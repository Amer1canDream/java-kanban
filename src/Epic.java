import java.util.Arrays;

public class Epic extends Task {
    int[] subtasks;
    public Epic(String name, String description, int id, String status, int[] subtasks) {
        super(name, description, id, status);
        this.subtasks = subtasks;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + description + ", " + status + ", " + Arrays.toString(subtasks);
    }
}
