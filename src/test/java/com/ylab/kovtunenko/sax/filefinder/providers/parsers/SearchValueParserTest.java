package com.ylab.kovtunenko.sax.filefinder.providers.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.filefinder.domain.MaskType;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.SearchValueParser;

public class SearchValueParserTest {
    private final SearchValueParser parser = new SearchValueParser();
    
    @Test
    public void parseMethodShuldReturnNameType() {
        MaskType type = parser.parse("*.test");
        assertEquals(type, MaskType.NAME);
    }
    
    @Test
    public void parseMethodShuldReturnExtensionType() {
        MaskType type = parser.parse("test.*");
        assertEquals(type, MaskType.EXTENSION);
    }
    
    @Test
    public void parseMethodShuldReturnRegexpType() {
        MaskType type = parser.parse(".+");
        assertEquals(type, MaskType.REGEXP);
    }
    
    @Test
    public void parseMethodShuldRizeExceptionWhenSearchValueIsNull() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            parser.parse(null);
        });
        
        String expectedMessage = "Search value is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
