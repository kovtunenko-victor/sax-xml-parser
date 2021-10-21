package com.ylab.kovtunenko.sax.xml.parser.providers;

public interface ReaderProvider<T, V> {
    T read(V input);
}
