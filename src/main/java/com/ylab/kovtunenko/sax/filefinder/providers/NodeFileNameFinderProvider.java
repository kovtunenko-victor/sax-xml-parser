package com.ylab.kovtunenko.sax.filefinder.providers;

import java.io.File;

import com.ylab.kovtunenko.sax.filefinder.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.filefinder.domain.Node;
import com.ylab.kovtunenko.sax.filefinder.providers.builders.BuilderProvider;

public class NodeFileNameFinderProvider {
    private final ParserProvider<ApplicationProperties> propertiesParser;
    private final BuilderProvider<XmlFileParserProvider> parserBuilder;
    private final BuilderProvider<NodeSearchProvider> finderBuilder;
    private final ReaderProvider<File, String> reader;
    
    public NodeFileNameFinderProvider(ParserProvider<ApplicationProperties> propertiesParser
            , BuilderProvider<XmlFileParserProvider> parserBuilder
            , BuilderProvider<NodeSearchProvider> finderBuilder
            , ReaderProvider<File, String> reader) {
        this.propertiesParser = propertiesParser;  
        this.parserBuilder = parserBuilder;
        this.finderBuilder = finderBuilder;
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
        ParserProvider<Node> xmlParser = parserBuilder.addParam("ApplicationProperties", appProps).addParam("ReaderProvider", reader).build();
        return xmlParser.parse();
    }
    
    private String doSearch(ApplicationProperties appProps, Node node) {
        SearchProvider<String, String> xmlFinder = finderBuilder.addParam("Node", node).build();
        return xmlFinder.search(appProps.getSearchMask());
    }
}
