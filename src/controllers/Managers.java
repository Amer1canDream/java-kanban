package controllers;

import controllers.histroy.InMemoryHistoryManager;
import controllers.tasks.FileBackedTasksManager;
import controllers.tasks.InMemoryTaskManager;
import controllers.tasks.TaskManager;

import java.io.File;

public class Managers {
    public static TaskManager getDefault() {
        FileBackedTasksManager manager = new FileBackedTasksManager();
        return manager;
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        return historyManager;
    }
}
