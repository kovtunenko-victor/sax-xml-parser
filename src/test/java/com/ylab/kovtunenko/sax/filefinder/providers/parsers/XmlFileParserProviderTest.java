package com.ylab.kovtunenko.sax.filefinder.providers.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.filefinder.domain.Arguments;
import com.ylab.kovtunenko.sax.filefinder.enums.HandlerType;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.FileReaderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.XmlFileParserProvider;
import com.ylab.kovtunenko.sax.filefinder.utils.TestUtils;

public class XmlFileParserProviderTest {
    private final FileReaderProvider reader = mock(FileReaderProvider.class);
    private XmlFileParserProvider provider;
    
    @Test
    public void parseMethodShuldReturnPeasedData() {
        String fileName = "TestFile3.xml";
        Arguments args = new Arguments(fileName);
        when(reader.read(fileName)).thenReturn(new File(TestUtils.getResourceFilePath(fileName)));
        provider = new XmlFileParserProvider(HandlerType.DEFAULT, reader);
        
        String result = provider.parse(args);

        assertEquals(result, "/file-09938329.pdf\r\n");
    }
    
    @Test
    public void parseMethodShuldRizeExceptionWhenReaderIsNull() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            Arguments args = new Arguments(null);
            provider = new XmlFileParserProvider(HandlerType.DEFAULT, null);
            provider.parse(args);
        });
        
        String expectedMessage = "Reader is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void parseMethodShuldRizeExceptionWhenHandlerTypeIsNull() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            Arguments args = new Arguments(null);
            provider = new XmlFileParserProvider(null, reader);
            provider.parse(args);
        });
        
        String expectedMessage = "Handler type is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void parseMethodShuldRizeExceptionWhenArgumentsIsNull() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            provider = new XmlFileParserProvider(HandlerType.DEFAULT, reader);
            provider.parse(null);
        });
        
        String expectedMessage = "Arguments is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
