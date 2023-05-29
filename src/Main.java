import controllers.Managers;
import controllers.tasks.FileBackedTasksManager;
import controllers.tasks.TaskManager;
import model.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        FileBackedTasksManager.main();
        //FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(new File("resources/taskManagerDump.csv"));

        //System.out.println(fileBackedTasksManager.getTasks());
        //System.out.println(fileBackedTasksManager.getEpics());
        //System.out.println(fileBackedTasksManager.getSubtasks());
        //System.out.println(fileBackedTasksManager.getHistory());
    }
}
