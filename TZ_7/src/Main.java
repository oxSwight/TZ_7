import managers.FileBackedTaskManager;
import managers.Managers;
import models.Epic;
import models.Subtask;
import models.Task;
import models.TaskStatus;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        // Создаем временный файл для хранения задач
        File tempFile = new File("tasks.csv");

        // Создаем экземпляр FileBackedTaskManager
        FileBackedTaskManager taskManager = new FileBackedTaskManager(tempFile);

        // Добавление задачи
        Task task1 = new Task("Task 1", "Description 1", 0, TaskStatus.NEW);
        int task1Id = taskManager.addNewTask(task1);

        // Добавление эпика
        Epic epic1 = new Epic("Epic 1", "Description 1", 0, TaskStatus.NEW);
        int epic1Id = taskManager.addNewEpic(epic1);

        // Добавление подзадачи к эпикам
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", 0, TaskStatus.NEW, epic1Id);
        int subtask1Id = taskManager.addNewSubtask(subtask1);

        // Получение и вывод задач
        Task retrievedTask1 = taskManager.getTask(task1Id);
        System.out.println("Retrieved Task: " + retrievedTask1.getName());

        Epic retrievedEpic1 = taskManager.getEpic(epic1Id);
        System.out.println("Retrieved Epic: " + retrievedEpic1.getName());

        Subtask retrievedSubtask1 = taskManager.getSubtask(subtask1Id);
        System.out.println("Retrieved Subtask: " + retrievedSubtask1.getName());

        // Обновление задачи
        taskManager.updateTask(new Task("task1Id", "Updated Task 1", 1, TaskStatus.IN_PROGRESS));

        // Сохранение состояния в файл
        taskManager.save();

        // Загрузка задач из файла
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        // Проверка загруженных задач
        System.out.println("Loaded Tasks:");
        for (Task task : loadedManager.getTasks()) {
            System.out.println(task);
        }

        // Удаление задач
        taskManager.deleteTask(task1Id);
        taskManager.deleteEpic(epic1Id);
        taskManager.deleteSubtask(subtask1Id);
        if (tempFile.delete()) {
            System.out.println("Файл удалён");
        } else {
            System.out.println("Файл не удален");
        }
    }
}
