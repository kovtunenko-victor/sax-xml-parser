package com.ylab.kovtunenko.sax.filefinder.providers.searches;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.filefinder.enums.MaskType;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.SearchProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.SearchProviderFactory;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.ExtensionSearchProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.NameSearchProvider;
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
        SearchProvider<String, String> provider = SearchProviderFactory.newInstance(MaskType.NAME);
        assertTrue(provider instanceof NameSearchProvider);
    }
    
    @Test
    public void newInstanceMethodShuldReturnExtensionSearchProvider() {
        SearchProvider<String, String> provider = SearchProviderFactory.newInstance(MaskType.EXTENSION);
        assertTrue(provider instanceof ExtensionSearchProvider);
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
}
