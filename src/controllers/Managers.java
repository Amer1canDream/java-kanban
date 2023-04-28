package controllers;

import controllers.InMemoryHistoryManager;
import controllers.InMemoryTaskManager;
import controllers.TaskManager;

public class Managers {
    public static TaskManager getDefault() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        return manager;
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        return historyManager;
    }
}
