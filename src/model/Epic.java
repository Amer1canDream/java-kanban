package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtasks = new ArrayList<Integer>();
    public Epic(String name, String description, int id) {
        super(name, description, id);
        this.status = "NEW";
    }

    public void setSubtasks(int subtaskId) {
        subtasks.add(subtaskId);
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + description + ", " + status + ", " + subtasks;
    }
}
