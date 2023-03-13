package controllers;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private HashMap<Integer, Task> tasks  = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks  = new HashMap<>();
    private HashMap<Integer, Epic> epics  = new HashMap<>();

    private int id = 0;

    public int getId() {
        id++;
        return id;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public void deleteAllTasks() {
        tasks.clear();
        subtasks.clear();
        epics.clear();
    }

    public Task getTaskById(int id) {
        return(tasks.get(id));
    }

    public Subtask getSubtaskById(int id) {
        return(subtasks.get(id));
    }

    public Epic getEpicById(int id) {
        return(epics.get(id));
    }
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteSubtaskById(int id) {
        subtasks.remove(id);
        int epicId = subtasks.get(id).getEpicId();
        setEpicStatus(epicId);
    }

    public void deleteEpicById(int id) {
        epics.remove(id);
    }

    public void createTask(Task task) {
        int id = getId();
        task.setId(id);
        tasks.put(id, task);
    }

    public void createSubtask(Subtask subtask) {
        int id = getId();
        subtask.setId(id);
        subtasks.put(id, subtask);
        int epicId = subtasks.get(id).getEpicId();
        setEpicStatus(epicId);
    }


    public void createEpic(Epic epic) {
        int id = getId();
        epics.put(id, epic);
        setEpicStatus(id);
    }

    public void updateTask(Task task) {
        int id = task.getId();
        Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        tasks.put(id, task);
    }

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

    public void updateEpic(Epic epic) {
        int id = epic.getId();
        Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        epics.put(id, epic);
    }

    public ArrayList<Subtask> getEpicSubtasks(int epicId) {

        ArrayList<Subtask> epicSubtasks = new ArrayList<Subtask>();
        ArrayList<Integer> subtasksIds = epics.get(epicId).getSubtasks();

        for (int i : subtasksIds) {
            epicSubtasks.add(subtasks.get(i));
        }

        return epicSubtasks;
    }

    public void setSubtaskStatus(int subtaskId, String status) {
        subtasks.get(subtaskId).setStatus(status);
        int epicId = subtasks.get(subtaskId).getEpicId();
        setEpicStatus(epicId);
    }

    public void deleteSubtask(int subtaskId) {
        int epicId = subtasks.get(subtaskId).getEpicId();
        subtasks.remove(subtaskId);
        setEpicStatus(epicId);
    }

    public void setTaskStatus(int taskId, String status) {
        tasks.get(taskId).setStatus(status);
    }

    public void setEpicStatus(int epicId) {

        boolean inProgress = true;

        if ( epics.get(epicId) == null ) {
            return;
        }

        if ( getEpicSubtasks(epicId).size() == 1 ) {
            inProgress = false;
            epics.get(epicId).setStatus("NEW");
        }
        Boolean isFinal = true;
        for ( Subtask subtask : getEpicSubtasks(epicId)) {
            if (subtask == null) {
                break;
            }
            if (subtask.getStatus() != "DONE") {
                isFinal = false;
            }
        }

        if ( isFinal == true ) {
            epics.get(epicId).setStatus("DONE");
            inProgress = false;
        }

        boolean isNew = true;
        for ( Subtask subtask : getEpicSubtasks(epicId)) {
            isNew = true;
            if (subtask == null) {
                continue;
            }
            if (subtask.getStatus() != "NEW") {
                isNew = false;
            }
        }

        if ( isNew == true ) {
            epics.get(epicId).setStatus("NEW");
            inProgress = false;
        }

        if (inProgress == true) {
            epics.get(epicId).setStatus("IN_PROGRESS");
        }
    }
}
