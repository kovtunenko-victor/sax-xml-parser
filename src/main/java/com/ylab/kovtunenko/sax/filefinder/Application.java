package com.ylab.kovtunenko.sax.filefinder;

import com.ylab.kovtunenko.sax.filefinder.providers.impl.InputArgumentsParser;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.RegexpSearchValueParserFactory;

public class Application {
    public static void main(String[] args) {
        InputArgumentsParser argumentParser = new InputArgumentsParser(args, new RegexpSearchValueParserFactory());
        
    }
}
