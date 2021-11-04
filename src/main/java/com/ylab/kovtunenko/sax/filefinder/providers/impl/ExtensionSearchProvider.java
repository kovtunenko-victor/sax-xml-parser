package com.ylab.kovtunenko.sax.filefinder.providers.impl;

import com.ylab.kovtunenko.sax.filefinder.providers.SearchProvider;

public class ExtensionSearchProvider extends RegexpSearchProvider implements SearchProvider<String, String>  {
    @Override
    public boolean search(String  searchData, String searchValue) {
        boolean result = checkInput(searchData, searchValue);
        
        if(!result) {
            result = doSearch(searchData, ".+\\..*");
            return result;
        } 
        
        return result;
    }
}
