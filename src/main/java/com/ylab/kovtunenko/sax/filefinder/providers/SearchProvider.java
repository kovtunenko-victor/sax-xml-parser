package com.ylab.kovtunenko.sax.filefinder.providers;

public interface SearchProvider<T, V> {
    T search(V data);
}