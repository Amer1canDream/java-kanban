import controllers.Managers;
import controllers.tasks.FileBackedTasksManager;
import controllers.tasks.TaskManager;


public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();
    }
}
