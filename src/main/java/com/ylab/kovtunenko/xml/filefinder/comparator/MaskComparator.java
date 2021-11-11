package com.ylab.kovtunenko.xml.filefinder.comparator;

public class MaskComparator extends RegexpComparator {
    
    @Override
    public boolean compare(String searchData) {
        boolean result = checkInput(searchData, searchValue);
        
        if(!result) {
            return doSearch(searchData, prepareSearchValue(searchValue));
        }
        
        return result;
    }
    
    protected String prepareSearchValue(String searchValue) {
        StringBuilder newSearchValue = new StringBuilder();
        char[] chars = searchValue.toCharArray();
        
        for (char ch: chars) {
            switch (ch)
            {
                case '.'    : newSearchValue.append("\\."); break;
                case '*'    : newSearchValue.append(".*"); break;
                default     : newSearchValue.append(ch); break;
            }
        }
        return newSearchValue.toString();
    }
}
