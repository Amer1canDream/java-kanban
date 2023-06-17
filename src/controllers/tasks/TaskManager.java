package controllers.tasks;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.HashMap;
import java.util.List;

public interface TaskManager {
    HashMap<Integer, Task> getTasks();
    HashMap<Integer, Subtask> getSubtasks();
    HashMap<Integer, Epic> getEpics();
    void deleteAllTasks();
    Task getTaskById(Integer id);
    Subtask getSubtaskById(Integer id);
    Epic getEpicById(Integer id);
    void deleteTaskById(Integer id);
    void deleteSubtaskById(Integer id);
    void deleteEpicById(Integer id);
    void createTask(Task task);
    void createSubtask(Subtask subtask);
    void createEpic(Epic epic);
    void updateTask(Task task);
    void updateSubtask(Subtask subtask);
    void updateEpic(Epic epic);
    List<Task> getHistory();
    List<Task> getPrioritizedTasks();
}
