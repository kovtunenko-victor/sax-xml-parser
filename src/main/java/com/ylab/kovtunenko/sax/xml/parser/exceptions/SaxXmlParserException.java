package com.ylab.kovtunenko.sax.xml.parser.exceptions;

public class SaxXmlParserException extends RuntimeException { 
    private static final long serialVersionUID = 1L;

    public SaxXmlParserException() {
        super();
    }
    
    public SaxXmlParserException(String message) {
        super(message);
    }
    
    public SaxXmlParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
