package com.ylab.kovtunenko.sax.filefinder.providers;

public interface ParserProvider <T, V> {
    T parse(V value);
}
