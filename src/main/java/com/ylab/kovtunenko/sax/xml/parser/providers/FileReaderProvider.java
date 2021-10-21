package com.ylab.kovtunenko.sax.xml.parser.providers;

import java.io.File;
import java.net.URISyntaxException;

import com.ylab.kovtunenko.sax.xml.parser.Application;
import com.ylab.kovtunenko.sax.xml.parser.exceptions.SaxXmlParserException;

public class FileReaderProvider implements ReaderProvider<File, String> {
    @Override
    public File read(String input) {
        try {
            File file = new File(input);

            if (file.exists() == false) {
                file = new File(Application.class.getClassLoader().getResource(input).toURI());
            }

            return file;
        } catch (URISyntaxException ex) {
            throw new SaxXmlParserException(String.format("Can`t open file [%s]", input), ex);
        }
    }
}
