package com.ylab.kovtunenko.sax.filefinder.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.filefinder.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.filefinder.domain.Node;
import com.ylab.kovtunenko.sax.filefinder.exceptions.SaxXmlParserException;
import com.ylab.kovtunenko.sax.filefinder.providers.FileReaderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.XmlFileParserProvider;

public class XmlFileParserProviderTest {
    private XmlFileParserProvider parser;
    private ApplicationProperties appProps = mock(ApplicationProperties.class);
    private FileReaderProvider reader = mock(FileReaderProvider.class);

    
    @Test
    public void parseMethtodShuldReturnFilledNode_DataFile() throws URISyntaxException {
        when(appProps.getFileName()).thenReturn("DataFile.xml");
        when(reader.read("DataFile.xml")).thenReturn(new File(this.getClass().getClassLoader().getResource("DataFile.xml").toURI()));
        parser = new XmlFileParserProvider(appProps, reader);
        
        Node node = parser.parse();
        
        assertEquals(node.getName(), "/");
        assertEquals(node.getIsFile(), false);
        assertEquals(node.getLevel(), 0);
    }
    
    @Test
    public void parseMethtodShuldReturnFilledNode_TestFile1() throws URISyntaxException {
        when(appProps.getFileName()).thenReturn("TestFile1.xml");
        when(reader.read("TestFile1.xml")).thenReturn(new File(this.getClass().getClassLoader().getResource("TestFile1.xml").toURI()));
        parser = new XmlFileParserProvider(appProps, reader);
        
        Node node = parser.parse();
        
        assertEquals(node.getName(), "/");
        assertEquals(node.getIsFile(), false);
        assertEquals(node.getLevel(), 0);
    }
    
    @Test
    public void parseMethtodShuldReturnEmptyNode_TestFile2() throws URISyntaxException {
        when(appProps.getFileName()).thenReturn("TestFile2.xml");
        when(reader.read("TestFile2.xml")).thenReturn(new File(this.getClass().getClassLoader().getResource("TestFile2.xml").toURI()));
        parser = new XmlFileParserProvider(appProps, reader);
        
        Node node = parser.parse();
        
        assertNull(node.getName());
        assertEquals(node.getIsFile(), false);
        assertEquals(node.getLevel(), 0);
    }
    
    @Test
    public void parseMethtodShuldRizeExceptionWhenXmlNotFormat() throws URISyntaxException {
        Exception exception = assertThrows(SaxXmlParserException.class, () -> {
            when(appProps.getFileName()).thenReturn("TestFile.xml");
            when(reader.read("TestFile.xml")).thenReturn(new File(this.getClass().getClassLoader().getResource("TestFile.xml").toURI()));
            parser = new XmlFileParserProvider(appProps, reader);
            parser.parse();
        });
       
        String expectedMessage = "Xml file not in format";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void parseMethtodShuldRizeExceptionWhenFileNotFound() throws URISyntaxException {
        Exception exception = assertThrows(SaxXmlParserException.class, () -> {
            parser = new XmlFileParserProvider(appProps, reader);
            parser.parse();
        });
       
        String expectedMessage = "Xml file parse exception";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
