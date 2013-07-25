package org.freebot.generic.tree;

public class Tree<T> {

    private final Node<T> head;
    Tree(Node<T> head) {
        this.head = head;
    }

    public Node<T> getHead() {
        return head;
    }
}
