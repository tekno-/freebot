package org.freebot.generic.tree;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

    private T t;
    private List<Node<T>> children;
    private Node<T> parent;
    public Node(T t, Node<T> parent) {
        this.t = t;
        this.children = new ArrayList<>();
        this.parent = parent;
    }

    void addChild(Node<T> child) {
        children.add(child);
    }

    public T getValue() {
        return t;
    }


}