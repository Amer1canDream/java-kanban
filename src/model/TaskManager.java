package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {
    public int getId();
    public HashMap<Integer, Task> getTasks();
    public HashMap<Integer, Subtask> getSubtasks();
    public HashMap<Integer, Epic> getEpics();
    public void deleteAllTasks();
    public Task getTaskById(int id);
    public Subtask getSubtaskById(int id);
    public Epic getEpicById(int id);
    public void deleteTaskById(int id);
    public void deleteSubtaskById(int id);
    public void deleteEpicById(int id);
    public void createTask(Task task);
    public void createSubtask(Subtask subtask);
    public void createEpic(Epic epic);
    public void updateTask(Task task);
    public void updateSubtask(Subtask subtask);
    public void updateEpic(Epic epic);
    public ArrayList<Subtask> getEpicSubtasks(int epicId);
    public void setSubtaskStatus(int subtaskId, Statuses status);
    public void deleteSubtask(int subtaskId);
    public void setTaskStatus(int taskId, Statuses status);
    public void setEpicStatus(int epicId);

    public List getHistory();
}
