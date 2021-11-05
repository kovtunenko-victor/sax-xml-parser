package com.ylab.kovtunenko.sax.filefinder.handlers;

import com.ylab.kovtunenko.sax.filefinder.domain.HandlerType;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.SearchProvider;

public class MySaxHandlerFactory {
    public static MySaxHandler create(HandlerType handlerType, String searchMask, SearchProvider<String, String> searchProvider) {
        if (handlerType == null) {
            throw new FileFinderAppException("Handler type is null");
        }
        
        if (searchMask == null) {
            throw new FileFinderAppException("Search mask is null");
        }
        
        if (searchProvider == null) {
            throw new FileFinderAppException("Search provider is null");
        }
        
        switch(handlerType) {
            case DEFAULT:
                return new MySaxHandlerImpl(searchMask, searchProvider);
            default:
                throw new FileFinderAppException(String.format("HandlerType [%s] is unknown", handlerType.name()));
        }
            
    }
}
