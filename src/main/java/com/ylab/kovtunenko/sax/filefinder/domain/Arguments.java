package com.ylab.kovtunenko.sax.filefinder.domain;

import com.ylab.kovtunenko.sax.filefinder.constants.GlobalConstants;
import com.ylab.kovtunenko.sax.filefinder.enums.MaskType;

public class Arguments {
    private final String fileName;
    private final String searchMask;
    private final MaskType searchMaskType;
    
    public Arguments(String fileName) {
        this.fileName = fileName;
        this.searchMask = GlobalConstants.EMPTY_STRING;
        this.searchMaskType = MaskType.NO_MASK;
    }
    
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
