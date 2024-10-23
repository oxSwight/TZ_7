package managers;


import models.Epic;
import models.Subtask;
import models.Task;
import models.TaskStatus;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTest {

    // Существующий тест
    @Test
    public void testSaveAndLoad() {
        File tempFile = new File("tasks_test.csv");
        FileBackedTaskManager manager = new FileBackedTaskManager(tempFile);

        // Добавление задач
        Task task = new Task("Test Task", "Test Description", 0, TaskStatus.NEW);
        manager.addNewTask(task);

        Epic epic = new Epic("Test Epic", "Test Description", 0, TaskStatus.NEW);
        manager.addNewEpic(epic);

        Subtask subtask = new Subtask("Test Subtask", "Test Description", 0, TaskStatus.NEW, epic.getId());
        manager.addNewSubtask(subtask);

        // Сохранение состояния
        manager.save();

        // Загрузка из файла
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        // Проверка загруженных данных
        assertEquals(1, loadedManager.getTasks().size());
        assertEquals(1, loadedManager.getEpics().size());
        assertEquals(1, loadedManager.getSubtasks().size());

        assertEquals("Test Task", loadedManager.getTask(task.getId()).getName());
        assertEquals("Test Epic", loadedManager.getEpic(epic.getId()).getName());
        assertEquals("Test Subtask", loadedManager.getSubtask(subtask.getId()).getName());
    }

    // Новый тест для проверки пустого файла
    @Test
    public void testLoadEmptyFile() {
        File tempFile = new File("empty_tasks_test.csv");
        // Создаем пустой файл
        try {
            if (tempFile.createNewFile()) {
                System.out.println("Создан пустой файл: " + tempFile.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileBackedTaskManager manager = FileBackedTaskManager.loadFromFile(tempFile);

        // Проверка, что все хранилища пустые
        assertEquals(0, manager.getTasks().size(), "Ожидалось, что в менеджере не будет задач");
        assertEquals(0, manager.getEpics().size(), "EОжидалось, что в менеджере не будет эпиов");
        assertEquals(0, manager.getSubtasks().size(), "Ожидалось, что в менеджере не будет подзадач");
    }
}

