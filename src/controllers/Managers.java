package controllers;

import controllers.history.InMemoryHistoryManager;
import controllers.tasks.FileBackedTasksManager;
import controllers.tasks.InMemoryTaskManager;
import controllers.tasks.TaskManager;

public class Managers {
    public static TaskManager getDefault() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        return manager;
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        return historyManager;
    }

    /*
    public static FileBackedTasksManager getDefaultFileBackedManager() {
        return new FileBackedTasksManager();
    }
     */
}
