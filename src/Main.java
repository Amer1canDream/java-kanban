import com.google.gson.GsonBuilder;
import controllers.Managers;
import controllers.tasks.HttpTaskManager;
import controllers.tasks.TaskManager;
import model.*;
import servers.HttpTaskServer;
import com.google.gson.Gson;
import servers.KVServer;

import java.io.IOException;
import java.time.Instant;


public class Main {

    public static void main(String[] args) throws IOException {

        new KVServer().start();

        TaskManager httpManager = Managers.getDefault();


        httpManager.createTask(new Task("Task1", "Description", Status.NEW, Instant.EPOCH, 50));
        httpManager.getTasks();
        HttpTaskManager refreshedHttpManager = new HttpTaskManager("http://localhost:8056");
        httpManager.createEpic(new Epic("1 name", "Test description"));

        httpManager.createSubtask(new Subtask("Test Subtask1","Test description 1",2,Status.NEW, Instant.EPOCH, 50));
        httpManager.createSubtask(new Subtask("Test Subtask2","Test description 2",2,Status.NEW, Instant.EPOCH, 100));
        refreshedHttpManager.load();
        refreshedHttpManager.getTasks();
/*
        httpManager.createSubtask(new Subtask("Test Subtask1","Test description 1",2,Status.NEW, Instant.EPOCH, 50));

        httpManager.getTasks();
        httpManager.getEpics();
        httpManager.getSubtasks();
        httpManager.getSubtasks();
        httpManager.getHistory();

        HttpTaskManager refreshedHttpManager = new HttpTaskManager("http://localhost:8056");
        refreshedHttpManager.load();
        refreshedHttpManager.getPrioritizedTasks();

 */
    }
}
