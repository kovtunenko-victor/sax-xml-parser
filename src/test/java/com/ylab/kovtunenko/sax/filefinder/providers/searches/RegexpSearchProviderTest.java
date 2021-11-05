package com.ylab.kovtunenko.sax.filefinder.providers.searches;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.RegexpSearchProvider;

public class RegexpSearchProviderTest {
    private final RegexpSearchProvider provider = new RegexpSearchProvider();
    
    @Test
    public void parseMethodShuldReturnTrueIfSearchValueFindedInSearchData() {
        assertTrue(provider.search("Test.java", "T.+\\.j.*"));
    }
    
    @Test
    public void parseMethodShuldReturnFalseIfSearchValueNotFindedInSearchData() {
        assertFalse(provider.search("Simple.java", "T.+\\.j.*"));
    }
    
    @Test
    public void parseMethodShuldReturnTrueIfSearchValueIsNull() {
        assertTrue(provider.search("Simple.java", null));
    }
    
    @Test
    public void parseMethodShuldReturnTrueIfSearchValueIsEmpty() {
        assertTrue(provider.search("Simple.java", ""));
    }
    
    @Test
    public void parseMethodShuldRizeExceptionWhenSearchDataIsNull() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            provider.search(null, "");
        });
        
        String expectedMessage = "Data for search is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
