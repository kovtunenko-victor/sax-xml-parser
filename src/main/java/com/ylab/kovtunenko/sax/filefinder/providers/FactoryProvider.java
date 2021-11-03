package com.ylab.kovtunenko.sax.filefinder.providers;

public interface FactoryProvider <T> {
    FactoryProvider <T> addParam(String key, Object value);
    T build();
}