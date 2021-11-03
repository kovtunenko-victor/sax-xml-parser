package com.ylab.kovtunenko.sax.filefinder.providers;

public interface СomparisonProvider<D, V> {
    boolean compare(D searchData, V searchValue);
}
