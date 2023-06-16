package tests.HistoryManager;

import controllers.Managers;
import controllers.history.HistoryManager;
import model.Status;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {

    private HistoryManager manager;

    @BeforeEach
    public void loadInitialConditions() {
        manager = Managers.getDefaultHistory();
    }
    @Test
    void addTest() {
        Task task1 = new Task(1, "Task1", Status.NEW, "Task1", Instant.EPOCH, 0);
        Task task2 = new Task(2,"Task2", Status.NEW, "Task2", Instant.EPOCH, 0);
        Task task3 = new Task(3, "Task3", Status.NEW, "Task3", Instant.EPOCH, 0);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);

        assertEquals(List.of(task1, task2, task3), manager.getHistory());
    }

    @Test
    void remove() {
        Task task1 = new Task(1, "Task1", Status.NEW, "Task1", Instant.EPOCH, 0);
        Task task2 = new Task(2, "Task2", Status.NEW, "Task2", Instant.EPOCH, 0);
        Task task3 = new Task(3, "Task3", Status.NEW, "Task3", Instant.EPOCH, 0);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);

        manager.remove(task2.getId());

        assertEquals(List.of(task1, task3), manager.getHistory());
    }

    @Test
    void removeAll() {
        Task task1 = new Task(1, "Task1", Status.NEW, "Task1", Instant.EPOCH, 0);
        Task task2 = new Task(2, "Task2", Status.NEW, "Task2", Instant.EPOCH, 0);
        Task task3 = new Task(3, "Task3", Status.NEW, "Task3", Instant.EPOCH, 0);

        manager.add(task1);
        manager.add(task2);
        manager.add(task3);

        manager.removeAll();

        final List<Task> emptyList = new ArrayList<>();
        assertEquals(emptyList, manager.getHistory());
    }
}