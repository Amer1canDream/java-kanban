package tests.TaskManager;

import controllers.tasks.FileBackedTasksManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    @BeforeEach
    public void loadInitialConditions() {
        try {
            File file = new File("resources/taskManagerDumpTest.csv");
            if (file.createNewFile()) {
                taskManager = FileBackedTasksManager.loadFromFile(new File("resources/taskManagerDumpTest.csv"));
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
    @AfterEach
    public void cleanTestCsvFile() {
        try {
            File f= new File("resources/taskManagerDumpTest.csv");
            f.delete();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
