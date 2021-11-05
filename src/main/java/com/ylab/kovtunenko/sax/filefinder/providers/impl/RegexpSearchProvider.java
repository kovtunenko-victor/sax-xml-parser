package com.ylab.kovtunenko.sax.filefinder.providers.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.SearchProvider;

public class RegexpSearchProvider implements SearchProvider<String, String> {
    @Override
    public boolean search(String  searchData, String searchValue) {
        boolean result = checkInput(searchData, searchValue);
        
        if(!result) {
            result = doSearch(searchData, searchValue.replaceAll("'", ""));
            return result;
        } 
        
        return result;
    }
    
    protected boolean checkInput(String  searchData, String searchValue) {
        if (searchValue == null) {
            return true;
        }
        
        if (searchData == null) {
            throw new FileFinderAppException("Data for search is null");
        }
        
        return false;
    }
    
    protected boolean doSearch(String  searchData, String searchValue) {
        Pattern pattern = Pattern.compile(searchValue);
        Matcher matcher = pattern.matcher(searchData);

        return matcher.find();
    }
}
