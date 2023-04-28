package controllers;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks  = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks  = new HashMap<>();
    private HashMap<Integer, Epic> epics  = new HashMap<>();

    private HistoryManager historyManager = Managers.getDefaultHistory();
    private int id = 0;

    private Integer getId() {
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
    public void deleteTaskById(Integer id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpicById(Integer id) {
        Epic epic = epics.get(id);
        for (Integer subtasksId : epic.getSubtasks()) {
            subtasks.remove(subtasksId);
        }
        epics.remove(id);
    }
    @Override
    public void deleteSubtaskById(Integer subtaskId) {
        Integer epicId = subtasks.get(subtaskId).getEpicId();
        subtasks.remove(subtaskId);
        getEpicById(epicId).deleteSubtask(subtaskId);
        setEpicStatus(epicId);
    }
    @Override
    public void createTask(Task task) {
        Integer id = getId();
        task.setId(id);
        tasks.put(id, task);
    }
    @Override
    public void createSubtask(Subtask subtask) {
        Integer id = getId();
        subtask.setId(id);
        subtasks.put(id, subtask);
        int epicId = subtasks.get(id).getEpicId();
        epics.get(epicId).setSubtasks(id);
        setEpicStatus(epicId);
    }

    @Override
    public void createEpic(Epic epic) {
        Integer id = getId();
        epics.put(id, epic);
        setEpicStatus(id);
    }
    @Override
    public void updateTask(Task task) {
        Integer id = task.getId();
        Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        tasks.put(id, task);
    }
    @Override
    public void updateSubtask(Integer id ,Subtask subtask) {
        subtasks.remove(id);
        subtasks.put(id, subtask);
        Integer epicId = subtasks.get(id).getEpicId();
        setEpicStatus(epicId);
    }
    @Override
    public void updateEpic(Epic epic) {
        Integer id = epic.getId();
        Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        epics.put(id, epic);
    }
    @Override
    public ArrayList<Subtask> getEpicSubtasks(Integer epicId) {

        ArrayList<Subtask> epicSubtasks = new ArrayList<Subtask>();
        List<Integer> subtasksIds = epics.get(epicId).getSubtasks();

        for (int i : subtasksIds) {
            epicSubtasks.add(subtasks.get(i));
        }

        return epicSubtasks;
    }

    private void setEpicStatus(Integer epicId) {

        boolean allSubtasksDone = false;
        boolean allSubtasksNew = false;

        for (Subtask subtask : getEpicSubtasks(epicId)) {
            allSubtasksNew = true;
            if (subtask.getStatus() != Status.NEW) {
                allSubtasksNew = false;
            }
        }

        for (Subtask subtask : getEpicSubtasks(epicId)) {
            allSubtasksDone = true;
            if (subtask.getStatus() != Status.DONE) {
                allSubtasksDone = false;
            }
        }

        if (epics.get(epicId).getSubtasks().isEmpty()) {
            epics.get(epicId).setStatus(Status.NEW);
        } else if (allSubtasksNew == true) {
            epics.get(epicId).setStatus(Status.NEW);
        } else if (allSubtasksDone == true) {
            epics.get(epicId).setStatus(Status.DONE);
            epics.get(epicId).getStatus();
        } else {
            epics.get(epicId).setStatus(Status.IN_PROGRESS);
        }
    }

    public List<Integer> getHistory() {
        return historyManager.getHistory();
    }
}