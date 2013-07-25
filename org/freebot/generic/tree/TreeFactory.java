package org.freebot.generic.tree;

import java.util.ArrayList;
import java.util.List;

public final class TreeFactory {

    private TreeFactory() {
    }

    public static <T> Tree<T> generateTree(final T start, final TreeGenerator<T> generator) {
        T head = start, gen = start;
        while((gen = generator.getParent(gen)) != null) {
            head = gen;
        }
        Node<T> headNode = generateNode(null, head, generator);
        return new Tree<>(headNode);
    }

    private static <T> Node<T> generateNode(Node<T> parent, T curr, TreeGenerator<T> generator) {
        List<T> list = generator.getChildren(curr);
        Node<T> node = new Node<>(curr, parent);
        for(T t : list) {
            node.addChild(generateNode(node, t, generator));
        }
        return node;
    }
}
