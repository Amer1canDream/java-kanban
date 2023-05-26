package controllers.histroy;

import controllers.histroy.HistoryManager;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {


    private final HashMap<Integer, Node<Task>> history;
    private Node<Task> first;
    private Node<Task> last;

    public InMemoryHistoryManager() {
        history = new HashMap<>();
    }

    @Override
    public void add(Task task) {
        Node<Task> node = linkLast(task);
        if (history.containsKey(task.getId()))
            removeNode(history.get(task.getId()));

        history.put(task.getId(), node);
    }

    @Override
    public void remove(int id) {
        removeNode(history.get(id));
        history.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private Node<Task> linkLast(Task task) {

        Node<Task> newNode = new Node<>(task);

        newNode.prev = last;
        newNode.next = null;

        if (last == null)
            first = newNode;
        else
            last.next = newNode;

        last = newNode;

        return newNode;
    }

    private List<Task> getTasks() {

        List<Task> tasks = new ArrayList<>();
        Node<Task> element = first;

        while (element != null) {
            tasks.add(element.data);
            element = element.next;
        }
        return tasks;
    }
    private void removeNode(Node<Task> node) {

        if (node == null)
            return;


        if (node.equals(first)) {
            first = node.next;

            if (node.next != null)
                node.next.prev = null;

        } else {
            node.prev.next = node.next;
            if (node.next != null)
                node.next.prev = node.prev;
        }
    }
    static class Node<T> {

        T data;
        Node<T> next;
        Node<T> prev;

        public Node(T data) {
            this.data = data;
        }
    }
}
