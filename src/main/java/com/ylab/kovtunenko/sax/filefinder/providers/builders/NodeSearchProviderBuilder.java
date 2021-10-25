package com.ylab.kovtunenko.sax.filefinder.providers.builders;

import java.util.HashMap;
import java.util.Map;

import com.ylab.kovtunenko.sax.filefinder.domain.Node;
import com.ylab.kovtunenko.sax.filefinder.exceptions.SaxXmlParserException;
import com.ylab.kovtunenko.sax.filefinder.providers.NodeSearchProvider;

public class NodeSearchProviderBuilder implements BuilderProvider<NodeSearchProvider> {
    private final Map<String, Object> params = new HashMap<>();
    public static final String NODE = "Node";

    @Override
    public BuilderProvider<NodeSearchProvider> addParam(String key, Object value) {
        params.put(key, value);
        return this;
    }

    @Override
    public NodeSearchProvider build() {
        Node node = (Node) params.get("Node");

        if (node == null) {
            throw new SaxXmlParserException("Can`t build XmlFileSearchProvider");
        }

        return new NodeSearchProvider(node);
    }
}
