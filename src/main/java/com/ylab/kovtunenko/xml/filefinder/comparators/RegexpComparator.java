package com.ylab.kovtunenko.xml.filefinder.comparators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpComparator extends Comparator {
    
    @Override
    public boolean compare(String searchData) {
        boolean result = checkInput(searchData, searchValue);
        
        if(!result) {
            return doSearch(searchData, searchValue);
        } 
        
        return result;
    }
    
    protected boolean doSearch(String searchData, String searchValue) {
        Pattern pattern = Pattern.compile(searchValue);
        Matcher matcher = pattern.matcher(searchData);

        return matcher.find();
    }
}
