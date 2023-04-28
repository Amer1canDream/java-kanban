package controllers;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {
    public HashMap<Integer, Task> getTasks();
    public HashMap<Integer, Subtask> getSubtasks();
    public HashMap<Integer, Epic> getEpics();
    public void deleteAllTasks();
    public Task getTaskById(int id);
    public Subtask getSubtaskById(int id);
    public Epic getEpicById(int id);
    public void deleteTaskById(Integer id);
    public void deleteSubtaskById(Integer id);
    public void deleteEpicById(Integer id);
    public void createTask(Task task);
    public void createSubtask(Subtask subtask);
    public void createEpic(Epic epic);
    public void updateTask(Task task);
    public void updateSubtask(Integer id, Subtask subtask);
    public void updateEpic(Epic epic);
    public ArrayList<Subtask> getEpicSubtasks(Integer epicId);
    public List getHistory();
}
