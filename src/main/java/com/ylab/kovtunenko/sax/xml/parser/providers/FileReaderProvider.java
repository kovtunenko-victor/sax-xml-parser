package com.ylab.kovtunenko.sax.xml.parser.providers;

import java.io.File;
import java.net.URISyntaxException;

import com.ylab.kovtunenko.sax.xml.parser.exceptions.SaxXmlParserException;

public class FileReaderProvider implements ReaderProvider<File, String> {
    @Override
    public File read(String input) {
        try {
            File file = new File(input);

            if (file.exists() == false && this.getClass().getClassLoader().getResource(input) != null) {
                file = new File(this.getClass().getClassLoader().getResource(input).toURI());
            } else {
                throw new SaxXmlParserException(String.format("File [%s] not found", input));
            }

            return file;
        } catch (URISyntaxException ex) {
            throw new SaxXmlParserException(String.format("Can`t open file [%s]", input), ex);
        }
    }
}
