package com.ylab.kovtunenko.sax.filefinder.providers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ylab.kovtunenko.sax.filefinder.domain.Node;
import com.ylab.kovtunenko.sax.filefinder.exceptions.SaxXmlParserException;

public class NodeSearchProvider implements SearchProvider<String, String> {
    private final StringBuilder result;
    private final Node node;
    private final StringBuilder tempPath;
    
    public NodeSearchProvider(Node node) {
        result = new StringBuilder();
        tempPath = new StringBuilder();
        this.node = node;
    }
    
    @Override
    public String search(String data) {
        getFilePath(node, data);
        return result.toString();
    }
    
    private void getFilePath(Node node, String searchData) {
        if(node == null || searchData == null) {
            throw new SaxXmlParserException("Node or searchData is null");
        }
        
        if(node.getIsFile() == false) {
            setFolder(node.getName());
            
            for(Node item : node.getChildren()) {
                getFilePath(item, searchData);
            }
            
            tempPath.setLength(calcTempPathLength(node.getName()));
        } else { 
            if(doSearch(searchData, node.getName())) {
                result.append(tempPath).append(node.getName()).append("\r\n");
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
 
    private void setFolder(String name) {
        if(!name.equals("/")) {
            tempPath.append(name).append("/");
        } else {
            tempPath.append(name);
        }
    }
    
    private int calcTempPathLength(String name) {
        int result = tempPath.length()-name.length()-1;
        
        if(result > 0 ) {
            return result;
        } else {
            return tempPath.length();
        }
    }
}
