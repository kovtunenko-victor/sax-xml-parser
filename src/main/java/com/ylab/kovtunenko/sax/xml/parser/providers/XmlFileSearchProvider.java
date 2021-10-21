package com.ylab.kovtunenko.sax.xml.parser.providers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ylab.kovtunenko.sax.xml.parser.domain.Node;

public class XmlFileSearchProvider implements SearchProvider<String, String> {
    private final StringBuilder result;
    private final Node node;
    private StringBuilder tempPath;
    
    public XmlFileSearchProvider(Node node) {
        result = new StringBuilder();
        this.node = node;
    }
    
    @Override
    public String search(String data) {
        getFilePath(node, data);
        return result.toString();
    }
    
    private void getFilePath(Node node, String searchData) {
        if(node.getIsFile() == false) {
            for(Node item : node.getChildren()) {
                getFilePath(item, searchData);
            }
        } else { 
            tempPath = new StringBuilder();
            getRootPath(node);
            
            if(doSearch(searchData, node.getName())) {
                tempPath.append(node.getName());
                result.append(tempPath).append("\r\n");
            }
        }
    }
    
    private boolean doSearch(String searchData, String searchValue) {
        Pattern pattern = Pattern.compile(searchData);
        Matcher matcher = pattern.matcher(searchValue);
        
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    private void getRootPath(Node node) {
        if(node.getRoot() != null) {
            if(!node.getRoot().getName().equals("/")) {
                tempPath.insert(0, node.getRoot().getName() + "/" );
            } else {
                tempPath.insert(0, node.getRoot().getName() );
            }
            getRootPath(node.getRoot());
        }
    }
}
