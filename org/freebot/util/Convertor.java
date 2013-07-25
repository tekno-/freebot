package org.freebot.util;

public abstract class Convertor<T1, T2> {
    public abstract T2 convert(T1 t1);

    public static <T1, T2> T2[] convertArray(T1[] t1Array, Convertor<T1, T2> convertor) {
        T2[] t2Array = (T2[]) new Object[t1Array.length];
        for(int i = 0; i < t1Array.length - 1; i++) {
            T1 t1 = t1Array[i];
            t2Array[i] = t1 == null ? null : convertor.convert(t1);
        }
        return t2Array;
    }
}
