package com.ylab.kovtunenko.sax.filefinder.providers.impl;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.ylab.kovtunenko.sax.filefinder.domain.Arguments;
import com.ylab.kovtunenko.sax.filefinder.domain.HandlerType;
import com.ylab.kovtunenko.sax.filefinder.domain.MaskType;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.handlers.MySaxHandler;
import com.ylab.kovtunenko.sax.filefinder.handlers.MySaxHandlerFactory;
import com.ylab.kovtunenko.sax.filefinder.providers.ParserProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.ReaderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.SearchProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.SearchProviderFactory;

public class XmlFileParserProvider  implements ParserProvider<String, Arguments> {
    private final HandlerType handlerType;
    private final ReaderProvider<File, String> reader;

    public XmlFileParserProvider(HandlerType handlerType, ReaderProvider<File, String> reader) {
        this.handlerType = handlerType;
        this.reader = reader;
    }

    @Override
    public String parse(Arguments arguments) {
        if (reader == null) {
            throw new FileFinderAppException("Reader is null");
        }

        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            MySaxHandler handler = MySaxHandlerFactory.create(handlerType, arguments.getSearchMask(), getSearchProvider(arguments.getSearchMaskType()));

            saxParser.parse(reader.read(arguments.getFileName()), handler);
            
            return handler.getResult();

        } catch (IOException | SAXException | ParserConfigurationException | IllegalArgumentException ex) {
            throw new FileFinderAppException("Xml file parse exception", ex);
        }
    }
    
    public SearchProvider<String, String> getSearchProvider(MaskType maskType) {
        return SearchProviderFactory.create(maskType);
    }
}
