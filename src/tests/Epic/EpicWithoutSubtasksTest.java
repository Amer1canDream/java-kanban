package tests.Epic;

import model.Epic;
import model.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EpicWithoutSubtasksTest {
    @Test
    public void shouldBeInProgress(){
        Epic epic = new Epic("Test name", "Test description");

        Assertions.assertEquals(epic.getStatus(), Status.IN_PROGRESS);
    }
    @Test
    public void shouldBeNew(){
        Epic epic = new Epic("Test name", "Test description");
        epic.setStatus(Status.NEW);
        Assertions.assertEquals(epic.getStatus(), Status.NEW);
    }
    @Test
    public void shouldBeDone(){
        Epic epic = new Epic("Test name", "Test description");
        epic.setStatus(Status.DONE);
        Assertions.assertEquals(epic.getStatus(), Status.DONE);
    }

}