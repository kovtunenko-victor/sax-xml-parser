package com.ylab.kovtunenko.sax.filefinder.providers;

import java.io.File;

import com.ylab.kovtunenko.sax.filefinder.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.filefinder.domain.Node;
import com.ylab.kovtunenko.sax.filefinder.providers.builders.BuilderProvider;

public class FileNameFinderProvider {
    private final ParserProvider<ApplicationProperties> propertiesParser;
    private final BuilderProvider<XmlFileParserProvider> xmlParserBuilder;
    private final BuilderProvider<NodeSearchProvider> xmlFinderBuilder;
    private final ReaderProvider<File, String> reader;
    
    public FileNameFinderProvider(ParserProvider<ApplicationProperties> propertiesParser
            , BuilderProvider<XmlFileParserProvider> xmlParserBuilder
            , BuilderProvider<NodeSearchProvider> xmlFinderBuilder
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
