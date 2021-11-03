package com.ylab.kovtunenko.sax.filefinder.providers.impl;

import java.io.File;

import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.ReaderProvider;

public class FileReader implements ReaderProvider<File, String> {
    
    @Override
    public File read(String input) {
        File file = new File(input);

        if (file.exists()) {
            return file;
        } else {
            throw new FileFinderAppException(String.format("File [%s] not found", input));
        }
    }
}
