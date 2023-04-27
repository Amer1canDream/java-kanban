package model;

import java.util.List;

public interface HistoryManager {
    public void add(Task task);
    public List getHistory();
}
