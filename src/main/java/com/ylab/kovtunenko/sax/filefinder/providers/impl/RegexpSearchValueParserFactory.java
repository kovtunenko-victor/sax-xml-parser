package com.ylab.kovtunenko.sax.filefinder.providers.impl;

import java.util.HashMap;
import java.util.Map;

import com.ylab.kovtunenko.sax.filefinder.constants.GlobalConstants;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.FactoryProvider;

public class RegexpSearchValueParserFactory implements FactoryProvider<RegexpSearchValueParser> {
    private final Map<String, Object> params = new HashMap<>();
    
    @Override
    public FactoryProvider<RegexpSearchValueParser> addParam(String key, Object value) {
        params.put(key, value);
        return this;
    }
    
    @Override
    public RegexpSearchValueParser build() {
        String searchData = (String)params.get(GlobalConstants.SEARCH_DATA);
        
        if(searchData == null) {
            throw new FileFinderAppException("Can't build RegexSearchProvider");
        }
        
        return new RegexpSearchValueParser(searchData);
    }
}
