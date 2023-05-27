package controllers;

import controllers.histroy.InMemoryHistoryManager;
import controllers.tasks.FileBackedTasksManager;
import controllers.tasks.InMemoryTaskManager;
import controllers.tasks.TaskManager;

import java.io.File;

public class Managers {
    public static TaskManager getDefault() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        return manager;
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        return historyManager;
    }

    public static FileBackedTasksManager getDefaultFileBackedManager() {

        return new FileBackedTasksManager();

    }
}
