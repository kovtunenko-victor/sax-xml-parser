package com.ylab.kovtunenko.sax.filefinder.domain;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final String name;
    private final boolean isFile;
    private final List<Node> children;
    private final Node root;
    private final long level;
    
    private Node(String name, boolean isFile, List<Node> children, Node root, long level) {
        this.name = name;
        this.isFile = isFile;
        this.children = new ArrayList<>(children);
        this.root = root;
        this.level = level;
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
    
    public Node getRoot() {
        return root;
    }
    
    public long getLevel() {
        return level;
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
        builder.append(getTab(node.level)).append("Name: [").append(node.name).append("] Is file: [").append(node.isFile).append("]\r\n");
        
        for(Node item : node.getChildren()) {
            builder.append(printNode(item));  
        }
        
        return builder.toString();
    }
    
    private String getTab(long length) {
        if(length > 0) {
            return String.format(String.format("%%%ss", length*5), " ");
        } else {
            return "";
        }
    }

    public static class Builder {
        private String name;
        private boolean isFile;
        private List<Node> children;
        private Node root;
        private long level;
        
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
        
        public Builder setRoot(Node root) {
            this.root = root;
            return this;
        }
        
        public Builder setLevel(long level) {
            this.level = level;
            return this;
        }
        
        public Node build() {
            return new Node(name, isFile, children, root, level);
        }
    }
}
