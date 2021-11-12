package com.ylab.kovtunenko.xml.filefinder.constants;

public interface GlobalConstants {
    public static final String SLASH = "/";
    public static final String EMPTY_STRING = "";
    public static final String NULL_STRING = "null";
    
    public static final String NODE = "NODE";
    public static final String NAME = "NAME";
    public static final String CHILDREN = "CHILDREN";
    public static final String CHILD = "CHILD";
    
    public static final String IS_FILE = "is-file";
    
    public enum MaskType {
        NO_MASK, REGEXP, MASK;
    }
}
