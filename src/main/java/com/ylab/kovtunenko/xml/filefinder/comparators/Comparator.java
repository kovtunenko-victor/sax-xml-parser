package com.ylab.kovtunenko.xml.filefinder.comparators;

import java.util.ArrayList;
import java.util.List;

import com.ylab.kovtunenko.xml.filefinder.constants.GlobalConstants;
import com.ylab.kovtunenko.xml.filefinder.exceptions.FileFinderAppException;

public abstract class Comparator {
    protected String searchValue;
    protected boolean isFile;
    protected List<String> tempPath;
    protected final StringBuilder result = new StringBuilder();
    
    public abstract boolean compare(String  searchData);
    
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
    
    public void setIsFile(boolean isFile) {
        this.isFile = isFile;
    }
    
    public boolean getIsFile() {
        return isFile;
    }
    
    public String getResult() {
        return result.toString();
    }
    
    public void insert(String elementValue) {
        if (isFile) {
            if (compare(elementValue)) {
                saveFilePath(getTempPath() + elementValue);
            }
        } else {
            insertFolder(elementValue);
        }
    }
    
    public void remuveLastFolder() {
        if (tempPath!= null && tempPath.size() > 0) {
            tempPath.remove(tempPath.size() - 1);
        }
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
    
    private void saveFilePath(String filePath) {
        result.append(filePath).append(System.lineSeparator());
    }

    private void insertFolder(String elementValue) {
        if (tempPath == null) {
            tempPath = new ArrayList<>();
        }

        if (elementValue!= null && !elementValue.equals(GlobalConstants.SLASH)) {
            if(elementValue.equals(GlobalConstants.EMPTY_STRING)) {
                tempPath.add(GlobalConstants.NULL_STRING);
            } else {
                tempPath.add(elementValue);
            }
        }
    }

    private String getTempPath() {
        StringBuilder result = new StringBuilder();
        result.append(GlobalConstants.SLASH);
        tempPath.forEach(pathItem -> result.append(pathItem).append(GlobalConstants.SLASH));

        return result.toString();
    }
}
