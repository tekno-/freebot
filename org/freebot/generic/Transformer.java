package org.freebot.generic;

import org.objectweb.asm.tree.ClassNode;

public interface Transformer {
    public void transform(ClassNode classNode);
}
