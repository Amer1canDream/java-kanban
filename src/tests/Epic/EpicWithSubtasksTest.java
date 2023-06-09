package tests.Epic;

import controllers.Managers;
import controllers.tasks.InMemoryTaskManager;
import controllers.tasks.TaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EpicWithSubtasksTest {

    private static TaskManager manager;
    @BeforeEach
    public void beforeEach() {
        manager = new InMemoryTaskManager();
    }

    @Test
    public void shouldBeNew(){
        manager.createEpic(new Epic(1,"1 name", Status.IN_PROGRESS, "Test description"));

        manager.createSubtask(new Subtask("Test Subtask1","Test description 1",1,Status.NEW));
        manager.createSubtask(new Subtask("Test Subtask2","Test description 2",1,Status.NEW));
        Assertions.assertEquals(manager.getEpicById(1).getStatus(), Status.NEW);
    }

    @Test
    public void shouldBeInProgress(){
        manager.createEpic(new Epic(1,"1 name", Status.NEW, "Test description"));

        manager.createSubtask(new Subtask("Test Subtask1","Test description 1",1,Status.IN_PROGRESS));
        manager.createSubtask(new Subtask("Test Subtask2","Test description 2",1,Status.IN_PROGRESS));
        Assertions.assertEquals(manager.getEpicById(1).getStatus(), Status.IN_PROGRESS);
    }

    @Test
    public void shouldBeInDone(){
        manager.createEpic(new Epic(1,"1 name", Status.NEW, "Test description"));

        manager.createSubtask(new Subtask("Test Subtask1","Test description 1",1,Status.DONE));
        manager.createSubtask(new Subtask("Test Subtask2","Test description 2",1,Status.DONE));
        Assertions.assertEquals(manager.getEpicById(1).getStatus(), Status.DONE);
    }

    @Test
    public void shouldBeInProgressMixed(){
        manager.createEpic(new Epic(1,"1 name", Status.NEW, "Test description"));

        manager.createSubtask(new Subtask("Test Subtask1","Test description 1",1,Status.NEW));
        manager.createSubtask(new Subtask("Test Subtask2","Test description 2",1,Status.DONE));
        Assertions.assertEquals(manager.getEpicById(1).getStatus(), Status.IN_PROGRESS);
    }
}
