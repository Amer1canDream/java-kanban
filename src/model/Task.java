package model;

public class Task {

    protected String name;
    protected String description;
    protected int id;
    protected String status;
    public Task(String name, String description, String status) {
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

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + description + ", " + status;
    }
}
