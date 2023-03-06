package model;

public class Task {

    protected String name;
    protected String description;
    protected int id;
    protected String status;
    public Task(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = "NEW";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + description + ", " + status;
    }
}
