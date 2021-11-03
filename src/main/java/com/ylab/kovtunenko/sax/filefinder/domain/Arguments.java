package com.ylab.kovtunenko.sax.filefinder.domain;

public class Arguments {
    private final String fileName;
    private final String searchMask;
    
    public Arguments(String fileName) {
        this.fileName = fileName;
        this.searchMask = "";
    }
    
    public Arguments(String fileName, String searchMask) {
        this.fileName = fileName;
        this.searchMask = searchMask;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public String getSearchMask() {
        return searchMask;
    }
}
