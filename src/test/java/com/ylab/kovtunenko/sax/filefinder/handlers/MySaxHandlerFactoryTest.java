package com.ylab.kovtunenko.sax.filefinder.handlers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.filefinder.enums.HandlerType;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.RegexpSearchProvider;

public class MySaxHandlerFactoryTest {
    private final RegexpSearchProvider searchProvider = mock(RegexpSearchProvider.class);
    
    @Test
    public void newInstanceMethodShuldReturnDefaultHandler() {
        MySaxHandler handler = MySaxHandlerFactory.newInstance(HandlerType.DEFAULT, "", searchProvider);
        assertTrue(handler instanceof MySaxHandlerImpl);
    }
    
    @Test
    public void newInstanceMethodShuldRizeExceptionWhenHandlerTypeIsNull() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            MySaxHandlerFactory.newInstance(null, "", searchProvider);
        });
        
        String expectedMessage = "Handler type is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void newInstanceMethodShuldRizeExceptionWhenSearchMaskIsNull() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            MySaxHandlerFactory.newInstance(HandlerType.DEFAULT, null, searchProvider);
        });
        
        String expectedMessage = "Search mask is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void newInstanceMethodShuldRizeExceptionWhenSearchProviderIsNull() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            MySaxHandlerFactory.newInstance(HandlerType.DEFAULT, "", null);
        });
        
        String expectedMessage = "Search provider is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
