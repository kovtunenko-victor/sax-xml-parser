package com.ylab.kovtunenko.sax.filefinder.providers.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ylab.kovtunenko.sax.filefinder.domain.MaskType;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.ParserProvider;

public class SearchValueParser implements ParserProvider<MaskType, String> {
    
    @Override
    public MaskType parse(String value) {
        Matcher matcher;
        
        if(value == null) {
            throw new FileFinderAppException("Search value is null");
        }

        matcher = Pattern.compile("[\\*]\\..+").matcher(value);
        if (matcher.find()) {
            return MaskType.NAME;
        }
        
        matcher = Pattern.compile(".+\\.\\*").matcher(value);
        if (matcher.find()) {
            return MaskType.EXTENSION;
        }
        
        return MaskType.REGEXP;
    }
}
