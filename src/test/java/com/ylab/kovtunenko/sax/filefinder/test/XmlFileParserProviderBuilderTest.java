package com.ylab.kovtunenko.sax.filefinder.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.filefinder.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.filefinder.exceptions.SaxXmlParserException;
import com.ylab.kovtunenko.sax.filefinder.providers.FileReaderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.XmlFileParserProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.builders.XmlFileParserProviderBuilder;

public class XmlFileParserProviderBuilderTest {
    private XmlFileParserProviderBuilder builder;
    private ApplicationProperties appProps = mock(ApplicationProperties.class);
    private FileReaderProvider reader = mock(FileReaderProvider.class);
    
    @Test
    public void buildMethodShuldReturnXmlFileParserProviderInstance() {
        builder = new XmlFileParserProviderBuilder();
        builder.addParam(XmlFileParserProviderBuilder.APPLICATION_PROPERTIES, appProps);
        builder.addParam(XmlFileParserProviderBuilder.READER_PROVIDER, reader);
        
        XmlFileParserProvider parser = builder.build();
        
        assertNotNull(parser);
    }
    
    @Test
    public void buildMethodShuldRizeExceptionWhenApplicationPropertiesIsNotSet() {
        Exception exception = assertThrows(SaxXmlParserException.class, () -> {
            builder = new XmlFileParserProviderBuilder();
            builder.addParam(XmlFileParserProviderBuilder.READER_PROVIDER, reader);
            
            builder.build();
        });
       
        String expectedMessage = "Can`t build XmlFileParserProvider";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void buildMethodShuldRizeExceptionWhenReaderIsNotSet() {
        Exception exception = assertThrows(SaxXmlParserException.class, () -> {
            builder = new XmlFileParserProviderBuilder();
            builder.addParam(XmlFileParserProviderBuilder.APPLICATION_PROPERTIES, appProps);
            
            builder.build();
        });
       
        String expectedMessage = "Can`t build XmlFileParserProvider";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void buildMethodShuldRizeExceptionWhenParamsIsNull() {
        Exception exception = assertThrows(SaxXmlParserException.class, () -> {
            builder = new XmlFileParserProviderBuilder();
            builder.addParam(XmlFileParserProviderBuilder.APPLICATION_PROPERTIES, null);
            builder.addParam(XmlFileParserProviderBuilder.READER_PROVIDER, null);
            
            builder.build();
        });
       
        String expectedMessage = "Can`t build XmlFileParserProvider";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
