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

public class DefaultHandlerTest {
    private final RegexpSearchProvider searchProvider = mock(RegexpSearchProvider.class);
    private final static String MASK = ".+";
    private final Attributes attributes = mock(Attributes.class);
    private DafaultHandler handler;
    
    
    @Test
    public void getResultMethodShuldReturnResultData() throws SAXException {
        emulateEvents("dir-0001", false);
        assertEquals(handler.getResult(), "/dir-0001/file-0003.xlsx\r\n/file-0001.xlsx\r\n/file-0002.xlsx\r\n");
    }
    
    @Test
    public void getResultMethodShuldReturnResultDataWithChildNameEmpty() throws SAXException {
        emulateEvents("dir-0001", true);
        assertEquals(handler.getResult(), "/dir-0001/file-0003.xlsx\r\n/file-0002.xlsx\r\n");
    }
    
    @Test
    public void getResultMethodShuldReturnResultDataWithChildNameExists() throws SAXException {
        emulateEvents("", true);
        assertEquals(handler.getResult(), "/null/file-0003.xlsx\r\n/file-0002.xlsx\r\n");
    }
    
    @Test
    public void getResultMethodShuldRizeExceptionWhenXmlNotInFormat() throws SAXException {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            handler = new DafaultHandler(MASK, searchProvider);
            handler.getResult();
        });

        String expectedMessage = "Xml file not in format";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    private void emulateEvents(String dirName, boolean disableName) throws SAXException {
        when(searchProvider.search("file-0001.xlsx", MASK)).thenReturn(true);
        when(searchProvider.search("file-0002.xlsx", MASK)).thenReturn(true);
        when(searchProvider.search("file-0003.xlsx", MASK)).thenReturn(true);
        handler = new DafaultHandler(MASK, searchProvider);
        
        emulateStartElement("node", false);
        emulateStartElement("name");
        emulateCharacters("/");
        emulateEndElement("name");
        emulateStartElement("children");
        emulateStartElement("child", false);
        emulateStartElement("name");
        emulateCharacters(dirName);
        emulateEndElement("name");
        emulateStartElement("children");
        emulateStartElement("child", true);
        emulateStartElement("name");
        emulateCharacters("file-0003.xlsx");
        emulateEndElement("name");
        emulateEndElement("child");
        emulateEndElement("children");
        emulateEndElement("child");
        emulateStartElement("child", true);
        
        if(disableName != true) {
            emulateStartElement("name");
            emulateCharacters("file-0001.xlsx");
            emulateEndElement("name");
        }
        
        emulateEndElement("child");
        emulateStartElement("child", true);
        emulateStartElement("name");
        emulateCharacters("file-0002.xlsx");
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
