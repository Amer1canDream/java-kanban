package controllers;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> historyList = new ArrayList();
    @Override
    public void add(Task task) {
        if ( historyList.size() == 10 ) {
            historyList.remove(0);
        }
        historyList.add(task);
    }

    @Override
    public List getHistory() {
        return historyList;
    }
}