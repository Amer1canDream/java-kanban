package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtasks = new ArrayList<Integer>();
    public Epic(String name, String description, int id, String status, ArrayList<Integer> subtasks) {
        super(name, description, id, status);
        this.subtasks = subtasks;
    }

    public void setSubtasks(int subtaskId) {
        subtasks.add(subtaskId);
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    public void deleteSubtask(int subtaskId) {
        subtasks.remove(Integer.valueOf(subtaskId));
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + description + ", " + status + ", " + subtasks;
    }
}
