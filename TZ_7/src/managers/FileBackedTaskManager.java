package managers;

import models.Epic;
import models.Subtask;
import models.Task;
import models.TaskStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {

    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    @Override
    public int addNewTask(Task task) {
        int id = super.addNewTask(task);
        save();
        return id;
    }

    @Override
    public int addNewEpic(Epic epic) {
        int id = super.addNewEpic(epic);
        save();
        return id;
    }

    @Override
    public Integer addNewSubtask(Subtask subtask) {
        Integer id = super.addNewSubtask(subtask);
        save();
        return id;
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public void deleteSubtasks() {
        super.deleteSubtasks();
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    public void save() {
        StringBuilder sb = new StringBuilder();
        sb.append("id,type,name,status,description,epic\n");

        for (Task task : getTasks()) {
            sb.append(taskToString(task)).append("\n");
        }
        for (Epic epic : getEpics()) {
            sb.append(epicToString(epic)).append("\n");
        }
        for (Subtask subtask : getSubtasks()) {
            sb.append(subtaskToString(subtask)).append("\n");
        }

        try {
            Files.writeString(file.toPath(), sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new ManagerLoadException("Ошибка при сохранении в файл", e);
        }
    }

    private String taskToString(Task task) {
        return String.format("%d,TASK,%s,%s,%s,", task.getId(), task.getName(), task.getStatus(), task.getDescription());
    }

    private String epicToString(Epic epic) {
        return String.format("%d,EPIC,%s,%s,%s,", epic.getId(), epic.getName(), epic.getStatus(), epic.getDescription());
    }

    private String subtaskToString(Subtask subtask) {
        return String.format("%d,SUBTASK,%s,%s,%s,%d", subtask.getId(), subtask.getName(), subtask.getStatus(), subtask.getDescription(), subtask.getEpicId());
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            for (String line : lines.subList(1, lines.size())) {
                Task task = fromString(line);
                if (task instanceof Subtask) {
                    manager.addNewSubtask((Subtask) task);
                } else if (task instanceof Epic) {
                    manager.addNewEpic((Epic) task);
                } else {
                    manager.addNewTask(task);
                }
            }
        } catch (IOException e) {
            throw new ManagerLoadException("Ошибка загрузки данных", e);
        }
        return manager;
    }

    private static Task fromString(String value) {
        String[] parts = value.split(",", -1);
        if (parts.length < 5) {
            throw new IllegalArgumentException("Неверный формат строки: " + value);
        }
        int id = Integer.parseInt(parts[0]);
        String type = parts[1];
        String name = parts[2];
        String status = parts[3];
        String description = parts[4];

        switch (type) {
            case "TASK":
                Task task = new Task(name, description, 0, TaskStatus.valueOf(status));
                task.setId(id);
                return task;
            case "EPIC":
                Epic epic = new Epic(name, description, 0, TaskStatus.valueOf(status));
                epic.setId(id);
                return epic;
            case "SUBTASK":
                int epicId = Integer.parseInt(parts[5]);
                Subtask subtask = new Subtask(name, description, 0, TaskStatus.valueOf(status), epicId);
                subtask.setId(id);
                return subtask;
            default:
                throw new IllegalArgumentException("Неизвестный тип задачи: " + type);
        }
    }
}


