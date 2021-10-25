package com.ylab.kovtunenko.sax.filefinder;

import com.ylab.kovtunenko.sax.filefinder.providers.FileNameFinderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.FileReaderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.InputPropertiesParserProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.builders.NodeSearchProviderBuilder;
import com.ylab.kovtunenko.sax.filefinder.providers.builders.XmlFileParserProviderBuilder;

public class Application {
    public static void main(String[] args) { 
        
        FileNameFinderProvider provider = new FileNameFinderProvider(new InputPropertiesParserProvider(args)
                , new XmlFileParserProviderBuilder() 
                , new NodeSearchProviderBuilder()
                , new FileReaderProvider());
        
        System.out.println(provider.doSerch());
    }
}
