package com.ylab.kovtunenko.sax.filefinder.providers;

public interface ReaderProvider<T, V> {
    T read(V input);
}
