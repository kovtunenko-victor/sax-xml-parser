package com.ylab.kovtunenko.sax.xml.parser.providers;

import java.io.File;

import com.ylab.kovtunenko.sax.xml.parser.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.xml.parser.domain.Node;
import com.ylab.kovtunenko.sax.xml.parser.providers.builders.ProviderBuilder;

public class SaxXmlParserProvider {
    private final ParserProvider<ApplicationProperties> propertiesParser;
    private final ProviderBuilder<XmlFileParserProvider> xmlParserBuilder;
    private final ProviderBuilder<XmlFileSearchProvider> xmlFinderBuilder;
    private final ReaderProvider<File, String> reader;
    
    public SaxXmlParserProvider(ParserProvider<ApplicationProperties> propertiesParser
            , ProviderBuilder<XmlFileParserProvider> xmlParserBuilder
            , ProviderBuilder<XmlFileSearchProvider> xmlFinderBuilder
            , ReaderProvider<File, String> reader) {
        this.propertiesParser = propertiesParser;  
        this.xmlParserBuilder = xmlParserBuilder;
        this.xmlFinderBuilder = xmlFinderBuilder;
        this.reader = reader;
    }
    
    public String doSerch() {
        ApplicationProperties appProps = getApplicationProperties();
        Node node = getNode(appProps);
        
        return doSearch(appProps, node);
    }
    
    public String prinTree() {
        ApplicationProperties appProps = getApplicationProperties();
        Node node = getNode(appProps);
        
        return node.toString();
    }
    
    private ApplicationProperties getApplicationProperties() { 
        return propertiesParser.parse();
    }
    
    private Node getNode(ApplicationProperties appProps) {
        ParserProvider<Node> xmlParser = xmlParserBuilder.addParam("ApplicationProperties", appProps).addParam("ReaderProvider", reader).build();
        return xmlParser.parse();
    }
    
    private String doSearch(ApplicationProperties appProps, Node node) {
        SearchProvider<String, String> xmlFinder = xmlFinderBuilder.addParam("Node", node).build();
        return xmlFinder.search(appProps.getSearchMask());
    }
}
