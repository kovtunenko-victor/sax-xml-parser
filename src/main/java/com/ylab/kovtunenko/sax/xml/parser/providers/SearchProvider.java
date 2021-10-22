package com.ylab.kovtunenko.sax.xml.parser.providers;

public interface SearchProvider<T, V> {
    T search(V data);
}