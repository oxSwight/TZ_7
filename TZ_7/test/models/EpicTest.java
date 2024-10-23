package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {

    @Test
    void testEpicsEquality() {
        Epic epic1 = new Epic("Epic", "Description", 0, TaskStatus.NEW);
        epic1.setId(1);
        Epic epic2 = new Epic("Epic", "Description", 0, TaskStatus.NEW);
        epic2.setId(1);

        assertEquals(epic1, epic2, "Эпики с одинаковым id должны быть равны.");
    }
}
