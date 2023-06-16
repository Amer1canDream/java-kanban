package controllers.tasks;

import controllers.history.HistoryManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TasksTypes;
import model.Status;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Formatter {

    public static String tasksToString(TaskManager tasksManager) {
        StringBuilder result = new StringBuilder();

        List<Task> allTasks = new ArrayList<>();
        allTasks.addAll(tasksManager.getTasks().values());
        allTasks.addAll(tasksManager.getEpics().values());
        allTasks.addAll(tasksManager.getSubtasks().values());

        for (Task task : allTasks) {
            result.append(task.toString()).append("\n");
        }

        return result.toString();
    }

    public static String historyToString(HistoryManager manager) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Task task : manager.getHistory())
            stringBuilder.append(task.getId()).append(",");

        return stringBuilder.toString();
    }

    public static List<Integer> historyFromString(String value) {

        List<Integer> history = new ArrayList<>();

        for (String line : value.split(","))
            history.add(Integer.parseInt(line));

        return history;
    }

    //static Integer count = 0;

    public static Task tasksFromString(String value) {

        String[] values = value.split(",");
        Integer epicID = 0;

        Integer id = Integer.parseInt(values[0]);
        TasksTypes type = TasksTypes.valueOf(values[1]);
        String name = values[2];
        Status status = Status.valueOf(values[3]);
        String description = values[4];
        Instant startTime = Instant.parse(values[5]);
        long duration = Long.parseLong(values[6]);

        if (type.equals(TasksTypes.SUBTASK)) {
            epicID = Integer.parseInt(values[8]);
        }

        if (type.equals(TasksTypes.TASK)) {
            return new Task(id, name, status, description, startTime, duration);
        }
        if (type.equals(TasksTypes.EPIC)) {
            return new Epic(id, name, status, description);
        }
        if (type.equals(TasksTypes.SUBTASK)) {
            return new Subtask(id, name, status, description, epicID, startTime, duration);
        } else {
            throw new IllegalArgumentException("Неподдерживаемый формат задачи");
        }
    }
}
