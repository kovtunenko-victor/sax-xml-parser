package com.ylab.kovtunenko.sax.xml.parser.domain;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final String name;
    private final boolean isFile;
    private final List<Node> children;
    
    public Node(String name, boolean isFile, List<Node> children) {
        this.name = name;
        this.isFile = isFile;
        this.children = new ArrayList<>(children);
    }
    
    public String getName() {
        return name;
    }
    
    public boolean getIsFile() {
        return isFile;
    }
    
    public List<Node> getChildren() {
        return children;
    }
}
