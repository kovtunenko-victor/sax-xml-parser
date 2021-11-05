package com.ylab.kovtunenko.sax.filefinder.providers;

import com.ylab.kovtunenko.sax.filefinder.domain.Arguments;

public class FileFinderProvider {
    private final ParserProvider<Arguments, String[]> argsParser;
    private final ParserProvider<String, Arguments> xmlParser;
    
    public FileFinderProvider(ParserProvider<Arguments, String[]> argsParser, ParserProvider<String, Arguments> xmlParser) {
        this.argsParser = argsParser;
        this.xmlParser = xmlParser;
    }
    
    public String find(String[] args) {
        Arguments arguments = argsParser.parse(args);
        return xmlParser.parse(arguments);
    }
}
