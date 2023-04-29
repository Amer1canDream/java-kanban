package controllers;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {
    HashMap<Integer, Task> getTasks();
    HashMap<Integer, Subtask> getSubtasks();
    HashMap<Integer, Epic> getEpics();
    void deleteAllTasks();
    Task getTaskById(int id);
    Subtask getSubtaskById(int id);
    Epic getEpicById(int id);
    void deleteTaskById(Integer id);
    void deleteSubtaskById(Integer id);
    void deleteEpicById(Integer id);
    void createTask(Task task);
    void createSubtask(Subtask subtask);
    void createEpic(Epic epic);
    void updateTask(Task task);
    void updateSubtask(Integer id, Subtask subtask);
    void updateEpic(Epic epic);
    ArrayList<Subtask> getEpicSubtasks(Integer epicId);
    List getHistory();
}
