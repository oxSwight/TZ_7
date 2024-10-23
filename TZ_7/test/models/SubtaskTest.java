package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubtaskTest {

    @Test
    void testSubtasksEquality() {
        Subtask subtask1 = new Subtask("Subtask", "Description", 0, TaskStatus.NEW, 1);
        subtask1.setId(1);
        Subtask subtask2 = new Subtask("Subtask", "Description", 0, TaskStatus.NEW, 1);
        subtask2.setId(1);

        assertEquals(subtask1, subtask2, "Подзадачи с одинаковым id должны быть равны.");
    }
}
