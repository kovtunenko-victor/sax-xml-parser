package com.ylab.kovtunenko.sax.filefinder.providers;

import java.io.File;

import com.ylab.kovtunenko.sax.filefinder.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.filefinder.exceptions.SaxXmlParserException;
import com.ylab.kovtunenko.sax.filefinder.providers.builders.BuilderProvider;

public class XmlFileFinderProvider implements SearchProvider<String> {
    private final ParserProvider<ApplicationProperties> propertiesParser;
    private final BuilderProvider<XmlFileParserProvider> parserBuilder;
    private final ReaderProvider<File, String> reader;
    
    public XmlFileFinderProvider(ParserProvider<ApplicationProperties> propertiesParser
            , BuilderProvider<XmlFileParserProvider> parserBuilder
            , ReaderProvider<File, String> reader) {
        this.propertiesParser = propertiesParser;  
        this.parserBuilder = parserBuilder;
        this.reader = reader;
    }
    
    public String search() {
        ApplicationProperties appProps = getApplicationProperties();
        return doSearch(appProps);
    }
    
    private ApplicationProperties getApplicationProperties() { 
        if(propertiesParser == null) {
            throw new SaxXmlParserException("Properties parser is null");
        }
        
        return propertiesParser.parse();
    }
    
    private String doSearch(ApplicationProperties appProps) {
        if(parserBuilder == null) {
            throw new SaxXmlParserException("Xml parser builder is null");
        }
        
        ParserProvider<String> xmlParser = parserBuilder.addParam("ApplicationProperties", appProps).addParam("ReaderProvider", reader).build();
        return xmlParser.parse();
    }
}
