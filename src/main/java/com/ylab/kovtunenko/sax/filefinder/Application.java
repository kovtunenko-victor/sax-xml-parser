package com.ylab.kovtunenko.sax.filefinder;

import com.ylab.kovtunenko.sax.filefinder.providers.XmlFileFinderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.FileReaderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.InputPropertiesParserProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.builders.RegexSearchProviderBuilder;
import com.ylab.kovtunenko.sax.filefinder.providers.builders.XmlFileParserProviderBuilder;

public class Application {
    public static void main(String[] args) {
        XmlFileFinderProvider provider = new XmlFileFinderProvider(new InputPropertiesParserProvider(args)
                , new XmlFileParserProviderBuilder() 
                , new RegexSearchProviderBuilder() 
                , new FileReaderProvider());
        
        System.out.println(provider.parseAndSearch());
    }
}
