package managers;

import managers.InMemoryHistoryManager;
import models.Task;
import models.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {

    private InMemoryHistoryManager historyManager;

    @BeforeEach
    public void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    public void testAddTaskToHistory() {
        Task task1 = new Task("Task 1", "Description 1", 0, TaskStatus.NEW);
        task1.setId(1);
        historyManager.add(task1);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "История должна содержать одну задачу.");
        assertEquals(task1, history.getFirst(), "Задача должна быть добавлена в историю.");
    }

    @Test
    public void testNoDuplicatesInHistory() {
        Task task1 = new Task("Task 1", "Description 1", 0, TaskStatus.NEW);
        task1.setId(1);
        historyManager.add(task1);
        historyManager.add(task1);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "В истории не должно быть дубликатов.");
    }

    @Test
    public void testRemoveTaskFromHistory() {
        Task task1 = new Task("Task 1", "Description 1", 0,TaskStatus.NEW);
        task1.setId(1);
        historyManager.add(task1);
        historyManager.remove(1);

        List<Task> history = historyManager.getHistory();
        assertTrue(history.isEmpty(), "История должна быть пустой после удаления задачи.");
    }
}
