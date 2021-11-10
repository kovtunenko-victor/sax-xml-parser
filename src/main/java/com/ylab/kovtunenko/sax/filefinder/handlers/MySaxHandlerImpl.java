package com.ylab.kovtunenko.sax.filefinder.handlers;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.ylab.kovtunenko.sax.filefinder.constants.GlobalConstants;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.SearchProvider;

public class MySaxHandlerImpl extends MySaxHandler {
    private static final String NODE = "NODE";
    private static final String NAME = "NAME";
    private static final String CHILDREN = "CHILDREN";
    private static final String CHILD = "CHILD";

    private StringBuilder result;
    private List<String> tempPath;
    private StringBuilder elementValue;
    private boolean isFile;

    public MySaxHandlerImpl(String searchMask, SearchProvider<String, String> searchProvider) {
        super(searchMask, searchProvider);
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
                result.append(getTempPath()).append(elementValue.toString()).append(GlobalConstants.NEW_LINE);
            }
        } else {
            insertFolder();
        }
    }

    private void insertFolder() {
        if (tempPath == null) {
            tempPath = new ArrayList<>();
        }

        if (!elementValue.toString().equals(GlobalConstants.SLASH)) {
            if(elementValue.toString().equals(GlobalConstants.EMPTY_STRING)) {
                tempPath.add(GlobalConstants.NULL_STRING);
            } else {
                tempPath.add(elementValue.toString());
            }
        }
    }

    private void remuveLastFolder() {
        if (tempPath.size() > 0) {
            tempPath.remove(tempPath.size() - 1);
        }
    }

    private String getTempPath() {
        StringBuilder result = new StringBuilder();
        result.append(GlobalConstants.SLASH);

        tempPath.forEach(pathItem -> result.append(pathItem).append(GlobalConstants.SLASH));

        return result.toString();
    }
    
    private void checkIsFile(Attributes attributes) {
        if (attributes.getValue("is-file") == null) {
            throw new FileFinderAppException("Is-file attribute is not set");
        }
    }
}
