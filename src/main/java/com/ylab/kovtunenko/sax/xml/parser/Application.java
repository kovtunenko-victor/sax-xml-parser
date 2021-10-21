package com.ylab.kovtunenko.sax.xml.parser;


import com.ylab.kovtunenko.sax.xml.parser.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.xml.parser.domain.Node;
import com.ylab.kovtunenko.sax.xml.parser.providers.FileReaderProvider;
import com.ylab.kovtunenko.sax.xml.parser.providers.InputPropertiesParserProvider;
import com.ylab.kovtunenko.sax.xml.parser.providers.ParserProvider;
import com.ylab.kovtunenko.sax.xml.parser.providers.SearchProvider;
import com.ylab.kovtunenko.sax.xml.parser.providers.XmlFileParserProvider;
import com.ylab.kovtunenko.sax.xml.parser.providers.XmlFileSearchProvider;

public class Application {
    public static void main(String[] args) {
        ParserProvider<ApplicationProperties> propertiesParser = new InputPropertiesParserProvider(args);
        
        ApplicationProperties appProps = propertiesParser.parse();
        
        ParserProvider<Node> xmlParser = new XmlFileParserProvider(appProps, new FileReaderProvider());
        
        Node node = xmlParser.parse();
     
        SearchProvider<String, String> xmlFinder = new XmlFileSearchProvider(node);
        
        String result = xmlFinder.search(appProps.getSearchMask());
        
        System.out.println(result);
    }
}
