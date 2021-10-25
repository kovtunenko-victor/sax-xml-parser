package com.ylab.kovtunenko.sax.filefinder.providers.builders;

public interface BuilderProvider <T> {
    BuilderProvider <T> addParam(String key, Object value);
    T build();
}