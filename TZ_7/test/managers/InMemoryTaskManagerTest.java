package managers;

import models.Epic;
import models.Subtask;
import models.Task;
import models.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void addNewTask() {
        Task task = new Task("Test Task", "Test Description", 0, TaskStatus.NEW);
        int taskId = taskManager.addNewTask(task);

        Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void addNewEpic() {
        Epic epic = new Epic("Test Epic", "Test Description", 0, TaskStatus.NEW);
        int epicId = taskManager.addNewEpic(epic);

        Epic savedEpic = taskManager.getEpic(epicId);

        assertNotNull(savedEpic, "Эпик не найден.");
        assertEquals(epic, savedEpic, "Эпики не совпадают.");

        List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics, "Эпики не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество эпиков.");
        assertEquals(epic, epics.getFirst(), "Эпики не совпадают.");
    }

    @Test
    void addNewSubtask() {
        Epic epic = new Epic("Test Epic", "Test Description", 0, TaskStatus.NEW);
        int epicId = taskManager.addNewEpic(epic);

        Subtask subtask = new Subtask("Test Subtask", "Test Description", 0, TaskStatus.NEW, epicId);
        int subtaskId = taskManager.addNewSubtask(subtask);

        Subtask savedSubtask = taskManager.getSubtask(subtaskId);

        assertNotNull(savedSubtask, "Подзадача не найдена.");
        assertEquals(subtask, savedSubtask, "Подзадачи не совпадают.");

        List<Subtask> subtasks = taskManager.getSubtasks();

        assertNotNull(subtasks, "Подзадачи не возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество подзадач.");
        assertEquals(subtask, subtasks.getFirst(), "Подзадачи не совпадают.");
    }

    @Test
    void historyTest() {
        Task task = new Task("Test Task", "Test Description", 0, TaskStatus.NEW);
        int taskId = taskManager.addNewTask(task);
        taskManager.getTask(taskId);

        List<Task> history = taskManager.getHistory();

        assertNotNull(history, "История не должна быть пустой.");
        assertEquals(1, history.size(), "История должна содержать одну задачу.");
        assertEquals(task, history.getFirst(), "Задача в истории должна совпадать с добавленной задачей.");
    }

    @Test
    void testTaskNonConflictingIds() {
        Task task1 = new Task("Task 1", "Description 1", 0, TaskStatus.NEW);
        Task task2 = new Task("Task 2", "Description 2", 0, TaskStatus.NEW);

        int id1 = taskManager.addNewTask(task1);
        int id2 = taskManager.addNewTask(task2);

        assertNotEquals(id1, id2, "Идентификаторы задач должны быть уникальными.");
    }
}
