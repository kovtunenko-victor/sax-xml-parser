package com.ylab.kovtunenko.sax.filefinder.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.filefinder.exceptions.SaxXmlParserException;
import com.ylab.kovtunenko.sax.filefinder.providers.FileReaderProvider;

public class FileReaderProviderTest {
    private FileReaderProvider reader = new ReaderProviderTestImpl();
    
    @Test
    public void readMethodShuldReturnFileByInputString( ) {
        File result = reader.read("DataFile.xml");
        assertEquals(result.exists(), true);
    }
    
    @Test
    public void readMethodShuldRizeExceptionWhenFileNotFound( ) {    
        Exception exception = assertThrows(SaxXmlParserException.class, () -> {
            reader.read("NoFile.xml");
        });

        String expectedMessage = "File [NoFile.xml] not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
