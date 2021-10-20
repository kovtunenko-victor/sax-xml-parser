package com.ylab.kovtunenko.sax.xml.parser.providers;

import com.ylab.kovtunenko.sax.xml.parser.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.xml.parser.domain.Node;

public class XmlFileParserProvider implements ParserProvider<Node> {
    private final ApplicationProperties appProps;
    
    public XmlFileParserProvider(ApplicationProperties appProps) {
        this.appProps = new ApplicationProperties(appProps);
    }
    
    @Override
    public Node parse() {
        return null;
    }
}
