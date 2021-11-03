package com.ylab.kovtunenko.sax.filefinder.providers.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ylab.kovtunenko.sax.filefinder.constants.GlobalConstants;
import com.ylab.kovtunenko.sax.filefinder.domain.Arguments;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.ParserProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.ReaderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.СomparisonProvider;

public class XmlFileParserProvider implements ParserProvider<String> {
    private final Arguments arguments;
    private final ReaderProvider<File, String> reader;
    private final СomparisonProvider<String, String> comparator;

    public XmlFileParserProvider(Arguments arguments, ReaderProvider<File, String> reader,
            СomparisonProvider<String, String> comparator) {
        this.arguments = arguments;
        this.reader = reader;
        this.comparator = comparator;
    }

    @Override
    public String parse() {
        if (reader == null) {
            throw new FileFinderAppException("Reader is null");
        }

        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            MySaxHandler handler = new MySaxHandler();
            saxParser.parse(reader.read(arguments.getFileName()), handler);

            if (handler.result == null) {
                throw new FileFinderAppException("Xml file not in format");
            }

            return handler.result.toString();

        } catch (IOException | SAXException | ParserConfigurationException | IllegalArgumentException ex) {
            throw new FileFinderAppException("Xml file parse exception", ex);
        }
    }

    private class MySaxHandler extends DefaultHandler {
        private static final String NODE = "NODE";
        private static final String NAME = "NAME";
        private static final String CHILDREN = "CHILDREN";
        private static final String CHILD = "CHILD";

        private StringBuilder result;
        private List<String> tempPath;
        private StringBuilder elementValue;
        private boolean isFile;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            switch (qName.toUpperCase()) {
            case NODE:
                isFile = Boolean.parseBoolean(attributes.getValue("is-file"));
                break;
            case NAME:
                elementValue = new StringBuilder();
                break;
            case CHILD:
                isFile = Boolean.parseBoolean(attributes.getValue("is-file"));
                break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            switch (qName.toUpperCase()) {
            case NODE:
                if (result == null) {
                    result = new StringBuilder();
                }
                break;
            case CHILD:
                if (!isFile) {
                    remuveLastFolder();
                }
                break;
            case CHILDREN:
                remuveLastFolder();
                isFile = true;
                break;
            case NAME:
                getFilePath();
                break;
            }
        }

        @Override
        public void characters(char ch[], int start, int length) throws SAXException {
            if (elementValue == null) {
                elementValue = new StringBuilder();
            } else {
                elementValue.append(ch, start, length);
            }
        }

        private void getFilePath() {
            if (!isFile) {
                insertFolder();
            } else {
                if (result == null) {
                    result = new StringBuilder();
                }

                if (comparator.compare(elementValue.toString(), arguments.getSearchMask())) {
                    result.append(getTempPath()).append(elementValue.toString()).append("\r\n");
                }
            }
        }

        private void insertFolder() {
            if (tempPath == null) {
                tempPath = new ArrayList<>();
            }

            if (!elementValue.toString().equals(GlobalConstants.SLASH)) {
                tempPath.add(elementValue.toString());

            }
        }

        private void remuveLastFolder() {
            tempPath.remove(tempPath.size()-1);
        }
        
        private String getTempPath() {
            StringBuilder result = new StringBuilder();
            result.append(GlobalConstants.SLASH);
            
            for(String item : tempPath) {
                result.append(item);
            }
            
            return result.toString();
        }
    }
}
