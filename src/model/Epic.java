package model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    protected List<Integer> subtasks = new ArrayList<>();

    private Instant endTime;

    public Epic(String name, String description) {
        super(name, description, Instant.ofEpochSecond(0), 0);
        status = Status.IN_PROGRESS;
    }

    @Override
    public Instant getEndTime() {
        return endTime;
    }

    public Epic(Integer id, String name, Status status, String description) {
        super(id, name, status, description, Instant.ofEpochSecond(0), 0);
    }
    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
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
        return id + "," + TasksTypes.EPIC + "," + name + "," + status + "," + description + "," + getStartTime() + "," + duration + getEndTime();
    }
}
