package com.ylab.kovtunenko.sax.filefinder.providers;

import java.io.File;

import com.ylab.kovtunenko.sax.filefinder.exceptions.SaxXmlParserException;

public class FileReaderProvider implements ReaderProvider<File, String> {
    @Override
    public File read(String input) {
        File file = new File(input);

        if (file.exists()) {
            return file;
        } else {
            throw new SaxXmlParserException(String.format("File [%s] not found", input));
        }
    }
}
