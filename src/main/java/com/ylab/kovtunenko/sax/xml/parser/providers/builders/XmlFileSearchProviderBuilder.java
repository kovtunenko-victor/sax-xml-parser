package com.ylab.kovtunenko.sax.xml.parser.providers.builders;

import java.util.HashMap;
import java.util.Map;

import com.ylab.kovtunenko.sax.xml.parser.domain.Node;
import com.ylab.kovtunenko.sax.xml.parser.exceptions.SaxXmlParserException;
import com.ylab.kovtunenko.sax.xml.parser.providers.XmlFileSearchProvider;

public class XmlFileSearchProviderBuilder implements ProviderBuilder<XmlFileSearchProvider> {
    private final Map<String, Object> params = new HashMap<>();
    public static final String NODE = "Node";

    @Override
    public ProviderBuilder<XmlFileSearchProvider> addParam(String key, Object value) {
        params.put(key, value);
        return this;
    }

    @Override
    public XmlFileSearchProvider build() {
        Node node = (Node) params.get("Node");

        if (node == null) {
            throw new SaxXmlParserException("Can`t build XmlFileSearchProvider");
        }

        return new XmlFileSearchProvider(node);
    }
}
