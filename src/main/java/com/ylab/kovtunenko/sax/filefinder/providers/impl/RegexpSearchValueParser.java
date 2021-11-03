package com.ylab.kovtunenko.sax.filefinder.providers.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ylab.kovtunenko.sax.filefinder.providers.ParserProvider;

public class RegexpSearchValueParser implements ParserProvider<String> {
    private final String searchValue;
    
    RegexpSearchValueParser(String searchValue) {
        this.searchValue = searchValue;
    }
    
    @Override
    public String parse() {
        Matcher matcher;
        String value = new String(searchValue);
        value = value.replaceAll("'", "");

        matcher = Pattern.compile("[\\*]\\..+").matcher(value);
        if (matcher.find()) {
            value = "." + value.replaceAll("\\*", "\\*\\\\");
            return value;
        }
        
        matcher = Pattern.compile(".+\\.\\*").matcher(value);
        if (matcher.find()) {
            value = value.replaceAll("\\.", "\\\\\\.\\.");
            return value;
        }
        
        return value;
    }
}
