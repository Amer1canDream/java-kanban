package controllers;

import model.Task;

public class Node<T> {

    T data;
    Node<T> next;
    Node<T> prev;

    public Node(Node<T> prev, T data, Node<T> next) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
