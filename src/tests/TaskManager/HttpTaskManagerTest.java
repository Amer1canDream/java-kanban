package tests.TaskManager;

import controllers.tasks.HttpTaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servers.KVServer;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
    protected KVServer server;

    @BeforeEach
    public void loadInitialConditions() throws IOException {

        server = new KVServer();
        server.start();

        taskManager = new HttpTaskManager("http://localhost:8056");

    }

    @AfterEach
    void serverStop() {
        server.stop();
    }

    @Test
    void loadFromServerTest() {
        Task task1 = new Task("Task1","Task1D",  Status.NEW, Instant.ofEpochSecond(5000), 5);
        Epic epic1 = new Epic("Epic1", "Epic1D");
        Subtask subtask1 = new Subtask("Subtask1","Subtask1D",2, Status.NEW, Instant.EPOCH, 50);
        Subtask subtask2 = new Subtask("Subtask2", "Subtask2D", 2, Status.NEW,Instant.ofEpochSecond(11111), 50);

        taskManager.createTask(task1);
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        taskManager.getTaskById(task1.getId());
        taskManager.getEpicById(epic1.getId());
        taskManager.getSubtaskById(subtask1.getId());
        taskManager.getSubtaskById(subtask2.getId());

        taskManager.load();
        HashMap<Integer, Task> tasks = taskManager.getTasks();

        assertNotNull(tasks);
        assertEquals(1, tasks.size());

        List history = taskManager.getHistory();

        assertNotNull(history);
        assertEquals(4, history.size());

    }
}
