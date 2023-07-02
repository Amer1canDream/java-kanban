package model;

import java.time.Instant;
import java.util.Objects;

public class Task {

    protected String name;
    protected String description;
    protected Integer id;
    protected Status status;
    protected Instant startTime;
    protected long duration;
    public Task(String name, String description, Status status, Instant startTime, long duration) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(String name, String description, Instant startTime, long duration) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.startTime = startTime;
    }
    public Task(int id, String name, Status status, String description, Instant startTime, long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }
    public Instant getStartTime() {

        return startTime;

    }
    public Instant getEndTime() {
        final byte SECONDS_IN_ONE_MINUTE = 60;
        return startTime.plusSeconds(duration * SECONDS_IN_ONE_MINUTE);
    }
    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task that = (Task) o;

        return Objects.equals(this.name, that.name)
                && Objects.equals(this.description, that.description)
                && Objects.equals(this.id, that.id)
                && Objects.equals(this.status, that.status);

    }

    @Override
    public String toString() {
        return id + "," + TasksTypes.TASK + "," + name + "," + status + "," + description + "," + getStartTime() + "," + duration + getEndTime();
    }
}
