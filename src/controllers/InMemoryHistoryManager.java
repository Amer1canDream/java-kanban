package controllers;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {


    private final HashMap<Integer, Node<Task>> history;

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


    private Node<Task> first;
    private Node<Task> last;

    public Node<Task> linkLast(Task task) {

        Node<Task> newNode = new Node<>(null, task, null);

        newNode.prev = last;

        if (last == null)
            first = newNode;
        else
            last.next = newNode;

        last = newNode;

        return newNode;

    }

    public List<Task> getTasks() {

        List<Task> tasks = new ArrayList<>();
        Node<Task> element = first;

        while (element != null) {
            tasks.add(element.data);
            element = element.next;
        }

        return tasks;
    }
    public void removeNode(Node<Task> node) {

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

}

class Node<T> {

    T data;
    Node<T> next;
    Node<T> prev;

    public Node(Node<T> prev, T data, Node<T> next) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
