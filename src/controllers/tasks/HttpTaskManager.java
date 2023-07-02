package controllers.tasks;

import clients.KVTaskClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Epic;
import model.Subtask;
import model.Task;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpTaskManager extends FileBackedTasksManager {
    protected KVTaskClient client;
    private final Gson gson;
    public HttpTaskManager(String url) {
        gson = Formatter.createGson();
        client = new KVTaskClient(url);
    }

    public KVTaskClient getClient() {
        return client;
    }

    @Override
    public void save() {


        String prioritizedTasks = gson.toJson(getPrioritizedTasks());
        client.save("tasks", prioritizedTasks);

        String history = gson.toJson(getHistory());
        client.save("tasks/history", history);

        String tasks = gson.toJson(getTasks());
        client.save("tasks/task", tasks);

        HashMap<Integer, Epic> Taskss = getEpics();
        System.out.println(Taskss);
        String epics = gson.toJson(Taskss);
        client.save("tasks/epic", epics);

        String subtasks = gson.toJson(getSubtasks());
        client.save("tasks/subtask", subtasks);

    }

    public void load() {
        String jsonPrioritizedTasks = getClient().load("tasks");
        Type prioritizedTaskType = new TypeToken<List<Task>>(){}.getType();
        List<Task> priorityTasks = gson.fromJson(jsonPrioritizedTasks, prioritizedTaskType);
        getPrioritizedTasks().addAll(priorityTasks);

        String gsonHistory = getClient().load("tasks/history");
        Type historyType = new TypeToken<List<Task>>(){}.getType();
        List<Task> history = gson.fromJson(gsonHistory, historyType);
        getHistory().addAll(history);

        String jsonTasks = getClient().load("tasks/task");
        Type taskType = new TypeToken<Map<Integer, Task>>(){}.getType();
        Map<Integer, Task> tasks = gson.fromJson(jsonTasks, taskType);
        getTasks().putAll(tasks);
        String jsonEpics = getClient().load("tasks/epic");
        Type epicType = new TypeToken<Map<Integer, Epic>>(){}.getType();
        Map<Integer, Epic> epics = gson.fromJson(jsonEpics, epicType);
        getEpics().putAll(epics);

        String jsonSubtasks = getClient().load("tasks/subtask");
        Type subtaskType = new TypeToken<Map<Integer, Subtask>>(){}.getType();
        Map<Integer, Subtask> subtasks = gson.fromJson(jsonSubtasks, subtaskType);
        getSubtasks().putAll(subtasks);
    }
}
