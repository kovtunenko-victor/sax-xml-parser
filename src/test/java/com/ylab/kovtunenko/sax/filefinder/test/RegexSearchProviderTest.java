package com.ylab.kovtunenko.sax.filefinder.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.filefinder.exceptions.SaxXmlParserException;
import com.ylab.kovtunenko.sax.filefinder.providers.RegexSearchProvider;

public class RegexSearchProviderTest {
    private RegexSearchProvider finder;

    @Test
    public void parseMethtodShuldReturnTrueIfSearchDataContainSearchValue() {
        finder = new RegexSearchProvider("File.txt");
        boolean result = finder.search(".+");
        assertTrue(result);
    }

    @Test
    public void parseMethtodShuldReturnFalseIfSearchDataNotContainSearchValue() {
        finder = new RegexSearchProvider("File.txt");
        boolean result = finder.search("[0-9]+");
        assertFalse(result);
    }

    @Test
    public void parseMethtodShuldReturnTrueIfSearchValueIsNull() {
        finder = new RegexSearchProvider("File.txt");
        boolean result = finder.search(null);
        assertTrue(result);
    }

    @Test
    public void parseMethtodShuldReturnFalseIfSearchDataIsNull() {
        Exception exception = assertThrows(SaxXmlParserException.class, () -> {
            finder = new RegexSearchProvider(null);
            finder.search(".+");
        });
        
        String expectedMessage = "Data for search is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
