package com.ylab.kovtunenko.sax.filefinder.handlers;

import java.util.Deque;
import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ylab.kovtunenko.sax.filefinder.constants.GlobalConstants;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.SearchProvider;

public class DafaultHandler extends BaseHandler {
    private static final String NODE = "NODE";
    private static final String NAME = "NAME";
    private static final String CHILDREN = "CHILDREN";
    private static final String CHILD = "CHILD";

    private StringBuilder result;
    private StringBuilder elementValue;
    private boolean isFile;
    
    private final PathBuilder pathBuilder;

    public DafaultHandler(String searchMask, SearchProvider<String, String> searchProvider) {
        super(searchMask, searchProvider);
        pathBuilder = new PathBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName.toUpperCase()) {
        case NODE:
            checkIsFile(attributes);
            isFile = Boolean.parseBoolean(attributes.getValue("is-file"));
            break;
        case NAME:
            elementValue = new StringBuilder();
            break;
        case CHILD:
            checkIsFile(attributes);
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
                pathBuilder.remuveLastFolder();
            }
            break;
        case CHILDREN:
            pathBuilder.remuveLastFolder();
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

    @Override
    public String getResult() {
        if (result == null) {
            throw new FileFinderAppException("Xml file not in format");
        }

        return result.toString();
    }

    private void getFilePath() {
        if (isFile) {
            if (result == null) {
                result = new StringBuilder();
            }
            if (searchProvider.search(elementValue.toString(), searchMask)) {
                result.append(pathBuilder.getTempPath()).append(elementValue.toString()).append(GlobalConstants.NEW_LINE);
            }
        } else {
            pathBuilder.insertFolder(elementValue.toString());
        }
    }

    private void checkIsFile(Attributes attributes) {
        if (attributes.getValue("is-file") == null) {
            throw new FileFinderAppException("Is-file attribute is not set");
        }
    }
    
    private class PathBuilder {
        private final Deque<String> tempPath;
        
        public PathBuilder() {
            tempPath = new LinkedList<>();
        }
        
        private void insertFolder(String folder) {
            if (!elementValue.toString().equals(GlobalConstants.SLASH)) {
                if(elementValue.toString().equals(GlobalConstants.EMPTY_STRING)) {
                    tempPath.add(GlobalConstants.NULL_STRING);
                } else {
                    tempPath.add(folder);
                }
            }
        }

        private void remuveLastFolder() {
            if (tempPath.size() > 0) {
                tempPath.removeLast();
            }
        }

        private String getTempPath() {
            StringBuilder result = new StringBuilder();
            result.append(GlobalConstants.SLASH);

            tempPath.forEach(pathItem -> result.append(pathItem).append(GlobalConstants.SLASH));

            return result.toString();
        } 
    }
}
