package model;

import java.time.Instant;

public class Subtask extends Task {

    private int epicId;

    public Subtask(Integer id, String name, Status status, String description, Integer epicId, Instant startTime, long duration) {
        super(name, description, status, startTime, duration);
        this.id = id;
        this.epicId = epicId;
    }

    public Subtask(String name, String description, int epicId, Status status,  Instant startTime, long duration) {
        super(name, description, status, startTime, duration);
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return id + "," + TasksTypes.SUBTASK + "," + name + "," + status + "," + description  + "," + getStartTime() + "," + duration + getEndTime() + epicId;
    }
}
