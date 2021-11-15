package com.ylab.kovtunenko.xml.filefinder.arguments;

import com.ylab.kovtunenko.xml.filefinder.constants.GlobalConstants.MaskType;

public class Arguments {
    private final String fileName;
    private final String searchMask;
    private final MaskType searchMaskType;
    
    public Arguments(String fileName, String searchMask, MaskType searchMaskType) {
        this.fileName = fileName;
        this.searchMask = searchMask;
        this.searchMaskType = searchMaskType;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public String getSearchMask() {
        return searchMask;
    }
    
    public MaskType getSearchMaskType() {
        return searchMaskType;
    }
}
