package com.ylab.kovtunenko.sax.xml.parser.providers.builders;

public interface ProviderBuilder <T> {
    ProviderBuilder <T> addParam(String key, Object value);
    T build();
}