package com.ylab.kovtunenko.xml.filefinder.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.ylab.kovtunenko.xml.filefinder.arguments.Arguments;
import com.ylab.kovtunenko.xml.filefinder.comparators.Comparator;
import com.ylab.kovtunenko.xml.filefinder.comparators.ComparatorFactory;
import com.ylab.kovtunenko.xml.filefinder.constants.GlobalConstants.MaskType;
import com.ylab.kovtunenko.xml.filefinder.exceptions.FileFinderAppException;

public class SaxParser {
    public String parse(Arguments arguments) {
        try {
            SAXParser saxParser = buildSaxParser();
            NodeParser handler = new NodeParser(getComparator(arguments.getSearchMask(), arguments.getSearchMaskType()));
            saxParser.parse(readFile(arguments.getFileName()), handler);
            
            return handler.getResult();
        } catch (IOException | SAXException | ParserConfigurationException | IllegalArgumentException ex) {
            throw new FileFinderAppException("Xml file parse exception", ex);
        }
    }
    
    private SAXParser buildSaxParser() throws SAXException, ParserConfigurationException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setValidating(false);
        saxParserFactory.setNamespaceAware(false);
        
        return saxParserFactory.newSAXParser();
    }
    
    private File readFile(String input) {
        File file = new File(input);

        if (file.exists()) {
            return file;
        } else {
            throw new FileFinderAppException(String.format("File [%s] not found", input));
        }
    }
    
    private Comparator getComparator(String mask, MaskType maskType) {
        Comparator comparator = ComparatorFactory.newInstance(maskType);
        comparator.setSearchValue(mask);    
        
        return comparator;
    }
}
