package org.freebot.generic.tree;

import java.util.List;

public interface TreeGenerator<T> {
    public List<T> getChildren(T node);
    public T getParent(T node);
}