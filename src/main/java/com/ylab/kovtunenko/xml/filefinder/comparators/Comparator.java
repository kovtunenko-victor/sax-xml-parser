package com.ylab.kovtunenko.xml.filefinder.comparators;

public abstract class Comparator extends FileStore {
    protected String searchValue;
    
    public abstract boolean compare(String  searchData);
    
    @Override
    public void insert(String elementValue) {
       if (isFile) {
           if (compare(elementValue)) {
               saveFilePath(getTempPath() + elementValue);
           }
       } else {
           insertFolder(elementValue);
       }
    }
    
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
}
