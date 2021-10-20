package com.ylab.kovtunenko.sax.xml.parser.domain;

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
    
    public ApplicationProperties(ApplicationProperties appProps) {
        this.fileName = appProps.fileName;
        this.searchMask = appProps.searchMask;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public String getSearchMask() {
        return searchMask;
    }
}
