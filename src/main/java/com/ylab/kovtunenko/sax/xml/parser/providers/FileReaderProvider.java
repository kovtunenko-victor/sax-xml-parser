package com.ylab.kovtunenko.sax.xml.parser.providers;

import java.io.File;

import com.ylab.kovtunenko.sax.xml.parser.exceptions.SaxXmlParserException;

public class FileReaderProvider implements ReaderProvider<File, String> {
    @Override
    public File read(String input) {
        File file = new File(input);

        if (file.exists() == true) {
            return file;
        } else {
            throw new SaxXmlParserException(String.format("File [%s] not found", input));
        }
    }
}
