
import managers.Managers;
import managers.TaskManager;
import models.Epic;
import models.Subtask;
import models.Task;
import models.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefaultTaskManager();

        Task task1 = new Task("Task 1", "Description 1", 0, TaskStatus.NEW);
        int task1Id = taskManager.addNewTask(task1);

        Epic epic1 = new Epic("Epic 1", "Description 1", 0, TaskStatus.NEW);
        int epic1Id = taskManager.addNewEpic(epic1);

        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", 0, TaskStatus.NEW, epic1Id);
        int subtask1Id = taskManager.addNewSubtask(subtask1);

        Task retrievedTask1 = taskManager.getTask(task1Id);
        System.out.println("Retrieved Task: " + retrievedTask1.getName());

        Epic retrievedEpic1 = taskManager.getEpic(epic1Id);
        System.out.println("Retrieved Epic: " + retrievedEpic1.getName());

        Subtask retrievedSubtask1 = taskManager.getSubtask(subtask1Id);
        System.out.println("Retrieved Subtask: " + retrievedSubtask1.getName());

        taskManager.updateTask(new Task("Updated Task 1", "Updated Description 1", 0, TaskStatus.IN_PROGRESS));

        taskManager.deleteTask(task1Id);
        taskManager.deleteEpic(epic1Id);
    }
}
