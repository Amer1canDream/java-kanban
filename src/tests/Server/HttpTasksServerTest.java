package tests.Server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controllers.tasks.Formatter;
import controllers.tasks.InMemoryTaskManager;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import servers.HttpTaskServer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTasksServerTest {

    private HttpTaskServer server;
    protected InMemoryTaskManager manager;

    protected Task task1 = new Task("Task1", "Task1D", Status.NEW, Instant.EPOCH, 5);

    protected Epic epic1 = new Epic("Epic1", "Epic1D");

    protected Subtask subtask1 = new Subtask("Subtask1", "Subtask1D", 2, Status.NEW, Instant.EPOCH, 50);

    protected Subtask subtask2 = new Subtask("Subtask2", "Subtask2D", 2, Status.NEW, Instant.EPOCH, 100);


    private Gson gson;

    @Before
    public void loadInitialConditions() throws IOException {

        manager = new InMemoryTaskManager();
        server = new HttpTaskServer(manager);
        gson = Formatter.createGson();

        manager.createTask(task1);
        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        System.out.println(manager.getPrioritizedTasks());
        manager.getTaskById(1);
        manager.getEpicById(2);
        manager.getSubtaskById(3);
        manager.getSubtaskById(4);

        server.start();
    }

    @After
    public void serverStop() {
        server.stop();
    }

    @Test
    public void getPrioritizedTasksTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Type type = new TypeToken<List<Task>>(){}.getType();
        List<Task> tasks = gson.fromJson((String) response.body(), type);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());

    }

    @Test
    public void getTasksTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Type type = new TypeToken<Map<Integer, Task>>(){}.getType();
        Map<Integer, Task> tasks = gson.fromJson(response.body(), type);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());

    }
    @Test
    public void getEpicsTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Type type = new TypeToken<Map<Integer, Epic>>(){}.getType();
        Map<Integer, Epic> epics = gson.fromJson((String) response.body(), type);

        assertNotNull(epics);
        assertEquals(1, epics.size());

    }

    @Test
    public void getSubtasksTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Type type = new TypeToken<Map<Integer, Subtask>>(){}.getType();
        Map<Integer, Subtask> subtasks = gson.fromJson((String) response.body(), type);

        assertNotNull(subtasks);
        assertEquals(2, subtasks.size());

    }

    @Test
    public void getTaskByIdTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/1");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Type type = new TypeToken<Task>(){}.getType();
        Task task = gson.fromJson((String) response.body(), type);

        assertNotNull(task);
        assertEquals(task1, task);
    }

    @Test
    public void getSubtaskByIdTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/3");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        System.out.println(subtask1);

        Type type = new TypeToken<Subtask>(){}.getType();
        Task task = gson.fromJson((String) response.body(), type);
        System.out.println(task);
        assertNotNull(subtask1);
        assertEquals(subtask1, task);

    }

    @Test
    public void getEpicByIdTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/2");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Type type = new TypeToken<Epic>(){}.getType();
        Task task = gson.fromJson((String) response.body(), type);

        assertNotNull(task);
        assertEquals(epic1, task);

    }

    @Test
    public void createTaskTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");

        String json = gson.toJson(task1);

        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(body)
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

    }

    @Test
    public void createEpicTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");

        String json = gson.toJson(epic1);

        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(body)
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

    }

    @Test
    public void createSubtaskTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask");

        String json = gson.toJson(subtask1);

        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(body)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

    }

    @Test
    public void deleteTaskTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/1");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(404, response.statusCode());

    }

    @Test
    public void deleteTasksTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Type type = new TypeToken<Map<Integer, Task>>(){}.getType();
        Map<Integer, Task> tasks = gson.fromJson(response.body(), type);

        assertNull(tasks);
    }
}
