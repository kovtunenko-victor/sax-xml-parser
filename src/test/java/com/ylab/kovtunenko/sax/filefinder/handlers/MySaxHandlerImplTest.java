package com.ylab.kovtunenko.sax.filefinder.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.RegexpSearchProvider;

public class MySaxHandlerImplTest {
    private final RegexpSearchProvider searchProvider = mock(RegexpSearchProvider.class);
    private final String mask = ".+";
    private final Attributes attributes = mock(Attributes.class);
    private MySaxHandlerImpl handler;
    
    
    @Test
    public void getResultMethodShuldReturnResultData() throws SAXException {
        emulateEvents();
        assertEquals(handler.getResult(), "/file-0001\r\n");
    }
    
    @Test
    public void getResultMethodShuldRizeExceptionWhenXmlNotInFormat() throws SAXException {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            handler = new MySaxHandlerImpl(mask, searchProvider);
            handler.getResult();
        });

        String expectedMessage = "Xml file not in format";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    private void emulateEvents() throws SAXException {
        when(searchProvider.search("file-0001", ".+")).thenReturn(true);
        handler = new MySaxHandlerImpl(mask, searchProvider);
        
        emulateStartElement("node", false);
        emulateStartElement("name");
        emulateCharacters("/");
        emulateEndElement("name");
        emulateStartElement("children");
        emulateStartElement("child", false);
        emulateStartElement("name");
        emulateCharacters("dir-0001");
        emulateEndElement("name");
        emulateEndElement("child");
        emulateStartElement("child", true);
        emulateStartElement("name");
        emulateCharacters("file-0001");
        emulateEndElement("name");
        emulateEndElement("child");
        emulateEndElement("children");
        emulateEndElement("node");
    }
    
    private void emulateStartElement(String qName) throws SAXException {
        handler.startElement("", "", qName, attributes);
    }
    
    private void emulateStartElement(String qName, Boolean isFile) throws SAXException {
        when(attributes.getValue("is-file")).thenReturn(isFile.toString());
        handler.startElement("", "", qName, attributes);
    }
    
    private void emulateEndElement(String qName) throws SAXException {
        handler.endElement("", "", qName);
    }
    
    private void emulateCharacters(String data) throws SAXException {
        handler.characters(data.toCharArray(), 0, data.length());
    }
}
