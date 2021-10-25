package com.ylab.kovtunenko.sax.filefinder.providers.builders;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.ylab.kovtunenko.sax.filefinder.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.filefinder.exceptions.SaxXmlParserException;
import com.ylab.kovtunenko.sax.filefinder.providers.ReaderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.XmlFileParserProvider;

public class XmlFileParserProviderBuilder implements BuilderProvider <XmlFileParserProvider> {
    private final Map<String, Object> params = new HashMap<>();
    
    public static final String APPLICATION_PROPERTIES = "ApplicationProperties";
    public static final String READER_PROVIDER = "ReaderProvider";
    
    @Override
    public BuilderProvider<XmlFileParserProvider> addParam(String key, Object value) {
        params.put(key, value);
        return this;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public XmlFileParserProvider build() {
        ApplicationProperties appProps = (ApplicationProperties)params.get(APPLICATION_PROPERTIES);
        ReaderProvider<File, String> reader = (ReaderProvider<File, String>)params.get(READER_PROVIDER);
        
        if(appProps == null || reader == null) {
            throw new SaxXmlParserException("Can`t build XmlFileParserProvider");
        }
        
        return new XmlFileParserProvider(appProps, reader);
    }
}