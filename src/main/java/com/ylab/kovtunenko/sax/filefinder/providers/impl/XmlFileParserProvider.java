package com.ylab.kovtunenko.sax.filefinder.providers.impl;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.ylab.kovtunenko.sax.filefinder.domain.Arguments;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.handlers.MySaxHandler;
import com.ylab.kovtunenko.sax.filefinder.providers.ParserProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.ReaderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.SearchProvider;

public class XmlFileParserProvider  implements ParserProvider<String, SearchProvider<String, String>> {
    private final Arguments arguments;
    private final ReaderProvider<File, String> reader;

    public XmlFileParserProvider(Arguments arguments, ReaderProvider<File, String> reader) {
        this.arguments = arguments;
        this.reader = reader;
    }

    @Override
    public String parse(SearchProvider<String, String> searchProvider) {
        if (reader == null) {
            throw new FileFinderAppException("Reader is null");
        }

        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            MySaxHandler handler = new MySaxHandler(arguments.getSearchMask() ,searchProvider);

            saxParser.parse(reader.read(arguments.getFileName()), handler);
            
            return handler.getResult();

        } catch (IOException | SAXException | ParserConfigurationException | IllegalArgumentException ex) {
            throw new FileFinderAppException("Xml file parse exception", ex);
        }
    } 
}