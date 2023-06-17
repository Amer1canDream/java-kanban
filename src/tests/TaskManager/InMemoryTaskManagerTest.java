package tests.TaskManager;

import controllers.tasks.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeEach
    public void loadInitialConditions() {
        taskManager = new InMemoryTaskManager();
    }
}
