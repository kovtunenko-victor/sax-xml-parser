package com.ylab.kovtunenko.sax.filefinder.providers.builders;


import java.util.HashMap;
import java.util.Map;

import com.ylab.kovtunenko.sax.filefinder.exceptions.SaxXmlParserException;
import com.ylab.kovtunenko.sax.filefinder.providers.RegexSearchProvider;

public class RegexSearchProviderBuilder implements BuilderProvider<RegexSearchProvider> {
private final Map<String, Object> params = new HashMap<>();
    
    public static final String SEARCH_DATA = "SearchData";
    
    @Override
    public BuilderProvider<RegexSearchProvider> addParam(String key, Object value) {
        params.put(key, value);
        return this;
    }
    
    @Override
    public RegexSearchProvider build() {
        String searchData = (String)params.get(SEARCH_DATA);
        
        if(searchData == null) {
            throw new SaxXmlParserException("Can`t build RegexSearchProvider");
        }
        
        return new RegexSearchProvider(searchData);
    }
}
