package com.ylab.kovtunenko.sax.filefinder.providers.readers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.FileReaderProvider;
import com.ylab.kovtunenko.sax.filefinder.utils.TestUtils;

public class FileReaderProviderTest {
    private final FileReaderProvider reader = new FileReaderProvider();
    
    @Test
    public void readMethodShuldReturnFileByInputString() {
        File result = reader.read(TestUtils.getResourceFilePath("DataFile.xml"));
        assertEquals(result.exists(), true);
    }
    
    @Test
    public void readMethodShuldRizeExceptionWhenFileNotFound( ) {    
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            reader.read("NoFile.xml");
        });

        String expectedMessage = "File [NoFile.xml] not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}