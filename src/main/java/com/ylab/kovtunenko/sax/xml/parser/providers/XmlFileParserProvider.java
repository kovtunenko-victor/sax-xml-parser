package com.ylab.kovtunenko.sax.xml.parser.providers;

import java.io.File;
import java.net.URISyntaxException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ylab.kovtunenko.sax.xml.parser.Application;
import com.ylab.kovtunenko.sax.xml.parser.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.xml.parser.domain.Node;
import com.ylab.kovtunenko.sax.xml.parser.exceptions.SaxXmlParserException;

public class XmlFileParserProvider implements ParserProvider<Node> {
    private final ApplicationProperties appProps;

    public XmlFileParserProvider(ApplicationProperties appProps) {
        this.appProps = new ApplicationProperties(appProps);
    }

    @Override
    public Node parse() {
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            saxParser.parse(readFile(), new MySaxHandler());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private File readFile() {
        try {
            File file = new File(appProps.getFileName());

            if (file.exists() == false) {
                file = new File(Application.class.getClassLoader().getResource(appProps.getFileName()).toURI());
            }

            return file;
        } catch (URISyntaxException ex) {
            throw new SaxXmlParserException(String.format("Can`t open file [%s]", appProps.getFileName()), ex);
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

        @Override
        public void startDocument() throws SAXException {
            nodeBuilder = Node.builder();
        }

        @Override
        public void endDocument() throws SAXException {
            System.out.print(rootNode.toString());
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            switch (qName) {
            case NODE:
                System.out.print("Start: " + qName + " ");
                System.out.println(attributes.getValue("is-file"));
                nodeBuilder.setIsFile(Boolean.parseBoolean(attributes.getValue("is-file")));
                break;
            case NAME:
                System.out.print("Start: " + qName + " ");
                elementValue = new StringBuilder();
                break;
            case CHILDREN:
                System.out.println("Start: " + qName + " ");
                buildNode(true);
                break;
            case CHILD:
                System.out.print("Start: " + qName + " ");
                System.out.println(attributes.getValue("is-file"));
                nodeBuilder.setIsFile(Boolean.parseBoolean(attributes.getValue("is-file")));
                break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            switch (qName) {
            case NODE:
                if (rootNode == null) {
                    buildNode(false);
                }
                break;
            case CHILD:
                buildNode(false);
                break;
            case NAME:
                System.out.print(elementValue.toString());
                System.out.println(" End: " + qName);
                nodeBuilder.setName(elementValue.toString());
                break;
            default:
                System.out.println("End: " + qName);
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

        private void buildNode(boolean switchIndex) {
            if (indexNode == null) {
                rootNode = nodeBuilder.build();
                indexNode = rootNode;
                nodeBuilder = Node.builder();
            } else {
                if (nodeBuilder.isInit()) {
                    lastNode = nodeBuilder.build();
                    indexNode.getChildren().add(lastNode);
                    nodeBuilder = Node.builder();

                    if (switchIndex == true) {
                        indexNode = lastNode;
                    }
                }
            }
        }
    }
}
