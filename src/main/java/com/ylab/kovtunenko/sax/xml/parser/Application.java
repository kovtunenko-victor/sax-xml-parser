package com.ylab.kovtunenko.sax.xml.parser;

import com.ylab.kovtunenko.sax.xml.parser.providers.FileReaderProvider;
import com.ylab.kovtunenko.sax.xml.parser.providers.InputPropertiesParserProvider;
import com.ylab.kovtunenko.sax.xml.parser.providers.SaxXmlParserProvider;
import com.ylab.kovtunenko.sax.xml.parser.providers.builders.XmlFileParserProviderBuilder;
import com.ylab.kovtunenko.sax.xml.parser.providers.builders.XmlFileSearchProviderBuilder;

public class Application {
    public static void main(String[] args) { 
        
        SaxXmlParserProvider provider = new SaxXmlParserProvider(new InputPropertiesParserProvider(args)
                , new XmlFileParserProviderBuilder() 
                , new XmlFileSearchProviderBuilder()
                , new FileReaderProvider());
        
        System.out.println(provider.doSerch());
    }
}
