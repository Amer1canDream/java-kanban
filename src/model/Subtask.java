package model;

public class Subtask extends Task {

    private int epicId;

    public Subtask(String name, String description, int id) {
        super(name, description, id);
        this.status = "NEW";
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + description + ", " + status + ", " + epicId;
    }
}
