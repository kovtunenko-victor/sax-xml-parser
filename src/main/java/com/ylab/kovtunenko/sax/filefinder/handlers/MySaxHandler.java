package com.ylab.kovtunenko.sax.filefinder.handlers;

import org.xml.sax.helpers.DefaultHandler;

import com.ylab.kovtunenko.sax.filefinder.providers.SearchProvider;

public abstract class MySaxHandler extends DefaultHandler {
    protected final SearchProvider<String, String> searchProvider;
    protected final String searchMask;
    
    public MySaxHandler(String searchMask, SearchProvider<String, String> searchProvider) {
        this.searchProvider = searchProvider;
        this.searchMask = searchMask; 
    }
    
    public abstract String getResult();
}
