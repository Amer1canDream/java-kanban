package servers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import controllers.tasks.Formatter;
import controllers.tasks.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class HttpTaskServer {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private byte[] response = new byte[0];
    private final TaskManager manager;
    private HttpExchange httpExchange;
    private final HttpServer server;
    private int responseCode = 404;
    private final Gson gson;
    private String getPath;
    private static final int PORT = 8080;

    public HttpTaskServer(TaskManager manager) throws IOException {

        this.manager = manager;
        server = HttpServer.create();
        server.bind(new InetSocketAddress(PORT), 0);
        server.createContext("/tasks", new TaskHandler());
        gson = Formatter.createGson();

    }

    public void start() {

        server.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");

    }

    public void stop() {

        server.stop(0);
        System.out.println("HTTP-сервер остановлен на " + PORT + " порту!");

    }

    class TaskHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) {

            ArrayList<String> regexPaths = new ArrayList<>();
            regexPaths.add("^/tasks$");
            regexPaths.add("^/tasks/task$");
            regexPaths.add("^/tasks/epic$");
            regexPaths.add("^/tasks/subtask$");
            regexPaths.add("^/tasks/task/\\d+$");
            regexPaths.add("^/tasks/subtask/\\d+$");
            regexPaths.add("^/tasks/epic/\\d+$");
            regexPaths.add("^/tasks/history$");
            regexPaths.add("^/tasks/subtask/epic/\\d+$");
            regexPaths.add("^/tasks/task/\\d+$");

            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();
            httpExchange = exchange;
            getPath = path;

            try {
                switch (method) {
                    case "GET":
                        if (Pattern.matches(regexPaths.get(0), path))
                            getPrioritizedTasks();
                        if (Pattern.matches(regexPaths.get(1), path))
                            getTasks();
                        if (Pattern.matches(regexPaths.get(2), path))
                            getEpics();
                        if (Pattern.matches(regexPaths.get(3), path))
                            getSubtasks();
                        if (Pattern.matches(regexPaths.get(4), path))
                            getTaskById();
                        if (Pattern.matches(regexPaths.get(5), path))
                            getSubtaskById();
                        if (Pattern.matches(regexPaths.get(6), path))
                            getEpicById();
                        break;
                    case "POST":
                        if (Pattern.matches(regexPaths.get(1), path))
                            createTask();
                        if (Pattern.matches(regexPaths.get(2), path))
                            createEpic();
                        if (Pattern.matches(regexPaths.get(3), path))
                            createSubtask();
                        break;

                    case "DELETE":
                        if (Pattern.matches(regexPaths.get(1), path))
                            deleteAllTasksEpicsSubtasks();
                        if (Pattern.matches(regexPaths.get(7), path))
                            deleteByID();
                        break;
                    default:
                        System.out.println("Метод " + method + " не поддерживается.");
                }
                exchange.sendResponseHeaders(responseCode, 0);
                try (OutputStream os = exchange.getResponseBody()) {
                    System.out.println(response);
                    os.write(response);
                }
            } catch (IOException e) {
                System.out.println("Ошибка выполнения запроса: " + e.getMessage());
            } finally {
                exchange.close();
            }
        }
        private void getPrioritizedTasks() {
            System.out.println("GET: /tasks запрос.\n");
            String prioritizedTasksToJson = gson.toJson(manager.getPrioritizedTasks());

            if (!prioritizedTasksToJson.isEmpty()) {
                response = prioritizedTasksToJson.getBytes(DEFAULT_CHARSET);
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                responseCode = 200;
            } else
                responseCode = 400;
        }
        private void getTaskById() {
            System.out.println("GET: /tasks/task/d+ запрос.\n");
            int id = Integer.parseInt(getPath.replaceFirst("/tasks/task/", ""));
            String taskByIDToJson = gson.toJson(manager.getTasks().get(id));

            if (!taskByIDToJson.isEmpty()) {
                response = taskByIDToJson.getBytes(DEFAULT_CHARSET);
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                responseCode = 200;
            } else
                responseCode = 400;
        }

        private void getSubtaskById() {
            System.out.println("GET: /tasks/subtask/d+ запрос.\n");
            int id = Integer.parseInt(getPath.replaceFirst("/tasks/subtask/", ""));
            String subtaskToJson = gson.toJson(manager.getSubtasks().get(id));

            if (!subtaskToJson.isEmpty()) {
                response = subtaskToJson.getBytes(DEFAULT_CHARSET);
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                responseCode = 200;
            } else
                responseCode = 400;
        }

        private void getEpicById() {
            System.out.println("GET: /tasks/epic/d+ запроc.\n");
            int id = Integer.parseInt(getPath.replaceFirst("/tasks/epic/", ""));
            String epicToJson = gson.toJson(manager.getEpics().get(id));

            if (!epicToJson.isEmpty()) {
                response = epicToJson.getBytes(DEFAULT_CHARSET);
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                responseCode = 200;
            } else
                responseCode = 400;
        }

        private void getTasks() {
            System.out.println("GET: /tasks/task запрос.\n");
            var tasksToJson = gson.toJson(manager.getTasks());

            if (!tasksToJson.isEmpty()) {
                response = tasksToJson.getBytes(DEFAULT_CHARSET);
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                responseCode = 200;
            } else
                responseCode = 400;
        }

        private void getEpics() {
            System.out.println("GET: /tasks/epic запроса.\n");
            String epicsToJson = gson.toJson(manager.getEpics());

            if (!epicsToJson.isEmpty()) {
                response = epicsToJson.getBytes(DEFAULT_CHARSET);
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                responseCode = 200;
            } else
                responseCode = 400;
        }

        private void getSubtasks() {
            System.out.println("GET: /tasks/subtask.\n");
            String subtasksToJson = gson.toJson(manager.getSubtasks());

            if (!subtasksToJson.isEmpty()) {
                response = subtasksToJson.getBytes(DEFAULT_CHARSET);
                httpExchange.getResponseHeaders().add("Content-Type", "application/json");
                responseCode = 200;
            } else
                responseCode = 400;
        }

        private void createTask() throws IOException {
            System.out.println("POST: /tasks/task запрос.\n");
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
            Task task = gson.fromJson(body, Task.class);
            responseCode = 200;

            if (!manager.getTasks().containsKey(task.getId())) {
                manager.createTask(task);
                System.out.println("Задача #" + task.getId() + " создана.\n" + body);
            } else {
                manager.updateTask(task);
                System.out.println("Задача #" + task.getId() + " обновлена.\n" + body);
            }
        }

        private void createEpic() throws IOException {
            System.out.println("POST: /tasks/epic запрос.\n");
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
            Epic epic = gson.fromJson(body, Epic.class);
            responseCode = 200;

            if (!manager.getEpics().containsKey(epic.getId())) {
                manager.createEpic(epic);
                System.out.println("Задача #" + epic.getId() + " создана.\n" + body);
            } else {
                manager.updateEpic(epic);
                System.out.println("Задача #" + epic.getId() + " обновлена.\n" + body);
            }
        }

        private void createSubtask() throws IOException {
            System.out.println("POST: /tasks/subtask запрос.\n");
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
            Subtask subtask = gson.fromJson(body, Subtask.class);
            responseCode = 200;

            if (!manager.getSubtasks().containsKey(subtask.getId())) {
                manager.createSubtask(subtask);
                System.out.println("Задача #" + subtask.getId() + " создана.\n" + body);
            } else {
                manager.updateSubtask(subtask);
                System.out.println("Задача #" + subtask.getId() + " создана.\n" + body);
            }
        }

        private void deleteAllTasksEpicsSubtasks() {
            System.out.println("DELETE: /tasks/task запрос.\n");
            manager.deleteAllTasks();
            System.out.println("Все таски, эпики и сабтаски удалены.");
            responseCode = 200;
        }

        private void deleteByID() {
            System.out.println("DELETE: /tasks/task/d+ запрос.\n");
            int id = Integer.parseInt(getPath.replaceFirst("/tasks/task/", ""));
            if (manager.getTasks().containsKey(id)) {
                manager.deleteTaskById(id);
                System.out.println("Задача #" + id + " удалена.\n");
            }
            if (manager.getEpics().containsKey(id)) {
                manager.deleteEpicById(id);
                System.out.println("Задача #" + id + " удалена.\n");
            }
            if (manager.getSubtasks().containsKey(id)) {
                manager.deleteSubtaskById(id);
                System.out.println("Задача #" + id + " удалена.\n");
            }
            responseCode = 200;
        }
    }
}

