package tests.TaskManager;

import controllers.tasks.TaskManager;
import model.Epic;
import model.Status;

import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;


abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;

    @Test
    void createTaskTest() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        taskManager.createTask(task);

        final Task savedTask = taskManager.getTaskById(0);

        Assertions.assertNotNull(savedTask, "Задача не найдена.");
        Assertions.assertEquals(task, savedTask, "Задачи не совпадают.");

        final HashMap<Integer, Task> tasks = taskManager.getTasks();

        Assertions.assertNotNull(tasks, "Задачи на возвращаются.");
        Assertions.assertEquals(1, tasks.size(), "Неверное количество задач.");
        Assertions.assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }
}
