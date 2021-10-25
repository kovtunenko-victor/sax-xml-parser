package com.ylab.kovtunenko.sax.filefinder.test;

import java.io.File;
import java.net.URISyntaxException;

import com.ylab.kovtunenko.sax.filefinder.exceptions.SaxXmlParserException;
import com.ylab.kovtunenko.sax.filefinder.providers.FileReaderProvider;

public class ReaderProviderTestImpl extends FileReaderProvider {
    @Override
    public File read(String input) {
        try {
            return super.read(input);
        } catch (SaxXmlParserException ex) {
            if(ex.getMessage().equals(String.format("File [%s] not found", input))) {
                try {
                    if (this.getClass().getClassLoader().getResource(input) != null) {
                        return new File(this.getClass().getClassLoader().getResource(input).toURI());
                    } else {
                        throw new SaxXmlParserException(String.format("File [%s] not found", input));
                    } 
                } catch (URISyntaxException ex1) {
                    throw new SaxXmlParserException(String.format("Exception when read file", input));
                }
            } else {
                throw new SaxXmlParserException(String.format("Exception when read file", input));
            }
        }
    }
}
