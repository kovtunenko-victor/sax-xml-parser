package com.ylab.kovtunenko.xml.filefinder.exceptions;

public class FileFinderAppException extends RuntimeException { 
    private static final long serialVersionUID = 1L;

    public FileFinderAppException() {
        super();
    }
    
    public FileFinderAppException(String message) {
        super(message);
    }
    
    public FileFinderAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
