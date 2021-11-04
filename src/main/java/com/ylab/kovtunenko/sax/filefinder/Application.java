package com.ylab.kovtunenko.sax.filefinder;

import com.ylab.kovtunenko.sax.filefinder.domain.Arguments;
import com.ylab.kovtunenko.sax.filefinder.providers.FileFinderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.ParserProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.SearchProviderFactory;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.ArgumentsParserProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.FileReaderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.SearchValueParser;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.XmlFileParserProvider;

public class Application {
    public static void main(String[] args) {
        
        
        FileFinderProvider finder = new FileFinderProvider();
    }
}
