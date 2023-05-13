package controllers;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList historyManager;
    private final HashMap<Integer, Node<Task>> history;

    public InMemoryHistoryManager() {

        historyManager = new CustomLinkedList();
        history = new HashMap<>();

    }

    @Override
    public void add(Task task) {
        Node<Task> node = historyManager.linkLast(task);

        //System.out.println(task);
        //System.out.println(node.data);

        if (history.containsKey(task.getId()))
            historyManager.removeNode(history.get(task.getId()));

        history.put(task.getId(), node);
        //System.out.println(history);
    }

    @Override
    public void remove(int id) {
        historyManager.removeNode(history.get(id));
        history.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getTasks();
    }

    class CustomLinkedList {

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

        int counter;

        public void removeNode(Node<Task> node) {

            counter++;
            if (node == null)
                return;

            // Если текущая нода равна первой перемещаем указатель first на node.next
            if (node.equals(first)) {
                first = node.next;
            // Если node.next не null - то присвоим указателю node.next.prev null
                if (node.next != null)
                    node.next.prev = null;
            // Иначе если текущая нода не равна ноде из указателя first.
            } else {
            // Указателю next предыдущей ноды присваиваем следующую ноду вместо текущей.
                node.prev.next = node.next;
                if (node.next != null)
                    node.next.prev = node.prev;
            }
        }
    }
}
