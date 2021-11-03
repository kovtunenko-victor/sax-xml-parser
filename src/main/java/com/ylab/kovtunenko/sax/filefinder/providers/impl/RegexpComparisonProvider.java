package com.ylab.kovtunenko.sax.filefinder.providers.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.СomparisonProvider;

public class RegexpComparisonProvider implements СomparisonProvider<String, String> {
    @Override
    public boolean compare(String  searchData, String searchValue) {
        if (searchValue == null) {
            return true;
        }
        
        if (searchData == null) {
            throw new FileFinderAppException("Data for search is null");
        }
        
        Pattern pattern = Pattern.compile(searchValue);
        Matcher matcher = pattern.matcher(searchData);

        return matcher.find();
    }
}
