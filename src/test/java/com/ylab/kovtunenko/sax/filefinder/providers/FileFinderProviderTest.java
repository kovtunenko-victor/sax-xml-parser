package com.ylab.kovtunenko.sax.filefinder.providers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.ArgumentsParserProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.XmlFileParserProvider;

public class FileFinderProviderTest {
    private final ArgumentsParserProvider argsParser = mock(ArgumentsParserProvider.class);
    private final XmlFileParserProvider xmlParser = mock(XmlFileParserProvider.class);
    private FileFinderProvider finder;
    
    @Test
    public void findMethodShuldDontRizeException() {
        assertDoesNotThrow(() -> {
            finder = new FileFinderProvider(argsParser, xmlParser);
            String[] args = {""};
            finder.find(args);
        });
    }
    
    @Test
    public void findMethodShuldRizeExceptionWhenArgumentsParserIsNull() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            finder = new FileFinderProvider(null, xmlParser);
            String[] args = {""};
            finder.find(args);
        });
        
        String expectedMessage = "ArgumentsParser is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void findMethodShuldRizeExceptionWhenXmlFileParserIsNull() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            finder = new FileFinderProvider(argsParser, null);
            String[] args = {""};
            finder.find(args);
        });
        
        String expectedMessage = "Xml parser is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void findMethodShuldRizeExceptionWhenArgumentsIsNull() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            finder = new FileFinderProvider(argsParser, xmlParser);
            finder.find(null);
        });
        
        String expectedMessage = "Arguments array is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
}
