package model;

public class Task {

    protected String name;
    protected String description;
    protected int id;
    protected Statuses status;
    public Task(String name, String description, Statuses status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Task() {
    }

    public void setStatus(Statuses status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Statuses getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + description + ", " + status;
    }
}
