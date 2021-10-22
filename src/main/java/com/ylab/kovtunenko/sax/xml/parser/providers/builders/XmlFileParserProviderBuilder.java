package com.ylab.kovtunenko.sax.xml.parser.providers.builders;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.ylab.kovtunenko.sax.xml.parser.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.xml.parser.exceptions.SaxXmlParserException;
import com.ylab.kovtunenko.sax.xml.parser.providers.ReaderProvider;
import com.ylab.kovtunenko.sax.xml.parser.providers.XmlFileParserProvider;

public class XmlFileParserProviderBuilder implements ProviderBuilder <XmlFileParserProvider> {
    private final Map<String, Object> params = new HashMap<>();
    
    public static final String APPLICATION_PROPERTIES = "ApplicationProperties";
    public static final String READER_PROVIDER = "ReaderProvider";
    
    @Override
    public ProviderBuilder<XmlFileParserProvider> addParam(String key, Object value) {
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