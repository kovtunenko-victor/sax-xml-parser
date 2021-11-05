package com.ylab.kovtunenko.sax.filefinder.providers;

import com.ylab.kovtunenko.sax.filefinder.domain.Arguments;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;

public class FileFinderProvider {
    private final ParserProvider<Arguments, String[]> argsParser;
    private final ParserProvider<String, Arguments> xmlParser;
    
    public FileFinderProvider(ParserProvider<Arguments, String[]> argsParser, ParserProvider<String, Arguments> xmlParser) {
        this.argsParser = argsParser;
        this.xmlParser = xmlParser;
    }
    
    public String find(String[] args) {
        if(args == null) {
            throw new FileFinderAppException("Arguments array is null");
        }
        
        if(argsParser == null) {
            throw new FileFinderAppException("ArgumentsParser is null");
        }
        
        if(xmlParser == null) {
            throw new FileFinderAppException("Xml parser is null");
        }
        
        Arguments arguments = argsParser.parse(args);
        return xmlParser.parse(arguments);
    }
}
