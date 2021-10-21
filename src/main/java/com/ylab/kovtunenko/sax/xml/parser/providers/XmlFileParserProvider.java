package com.ylab.kovtunenko.sax.xml.parser.providers;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ylab.kovtunenko.sax.xml.parser.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.xml.parser.domain.Node;
import com.ylab.kovtunenko.sax.xml.parser.exceptions.SaxXmlParserException;

public class XmlFileParserProvider implements ParserProvider<Node> {
    private final ApplicationProperties appProps;
    private final ReaderProvider<File, String> reader;
    
    public XmlFileParserProvider(ApplicationProperties appProps, ReaderProvider<File, String> reader) {
        this.appProps = new ApplicationProperties(appProps);
        this.reader = reader;
    }

    @Override
    public Node parse() {
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            MySaxHandler handler = new MySaxHandler();
            saxParser.parse(reader.read(appProps.getFileName()), handler);
            
            return handler.rootNode;

        } catch (Exception ex) {
            throw new SaxXmlParserException("Xml file parse exception", ex);
        }
    }

    private class MySaxHandler extends DefaultHandler {
        private static final String NODE = "node";
        private static final String NAME = "name";
        private static final String CHILDREN = "children";
        private static final String CHILD = "child";

        private StringBuilder elementValue;
        private Node.Builder nodeBuilder;

        private Node rootNode;
        private Node indexNode;
        private Node lastNode;
        
        private long level = 0;

        @Override
        public void startDocument() throws SAXException {
            nodeBuilder = Node.builder();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            switch (qName) {
            case NODE:
                nodeBuilder.setIsFile(Boolean.parseBoolean(attributes.getValue("is-file")));
                break;
            case NAME:
                elementValue = new StringBuilder();
                break;
            case CHILDREN:
                buildNode();
                level++;
                if(lastNode != null) {
                    indexNode = lastNode;
                }
                
                break;
            case CHILD:
                nodeBuilder.setIsFile(Boolean.parseBoolean(attributes.getValue("is-file")));
                break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            switch (qName) {
            case NODE:
                if (rootNode == null) {
                    buildNode();
                }
                break;
            case CHILD:
                buildNode();
                break;
            case CHILDREN:
                level--;
                indexNode = indexNode.getRoot();
                break;
            case NAME:
                nodeBuilder.setName(elementValue.toString());
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

        private void buildNode() {
            if (indexNode == null) {
                nodeBuilder.setLevel(0);
                rootNode = nodeBuilder.build();
                indexNode = rootNode;
                nodeBuilder = Node.builder();
            } else {
                 if (nodeBuilder.isInit()) {
                    nodeBuilder.setLevel(level);
                    nodeBuilder.setRoot(indexNode);
                    lastNode = nodeBuilder.build();
                    indexNode.getChildren().add(lastNode);
                    nodeBuilder = Node.builder();
                }
            }
        }
    }
}
