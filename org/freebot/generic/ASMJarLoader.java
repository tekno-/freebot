package org.freebot.generic;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ASMJarLoader extends ClassLoader {

    private final Map<String, byte[]> jarEntries;
    private final List<Transformer> transformers;

    /**
     * Constructs a new ASMJarLoader.
     * @param jarEntries the mapped jar files
     */
    public ASMJarLoader(Map<String, byte[]> jarEntries) {
        this.jarEntries = jarEntries;
        transformers = new ArrayList<>();
    }

    /**
     * Register a class transformer for class loading. Allows for method chaining.
     * @param transformer the transformer to be added
     * @return this {@link ASMJarLoader} for chaining
     */
    public ASMJarLoader registerTransformer(Transformer transformer) {
        transformers.add(transformer);
        return this;
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if(jarEntries.containsKey(name)) {
            byte[] value = jarEntries.get(name);
            ClassReader cr = new ClassReader(value);
            ClassNode classNode = new ClassNode();
            cr.accept(classNode, ClassReader.SKIP_DEBUG);
            for(Transformer transformer : transformers) {
                transformer.transform(classNode);
            }
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(cw);
            value = cw.toByteArray();
            return defineClass(name, value, 0, value.length);
        }
        return super.loadClass(name, resolve);
    }

}
