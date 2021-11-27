package com.ylab.kovtunenko.sax.filefinder.providers.searches;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.filefinder.enums.MaskType;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.SearchProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.SearchProviderFactory;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.MaskSearchProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.RegexpSearchProvider;

public class SearchProviderFactoryTest {
    @Test
    public void newInstanceMethodShuldReturnEmptySearchProvider() {
        SearchProvider<String, String> provider = SearchProviderFactory.newInstance(MaskType.NO_MASK);
        assertTrue(provider.search(null, null));
    }
    
    @Test
    public void newInstanceMethodShuldReturnRegexpSearchProvider() {
        SearchProvider<String, String> provider = SearchProviderFactory.newInstance(MaskType.REGEXP);
        assertTrue(provider instanceof RegexpSearchProvider);
    }
    
    @Test
    public void newInstanceMethodShuldReturnNameSearchProvider() {
        SearchProvider<String, String> provider = SearchProviderFactory.newInstance(MaskType.MASK);
        assertTrue(provider instanceof MaskSearchProvider);
    }
    
    @Test
    public void newInstanceMethodShuldRizeExceptionWhenMaskTypeIsNull() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            SearchProviderFactory.newInstance(null);
        });
        
        String expectedMessage = "Mask type is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void newInstanceMethodShuldRizeExceptionWhenMaskTypeIsUnknown() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            SearchProviderFactory.newInstance(MaskType.UNKNOWN);
        });
        
        String expectedMessage = "MaskType [UNKNOWN] is unknown";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
