package com.ylab.kovtunenko.sax.filefinder.providers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ylab.kovtunenko.sax.filefinder.exceptions.SaxXmlParserException;

public class RegexSearchProvider implements SearchProvider<Boolean, String> {
    private final String  searchData;
    
    public RegexSearchProvider(String  searchData) {
        this.searchData = searchData;  
    }
    
    @Override
    public Boolean search(String searchValue) {
        if (searchValue == null) {
            return true;
        }
        
        if (searchData == null) {
            throw new SaxXmlParserException("Data for search is null");
        }
        
        Pattern pattern = Pattern.compile(searchValue);
        Matcher matcher = pattern.matcher(searchData);

        return matcher.find();
    }
}
