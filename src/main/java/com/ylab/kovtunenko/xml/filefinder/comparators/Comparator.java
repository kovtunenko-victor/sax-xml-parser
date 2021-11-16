package com.ylab.kovtunenko.xml.filefinder.comparators;

public abstract class Comparator extends FileStore {
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
}
