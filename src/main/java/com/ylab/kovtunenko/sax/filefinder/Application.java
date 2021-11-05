package com.ylab.kovtunenko.sax.filefinder;

import com.ylab.kovtunenko.sax.filefinder.domain.Arguments;
import com.ylab.kovtunenko.sax.filefinder.enums.HandlerType;
import com.ylab.kovtunenko.sax.filefinder.providers.FileFinderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.ParserProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.ArgumentsParserProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.FileReaderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.SearchValueParserProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.XmlFileParserProvider;

public class Application {
    public static void main(String[] args) {
        ParserProvider<Arguments, String[]> argsParser = new ArgumentsParserProvider(new SearchValueParserProvider());
        ParserProvider<String, Arguments> xmlParser = new XmlFileParserProvider(HandlerType.DEFAULT, new FileReaderProvider());
        
        FileFinderProvider finder = new FileFinderProvider(argsParser, xmlParser);
        
        System.out.println(finder.find(args));
    }
}
