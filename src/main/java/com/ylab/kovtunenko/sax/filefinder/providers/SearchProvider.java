package com.ylab.kovtunenko.sax.filefinder.providers;

public interface SearchProvider<D, V> {
    boolean search(D searchData, V searchValue);
}
