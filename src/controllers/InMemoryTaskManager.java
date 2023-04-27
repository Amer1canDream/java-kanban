package controllers;

import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks  = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks  = new HashMap<>();
    private HashMap<Integer, Epic> epics  = new HashMap<>();

    private HistoryManager historyManager = Managers.getDefaultHistory();
    private int id = 0;
    @Override
    public int getId() {
        id++;
        return id;
    }

    @Override
    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }
    @Override
    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }
    @Override
    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }
    @Override
    public void deleteAllTasks() {
        tasks.clear();
        subtasks.clear();
        epics.clear();
    }
    @Override
    public Task getTaskById(int id) {
        historyManager.add(tasks.get(id));
        return(tasks.get(id));
    }
    @Override
    public Subtask getSubtaskById(int id) {
        historyManager.add(subtasks.get(id));
        return(subtasks.get(id));
    }
    @Override
    public Epic getEpicById(int id) {
        historyManager.add(epics.get(id));
        return(epics.get(id));
    }
    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }
    @Override
    public void deleteSubtaskById(int id) {
        subtasks.remove(id);
        int epicId = subtasks.get(id).getEpicId();
        setEpicStatus(epicId);
    }
    @Override
    public void deleteEpicById(int id) {
        epics.remove(id);
    }
    @Override
    public void createTask(Task task) {
        int id = getId();
        task.setId(id);
        tasks.put(id, task);
    }
    @Override
    public void createSubtask(Subtask subtask) {
        int id = getId();
        subtask.setId(id);
        subtasks.put(id, subtask);
        int epicId = subtasks.get(id).getEpicId();
        setEpicStatus(epicId);
    }

    @Override
    public void createEpic(Epic epic) {
        int id = getId();
        epics.put(id, epic);
        setEpicStatus(id);
    }
    @Override
    public void updateTask(Task task) {
        int id = task.getId();
        Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        tasks.put(id, task);
    }
    @Override
    public void updateSubtask(Subtask subtask) {
        int id = subtask.getId();
        Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        subtasks.put(id, subtask);
        int epicId = subtasks.get(id).getEpicId();
        setEpicStatus(epicId);
    }
    @Override
    public void updateEpic(Epic epic) {
        int id = epic.getId();
        Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        epics.put(id, epic);
    }
    @Override
    public ArrayList<Subtask> getEpicSubtasks(int epicId) {

        ArrayList<Subtask> epicSubtasks = new ArrayList<Subtask>();
        ArrayList<Integer> subtasksIds = epics.get(epicId).getSubtasks();

        for (int i : subtasksIds) {
            epicSubtasks.add(subtasks.get(i));
        }

        return epicSubtasks;
    }
    @Override
    public void setSubtaskStatus(int subtaskId, Statuses status) {
        subtasks.get(subtaskId).setStatus(status);
        int epicId = subtasks.get(subtaskId).getEpicId();
        setEpicStatus(epicId);
    }
    @Override
    public void deleteSubtask(int subtaskId) {
        int epicId = subtasks.get(subtaskId).getEpicId();
        subtasks.remove(subtaskId);
        setEpicStatus(epicId);
    }
    @Override
    public void setTaskStatus(int taskId, Statuses status) {
        tasks.get(taskId).setStatus(status);
    }
    @Override
    public void setEpicStatus(int epicId) {

        boolean inProgress = true;

        if ( epics.get(epicId) == null ) {
            return;
        }

        if ( getEpicSubtasks(epicId).size() == 1 ) {
            inProgress = false;
            epics.get(epicId).setStatus(Statuses.NEW);
        }
        Boolean isFinal = true;
        for ( Subtask subtask : getEpicSubtasks(epicId)) {
            if (subtask == null) {
                break;
            }
            if (subtask.getStatus() != Statuses.DONE) {
                isFinal = false;
            }
        }

        if ( isFinal == true ) {
            epics.get(epicId).setStatus(Statuses.DONE);
            inProgress = false;
        }

        boolean isNew = true;
        for ( Subtask subtask : getEpicSubtasks(epicId)) {
            isNew = true;
            if (subtask == null) {
                continue;
            }
            if (subtask.getStatus() != Statuses.NEW ) {
                isNew = false;
            }
        }

        if ( isNew == true ) {
            epics.get(epicId).setStatus(Statuses.NEW);
            inProgress = false;
        }

        if (inProgress == true) {
            epics.get(epicId).setStatus(Statuses.IN_PROGRESS);
        }
    }

    public List getHistory() {
        return historyManager.getHistory();
    }
}
