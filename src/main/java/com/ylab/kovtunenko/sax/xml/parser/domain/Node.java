package com.ylab.kovtunenko.sax.xml.parser.domain;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final String name;
    private final boolean isFile;
    private final List<Node> children;
    
    private Node(String name, boolean isFile, List<Node> children) {
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
    
    public static Builder builder() {
        return new Builder();
    }
    
    @Override
    public String toString() {
        return printNode(this);
    }
    
    private String printNode(Node node) {
        StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(node.name).append(" Is file: ").append(node.isFile).append("\r\n");
        
        for(Node item : node.children) {
            builder.append(" ").append(printNode(item));
        }
        
        return builder.toString();
    }
    
    public static class Builder {
        private String name;
        private boolean isFile;
        private List<Node> children;
        
        private Builder() {
            children = new ArrayList<>();
        }
        
        public boolean isInit() {
            return name != null;
        }
        
        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        
        public Builder setIsFile(boolean isFile) {
            this.isFile = isFile;
            return this;
        }
        
        public Builder setChild(Node child) {
            this.children.add(child);
            return this;
        }
        
        public Builder setChildren(List<Node> children) {
            this.children = new ArrayList<>(children);
            return this;
        }
        
        public Node build() {
            return new Node(name, isFile, children);
        }
        
        
    }
}
