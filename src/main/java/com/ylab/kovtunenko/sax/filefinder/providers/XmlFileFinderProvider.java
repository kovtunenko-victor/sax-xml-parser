package com.ylab.kovtunenko.sax.filefinder.providers;

import java.io.File;

import com.ylab.kovtunenko.sax.filefinder.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.filefinder.exceptions.SaxXmlParserException;
import com.ylab.kovtunenko.sax.filefinder.providers.builders.BuilderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.builders.XmlFileParserProviderBuilder;

public class XmlFileFinderProvider {
    private final ParserProvider<ApplicationProperties> propertiesParser;
    private final BuilderProvider<XmlFileParserProvider> parserBuilder;
    private final BuilderProvider<RegexSearchProvider> finderBuilder;
    private final ReaderProvider<File, String> reader;
    
    public XmlFileFinderProvider(ParserProvider<ApplicationProperties> propertiesParser
            , BuilderProvider<XmlFileParserProvider> parserBuilder
            , BuilderProvider<RegexSearchProvider> finderBuilder
            , ReaderProvider<File, String> reader) {
        this.propertiesParser = propertiesParser;  
        this.parserBuilder = parserBuilder;
        this.finderBuilder = finderBuilder;
        this.reader = reader;
    }
    
    public String parseAndSearch() {
        ApplicationProperties appProps = getApplicationProperties();
        ParserProvider<String> parser = getParser(appProps);
        return parser.parse();
    }
    
    private ApplicationProperties getApplicationProperties() { 
        if(propertiesParser == null) {
            throw new SaxXmlParserException("Properties parser is null");
        }
        
        return propertiesParser.parse();
    }
    
    private ParserProvider<String> getParser(ApplicationProperties appProps) {
        if(parserBuilder == null) {
            throw new SaxXmlParserException("Xml parser builder is null");
        }
        
        return parserBuilder
                .addParam(XmlFileParserProviderBuilder.APPLICATION_PROPERTIES, appProps)
                .addParam(XmlFileParserProviderBuilder.READER_PROVIDER, reader)
                .addParam(XmlFileParserProviderBuilder.SEARCH_PROVIDER_BUILDER, finderBuilder)
                .build();
    }
}
