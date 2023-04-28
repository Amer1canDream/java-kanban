package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    protected List<Integer> subtasks = new ArrayList<>();

    public Epic(String name, String description, List<Integer> subtasks) {
        super(name, description);
        this.subtasks = subtasks;
        status = Status.IN_PROGRESS;
    }

    public void setSubtasks(int subtaskId) {
        subtasks.add(subtaskId);
    }

    public List<Integer> getSubtasks() {
        return subtasks;
    }

    public void deleteSubtask(Integer subtaskId) {
        subtasks.remove(subtaskId);
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + description + ", " + status + ", " + subtasks;
    }
}
