package com.ylab.kovtunenko.sax.xml.parser;


import com.ylab.kovtunenko.sax.xml.parser.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.xml.parser.domain.Node;
import com.ylab.kovtunenko.sax.xml.parser.providers.InputPropertiesParserProvider;
import com.ylab.kovtunenko.sax.xml.parser.providers.ParserProvider;
import com.ylab.kovtunenko.sax.xml.parser.providers.XmlFileParserProvider;

public class Application {
    public static void main(String[] args) {
        ParserProvider<ApplicationProperties> propertiesParser = new InputPropertiesParserProvider(args);
        ApplicationProperties appProps = propertiesParser.parse();
        
        ParserProvider<Node> xmlParser = new XmlFileParserProvider(appProps);
        System.out.println(xmlParser.parse().toString());
    }
}
