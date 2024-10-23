package models;

import managers.HistoryManager;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import managers.Managers;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testTasksEquality() {
        Task task1 = new Task("Task", "Description", 0, TaskStatus.NEW);
        task1.setId(1);
        Task task2 = new Task("Task", "Description", 0, TaskStatus.NEW);
        task2.setId(1);

        assertEquals(task1, task2, "Задачи с одинаковым id должны быть равны.");

    }
    @Test
    void getDefault_shouldReturnNotNull() {
        TaskManager taskManager = Managers.getDefaultTaskManager();
        assertNotNull(taskManager, "default manager should not be null");
    }

    @Test
    void getHistoryManager_shouldReturnNotNull() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "History manager should not be null");
    }
}
