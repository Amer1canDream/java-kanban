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

    public void deleteById(int id) {
        tasks.remove(id);
        subtasks.remove(id);
        epics.remove(id);
    }

    public void createTask(String name, String description) {
        Task task = new Task(name, description, getId());
        tasks.put(task.getId(), task);
    }

    public void createSubtask(String name, String description) {
        Subtask subtask = new Subtask(name, description, getId());
        subtasks.put(subtask.getId(), subtask);
    }

    public void createEpic(String name, String description) {
        Epic epic = new Epic(name, description, getId());
        epics.put(epic.getId(), epic);
    }

    public void linkSubtask(int epicId, int subtaskId) {
        epics.get(epicId).setSubtasks(subtaskId);
        subtasks.get(subtaskId).setEpicId(epicId);
    }

    public void setEpicStatus(int epicId, String status) {
        epics.get(epicId).setStatus(status);
    }

    public ArrayList<Subtask> subtasksList(int epicId) {

        ArrayList<Subtask> subtasksLines = new ArrayList<Subtask>();
        ArrayList<Integer> subtasksIds = epics.get(epicId).getSubtasks();

        for (int i : subtasksIds) {
            subtasksLines.add(subtasks.get(i));
        }

        return subtasksLines;
    }

    public void setSubtaskStatus(int subtaskId, String status) {
        subtasks.get(subtaskId).setStatus(status);

        Boolean isFinal = null;
        if ( status == "DONE") {
            isFinal = true;

            for ( Subtask subtask : subtasksList(epics.get(subtasks.get(subtaskId).getEpicId()).getId())) {
                if (subtask.getStatus() != "DONE") {
                    isFinal = false;
                }
            }
        }

        if ( isFinal == true ) {
            epics.get(subtasks.get(subtaskId).getEpicId()).setStatus("DONE");
        }
    }

    public void setTaskStatus(int taskId, String status) {
        tasks.get(taskId).setStatus(status);
    }
}
