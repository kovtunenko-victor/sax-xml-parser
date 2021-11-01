package com.ylab.kovtunenko.sax.filefinder.providers;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ylab.kovtunenko.sax.filefinder.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.filefinder.exceptions.SaxXmlParserException;
import com.ylab.kovtunenko.sax.filefinder.providers.builders.BuilderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.builders.RegexSearchProviderBuilder;

public class XmlFileParserProvider implements ParserProvider<String> {
    private final ApplicationProperties appProps;
    private final ReaderProvider<File, String> reader;
    private final BuilderProvider<RegexSearchProvider> searchBuilder;

    public XmlFileParserProvider(ApplicationProperties appProps, 
            ReaderProvider<File, String> reader,
            BuilderProvider<RegexSearchProvider> searchBuilder) {
        this.appProps = appProps;
        this.reader = reader;
        this.searchBuilder = searchBuilder;
    }

    @Override
    public String parse() {
        if (reader == null) {
            throw new SaxXmlParserException("Reader is null");
        }

        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            MySaxHandler handler = new MySaxHandler();
            saxParser.parse(reader.read(appProps.getFileName()), handler);

            if (handler.result == null) {
                throw new SaxXmlParserException("Xml file not in format");
            }

            return handler.result.toString();

        } catch (IOException | SAXException | ParserConfigurationException | IllegalArgumentException ex) {
            throw new SaxXmlParserException("Xml file parse exception", ex);
        }
    }

    private class MySaxHandler extends DefaultHandler {
        private static final String NODE = "node";
        private static final String NAME = "name";
        private static final String CHILDREN = "children";
        private static final String CHILD = "child";

        private static final String SLASH = "/";
        private static final String SLASH_IN_NAME_RAPLACE = "~";

        private StringBuilder result;
        private StringBuilder tempPath;
        private StringBuilder elementValue;
        private boolean isFile;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            switch (qName) {
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
            switch (qName) {
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
                setFolder();
            } else {
                if (result == null) {
                    result = new StringBuilder();
                }

                if (doSearch(appProps.getSearchMask(), elementValue.toString())) {
                    result.append(tempPath).append(elementValue.toString().replaceAll(SLASH, SLASH_IN_NAME_RAPLACE))
                            .append("\r\n");
                }
            }
        }

        private boolean doSearch(String searchValue, String searchData) {
            return searchBuilder.addParam(RegexSearchProviderBuilder.SEARCH_DATA, searchData).build().search(searchValue);
        }

        private void setFolder() {
            if (tempPath == null) {
                tempPath = new StringBuilder();
            }

            if (!elementValue.toString().equals(SLASH)) {
                tempPath.append(elementValue.toString()).append(SLASH);
            } else {
                tempPath.append(elementValue.toString());
            }
        }

        private void remuveLastFolder() {
            int result = tempPath.length() - (getLastFolder(tempPath.toString()).length()) - 1;

            if (result > 0) {
                tempPath.setLength(result);
            }
        }

        private String getLastFolder(String path) {
            if (tempPath.toString().split(SLASH).length > 0) {
                return tempPath.toString().split(SLASH)[tempPath.toString().split(SLASH).length - 1];
            } else {
                return SLASH;
            }
        }
    }
}
