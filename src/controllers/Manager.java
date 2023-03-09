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
        epics.clear();
    }

    public void deleteById(int id) {
        tasks.remove(id);
        subtasks.remove(id);
        epics.remove(id);
    }

    public void createTask(String name, String description) {
        Task task = new Task(name, description, getId(), "NEW");
        tasks.put(task.getId(), task);
    }

    /** Создаем подзадачу. Если у подзадачи статус NEW, то проверяем эпик на наличие других задач **/
    public void createSubtask(String name, String description, int epicId, String status) {
        Subtask subtask = new Subtask(name, description, getId(), status, epicId);
        subtasks.put(subtask.getId(), subtask);
        if (subtask.getStatus() == "NEW" && epics.get(subtask.getEpicId()) != null ) {
            boolean isNew = true;
                /** Узнаем epicId и для каждой подзадачи этого эпика мы проверяем статус  **/
                System.out.println(subtasksList(subtask.getEpicId()));
                for ( Subtask subtsk : subtasksList(subtask.getEpicId())) {
                    System.out.println(subtsk);
                    if (subtsk.getStatus() != "NEW") {
                        isNew = false;
                    }
                }

            if ( isNew == true ) {
                epics.get(subtask.getEpicId()).setStatus("NEW");
            }
        }
    }


    public void createEpic(String name, String description, ArrayList<Integer> subtasksLinkedId) {
        Epic epic = new Epic(name, description, getId(), "NEW", subtasksLinkedId);
        epics.put(epic.getId(), epic);
    }

    public ArrayList<Subtask> subtasksList(int epicId) {

        ArrayList<Subtask> subtasksLines = new ArrayList<Subtask>();
        ArrayList<Integer> subtasksIds = epics.get(epicId).getSubtasks();

        for (int i : subtasksIds) {
            subtasksLines.add(subtasks.get(i));
        }

        return subtasksLines;
    }


    /**
    Этот метод как раз и занимается тем, что на основе смены статуса подзадачи расчитывает статус эпика.
     **/
    public void setSubtaskStatus(int subtaskId, String status) {
        subtasks.get(subtaskId).setStatus(status);

        Boolean isFinal = null;
        if ( status == "DONE") {
            isFinal = true;

            /** Узнаем epicId и для каждой подзадачи этого эпика мы проверяем статус  **/
            for ( Subtask subtask : subtasksList(epics.get(subtasks.get(subtaskId).getEpicId()).getId())) {
                if (subtask.getStatus() != "DONE") {
                    isFinal = false;
                }
            }
        }

        /** Если у всех подзадач статус DONE, то устанавливаем статус DONE для эпика **/
        if ( isFinal == true ) {
            epics.get(subtasks.get(subtaskId).getEpicId()).setStatus("DONE");
        }
    }

    /** Перед удалением подзадачи мы проверяем размер subtaskList для эпика подзадачи и если он = 1, то меняем статус эпикм на NEW **/
    public void deleteSubtask(int subtaskId) {
        if ( subtasksList(epics.get(subtasks.get(subtaskId).getEpicId()).getId()).size() == 1 ) {
            epics.get(subtasks.get(subtaskId).getEpicId()).setStatus("NEW");
        }
        epics.get(subtasks.get(subtaskId).getEpicId()).deleteSubtask(subtaskId);
        System.out.println(subtasksList(epics.get(subtasks.get(subtaskId).getEpicId()).getId()).size());
        subtasks.remove(subtaskId);
    }

    public void setTaskStatus(int taskId, String status) {
        tasks.get(taskId).setStatus(status);
    }
}
