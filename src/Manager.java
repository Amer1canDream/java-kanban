import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    HashMap<Integer, Task> tasks  = new HashMap<>();
    HashMap<Integer, Subtask> subtasks  = new HashMap<>();
    HashMap<Integer, Epic> epics  = new HashMap<>();


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

    public void createTask(Task task) {
        tasks.put(task.id, task);
    }

    public void createSubtask(Subtask subtask) {
        subtasks.put(subtask.id, subtask);
    }

    public void createEpic(Epic epic) {
        Boolean notAllNew = false;
        epics.put(epic.id, epic);
        if (epic.subtasks.length == 0 ) {
            epic.status = "NEW";
        } else {
            for (int i : epic.subtasks) {
                if (subtasks.get(i).status != "NEW" ) {
                    subtasks.get(i);
                    notAllNew = true;
                }
            }

            if ( notAllNew == false ) {
                changeEpic(new Epic(epic.name, epic.description, epic.id, "NEW", epic.subtasks));
            }
        }

    }

    public void changeTask(Task task) {
        tasks.replace(task.id, task);
    }

    public void changeEpic(Epic epic) {
        epics.replace(epic.id, epic);
    }

    public ArrayList<Subtask> subtasksList(int id) {
        ArrayList<Subtask> subtasksLines = null;
        for (Subtask subtask : subtasks.values()) {

            if ( subtask.epicId == id ) {
                subtasksLines.add(subtask);
            }
        }
        return subtasksLines;
    }

    public void changeSubtask(Subtask subtask) {
        Boolean isFinal = null;
        subtasks.replace(subtask.id, subtask);
        if ( subtask.status == "DONE" ) {
            isFinal = true;
            for (int i : epics.get(subtask.epicId).subtasks) {
                if ( subtasks.get(i).status != "DONE" ) {
                    isFinal = false;
                };
            }
        }

        if (isFinal == true ) {
            changeEpic(new Epic (epics.get(subtask.epicId).name, epics.get(subtask.epicId).description,
                    epics.get(subtask.epicId).id, "DONE", epics.get(subtask.epicId).subtasks));
        }
    }
}
