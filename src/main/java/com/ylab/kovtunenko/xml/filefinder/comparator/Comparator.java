package com.ylab.kovtunenko.xml.filefinder.comparator;

import com.ylab.kovtunenko.xml.filefinder.exceptions.FileFinderAppException;

public abstract class Comparator {
    protected String searchValue;
    
    public abstract boolean compare(String  searchData);
    
    public void setMask(String searchValue) {
        this.searchValue = searchValue;
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
}
