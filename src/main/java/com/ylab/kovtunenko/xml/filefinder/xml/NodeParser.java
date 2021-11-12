package com.ylab.kovtunenko.xml.filefinder.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ylab.kovtunenko.xml.filefinder.comparator.Comparator;
import com.ylab.kovtunenko.xml.filefinder.constants.GlobalConstants;


public class NodeParser extends DefaultHandler {
    private final Comparator comparator;
    private String elementValue;

    public NodeParser(Comparator comparator) {
        this.comparator = comparator;
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (attributes.getValue(GlobalConstants.IS_FILE) != null) {
            comparator.setIsFile(Boolean.parseBoolean(attributes.getValue(GlobalConstants.IS_FILE)));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName.toUpperCase()) {
        
        case GlobalConstants.CHILD:
            if(!comparator.getIsFile()) {
                comparator.remuveLastFolder();
            }
            break;
        case GlobalConstants.CHILDREN:
            comparator.remuveLastFolder();
            comparator.setIsFile(true);
            break;
        case GlobalConstants.NAME:
            comparator.insert(elementValue.toString());
            break;
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        elementValue = new String(ch, start, length);
    }
}
