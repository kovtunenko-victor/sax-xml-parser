package com.ylab.kovtunenko.sax.filefinder.domain;

public class ApplicationProperties {
    private final String fileName;
    private final String searchMask;
    
    public ApplicationProperties(String fileName) {
        this.fileName = fileName;
        this.searchMask = "";
    }
    
    public ApplicationProperties(String fileName, String searchMask) {
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
