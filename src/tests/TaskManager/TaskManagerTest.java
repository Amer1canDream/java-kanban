package tests.TaskManager;

import controllers.exceptions.ManagerDeleteTaskException;
import controllers.tasks.TaskManager;
import model.Epic;
import model.Status;

import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;


abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;

    @Test
    public void getPrioritizedTasksTest() {

        final Task task = new Task("Task1", "description", Status.NEW, Instant.ofEpochSecond(100), 0);
        final Task task1 = new Task("Task1", "description", Status.NEW, Instant.ofEpochSecond(40), 0);
        final Task task2 = new Task("Task1", "description", Status.NEW, Instant.EPOCH, 0);
        taskManager.createTask(task);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        List<Task> prioritizedTasks = new ArrayList<Task>();
        prioritizedTasks.add(task2);
        prioritizedTasks.add(task1);
        prioritizedTasks.add(task);
        List<Task> prioritizedTasksCreated = taskManager.getPrioritizedTasks();
        assertEquals(prioritizedTasks, prioritizedTasksCreated);

    }
    @Test
    public void setEpicEndTimeTest() {
        final Epic epic = new Epic("Epic1", "description");
        taskManager.createEpic(epic);
        taskManager.getEpicById(1).setEndTime(Instant.ofEpochSecond(42));
        assertEquals(Instant.ofEpochSecond(42), taskManager.getEpicById(1).getEndTime());
    }

    @Test
    public void getDurationTest() {
        final Task task = new Task("Task1", "description", Status.NEW, Instant.EPOCH, 0);
        taskManager.createTask(task);
        assertEquals(0, taskManager.getTaskById(1).getDuration());
    }

    @Test
    public void setDurationTest() {
        final Task task = new Task("Task1", "description", Status.NEW, Instant.EPOCH, 0);
        taskManager.createTask(task);
        taskManager.getTaskById(1).setDuration(42);
        assertEquals(42, taskManager.getTaskById(1).getDuration());
    }

    @Test
    public void setStartTimeTest() {
        final Task task = new Task("Task1", "description", Status.NEW, Instant.EPOCH, 0);
        taskManager.createTask(task);
        taskManager.getTaskById(1).setStartTime(Instant.ofEpochSecond(42));
        assertEquals(Instant.ofEpochSecond(42), taskManager.getTaskById(1).getStartTime());
    }

    @Test
    void createTaskTest() {
        final Task task = new Task("Test addNewTask", "Description", Status.NEW, Instant.EPOCH, 0);
        taskManager.createTask(task);

        final Task savedTask = taskManager.getTaskById(1);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        HashMap<Integer, Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(1), "Задачи не совпадают. Из tasks HashMap");
    }

    @Test
    void createSubtaskTest() {
        final Epic epic = new Epic("Epic Test", "Epic description");
        final Subtask subtask = new Subtask("Subtask Test", "Description", 1, Status.NEW, Instant.EPOCH, 0);

        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask);

        final Task savedSubtask = taskManager.getSubtaskById(2);

        HashMap<Integer, Subtask> subtasks = taskManager.getSubtasks();

        final Epic epicFromTaskManager = taskManager.getEpicById(1);

        assertNotNull(subtask, "Задача не найдена.");
        assertEquals(subtask, savedSubtask, "Задачи не совпадают.");
        assertEquals(1, subtasks.size(), "Неверное количество задач.");
        assertEquals(subtask, subtasks.get(2), "Задачи не совпадают. Из subtasks HashMap");
        assertNotNull(epicFromTaskManager, "Эпика из сабтаски пустой");
    }

    @Test
    void createEpicTest() {
        final Epic epic = new Epic("Epic test", "description");

        taskManager.createEpic(epic);

        final Epic savedEpic = taskManager.getEpicById(1);
        HashMap<Integer, Epic> epics = taskManager.getEpics();
        assertEquals(savedEpic.getStatus(), Status.NEW, "Статус не NEW");
        assertNotNull(epic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(1), "Задачи не совпадают. Из subtasks HashMap");
    }

    @Test
    void getTasksTest() {
        HashMap<Integer, Task> tasksNullSaved = taskManager.getTasks();
        HashMap<Integer, Task> tasksNullCreated = new HashMap<Integer, Task>();

        assertEquals(tasksNullSaved, tasksNullCreated, "Таски не пустые");

        final Task task = new Task("Task1", "description", Status.NEW, Instant.EPOCH, 0);
        final Task task1 = new Task("Task2", "description", Status.NEW, Instant.EPOCH, 0);

        HashMap<Integer, Task> tasksCreated = new HashMap<Integer, Task>();

        taskManager.createTask(task);

        ManagerDeleteTaskException thrownGetTask = assertThrows(ManagerDeleteTaskException.class, () -> {
            taskManager.deleteTaskById(17);
        });
        assertEquals("Задача с таким ID не найдена", thrownGetTask.getMessage());

        ManagerDeleteTaskException thrownGetTaskNull = assertThrows(ManagerDeleteTaskException.class, () -> {
            taskManager.deleteTaskById(null);
        });
        assertEquals("Поле ID пустое", thrownGetTaskNull.getMessage());
    }

    @Test
    void updateTaskTest() {
        final Task task = new Task("Task1", "description", Status.NEW, Instant.EPOCH, 0);
        taskManager.createTask(task);

        final Task updateTask = new Task(1, "Task1", Status.IN_PROGRESS, "description", Instant.EPOCH, 0);

        taskManager.updateTask(updateTask);
        final Task savedTask = taskManager.getTaskById(1);

        assertEquals(updateTask, savedTask, "Таски различаются");
    }

    @Test
    void updateSubtaskTest() {
        final Epic epic = new Epic("Epic1", "description");
        final Subtask subtask1 = new Subtask("Subtask1", "Description", 1, Status.NEW, Instant.EPOCH, 0);
        final Subtask subtask2 = new Subtask("Subtask2", "Description", 1, Status.NEW, Instant.EPOCH, 0);

        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);

        final Epic epicStatusNewSaved = taskManager.getEpicById(1);

        assertEquals(epicStatusNewSaved.getStatus(), Status.NEW, "Статус не NEW");

        final Subtask subtaskStatusInProgress = new Subtask(2,"Subtask1", Status.IN_PROGRESS, "Description", 1, Instant.EPOCH, 0);
        taskManager.updateSubtask(subtaskStatusInProgress);
        final Subtask subtaskStatusInProgressSaved = taskManager.getSubtaskById(2);
        final Epic epicStatusInProgressSaved = taskManager.getEpicById(1);

        assertEquals(subtaskStatusInProgressSaved, subtaskStatusInProgress, "Таски отличаются");
        assertEquals(epicStatusInProgressSaved.getStatus(), Status.IN_PROGRESS, "Статус у Эпика не IN_PROGRESS");

        final Subtask subtaskStatusDone1 = new Subtask(2,"Subtask1", Status.DONE, "Description", 1, Instant.EPOCH, 0);
        final Subtask subtaskStatusDone2 = new Subtask(3,"Subtask2", Status.DONE, "Description", 1, Instant.EPOCH, 0);

        taskManager.updateSubtask(subtaskStatusDone1);
        taskManager.updateSubtask(subtaskStatusDone2);

        final Epic epicStatusDoneSaved = taskManager.getEpicById(1);
        assertEquals(epicStatusDoneSaved.getStatus(), Status.DONE, "Статус эпика не DONE");
    }

    @Test
    void updateEpicTest() {
        final Epic epic = new Epic("Epic1", "description");
        taskManager.createEpic(epic);

        final Epic epicStatusInProgress = new Epic(1, "Epic1", Status.IN_PROGRESS, "description");
        taskManager.updateEpic(epicStatusInProgress);
        final Epic epicStatusInProgressSaved = taskManager.getEpicById(1);

        assertEquals(epicStatusInProgress, epicStatusInProgressSaved, "Эпики не совпдают");
    }

    @Test
    void getHistoryTest() {
        final Epic epic = new Epic("Epic1", "description");
        final Subtask subtask1 = new Subtask("Subtask1", "Description", 1, Status.NEW, Instant.EPOCH, 0);
        final Subtask subtask2 = new Subtask("Subtask2", "Description", 1, Status.NEW, Instant.EPOCH, 0);
        final Task task1 = new Task("Task1", "description", Status.NEW, Instant.EPOCH, 0);
        final Task task2 = new Task("Task1", "description", Status.NEW, Instant.EPOCH, 0);

        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        taskManager.getTaskById(5);
        taskManager.getTaskById(5);
        taskManager.getTaskById(4);
        taskManager.getTaskById(4);
        taskManager.getEpicById(1);
        taskManager.getSubtaskById(3);
        taskManager.getSubtaskById(2);
        taskManager.getEpicById(1);

        final List<Task> historySaved = taskManager.getHistory();
        final List<Task> historyCurrent = new ArrayList<>();
        historyCurrent.add(task2);
        historyCurrent.add(task1);
        historyCurrent.add(subtask2);
        historyCurrent.add(subtask1);
        historyCurrent.add(epic);

        assertEquals(historySaved, historyCurrent, "История некорректно формируется");

        taskManager.deleteTaskById(5);
        taskManager.deleteEpicById(1);
        final List<Task> historyAfterRemove2Saved = taskManager.getHistory();
        final List<Task> historyAfterRemove2Current = new ArrayList<>();
        historyAfterRemove2Current.add(task1);
        assertEquals(historyAfterRemove2Saved, historyAfterRemove2Current, "История после удаления из начала и из конца и из середины некорректно формируется");

        taskManager.deleteAllTasks();
        final List<Task> historyClearSaved = taskManager.getHistory();
        final List<Task> historyClearCurrent = new ArrayList<>();

        assertEquals(historyClearCurrent, historyClearSaved, "Истории после удаления не совпадают");

    }
}
