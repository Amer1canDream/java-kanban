package controllers.tasks;

import controllers.Managers;
import controllers.history.HistoryManager;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks  = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks  = new HashMap<>();
    private HashMap<Integer, Epic> epics  = new HashMap<>();

    protected HistoryManager historyManager = Managers.getDefaultHistory();
    private int id;


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
    public Task getTaskById(Integer id) {
        historyManager.add(tasks.get(id));
        return(tasks.get(id));
    }
    @Override
    public Subtask getSubtaskById(Integer id) {
        historyManager.add(subtasks.get(id));
        return(subtasks.get(id));
    }
    @Override
    public Epic getEpicById(Integer id) {
        historyManager.add(epics.get(id));
        return(epics.get(id));
    }
    @Override
    public void deleteTaskById(Integer id) {
        historyManager.remove(id);
        tasks.remove(id);
    }

    @Override
    public void deleteEpicById(Integer id) {
        Epic epic = epics.get(id);
        for (Integer subtasksId : epic.getSubtasks()) {
            historyManager.remove(subtasksId);
            subtasks.remove(subtasksId);
        }
        epics.remove(id);
        historyManager.remove(id);
    }
    @Override
    public void deleteSubtaskById(Integer subtaskId) {
        Integer epicId = subtasks.get(subtaskId).getEpicId();
        subtasks.remove(subtaskId);
        getEpicById(epicId).deleteSubtask(subtaskId);
        setEpicStatus(epicId);
        historyManager.remove(id);
    }

    public void addTaskWithoutHistory(Integer id, Task task) {
        tasks.put(id, task);
    }

    public void addSubtaskWithoutHistory(Integer id, Subtask subtask) {
        subtasks.put(id, subtask);
    }

    public void addEpicWithoutHistory(Integer id, Epic epic) {
        epics.put(id, epic);
    }
    @Override
    public void createTask(Task task) {
        if ( task.getId() != null ) {
            tasks.put(task.getId(), task);
        } else {
            Integer id = getId();
            task.setId(id);
            tasks.put(id, task);
        }
    }
    @Override
    public void createSubtask(Subtask subtask) {
        if ( subtask.getId() != null ) {
            subtasks.put(subtask.getId(), subtask);
        } else {
            Integer id = getId();
            subtask.setId(id);
            subtasks.put(id, subtask);
            int epicId = subtasks.get(id).getEpicId();
            epics.get(epicId).setSubtasks(id);
            setEpicStatus(epicId);
        }
    }

    @Override
    public void createEpic(Epic epic) {
        if ( epic.getId() != null ) {
            epics.put(epic.getId(), epic);
        } else {
            Integer id = getId();
            epic.setId(id);
            epics.put(id, epic);
            setEpicStatus(id);
        }
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

    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private Integer getId() {
        id++;
        return id;
    }
    private void setEpicStatus(Integer epicId) {

        if (epics.get(epicId).getSubtasks().isEmpty()) {
            epics.get(epicId).setStatus(Status.NEW);
            return;
        }

        int countNew = 0;
        int countInProgress = 0;
        int countDone = 0;

        for (Subtask subtask : getEpicSubtasks(epicId)) {
            if (subtask.getStatus() == Status.NEW) {
                countNew++;
            } else if (subtask.getStatus() == Status.DONE) {
                countDone++;
            } else if (subtask.getStatus() == Status.IN_PROGRESS) {
                countInProgress++;
            }
            if ( getEpicSubtasks(epicId).size() == countNew ) {
                epics.get(epicId).setStatus(Status.NEW);
            } else if ( getEpicSubtasks(epicId).size() == countDone ) {
                epics.get(epicId).setStatus(Status.DONE);
            } else {
                epics.get(epicId).setStatus(Status.IN_PROGRESS);
            }
        }
    }
}
