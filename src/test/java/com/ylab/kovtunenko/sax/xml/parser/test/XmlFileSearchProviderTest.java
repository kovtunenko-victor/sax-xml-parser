package com.ylab.kovtunenko.sax.xml.parser.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.xml.parser.domain.Node;
import com.ylab.kovtunenko.sax.xml.parser.exceptions.SaxXmlParserException;
import com.ylab.kovtunenko.sax.xml.parser.providers.XmlFileSearchProvider;

public class XmlFileSearchProviderTest {
    private XmlFileSearchProvider finder;
    
    
    @Test
    public void searchMethtodShuldReturnFindedFilePath() {
        Node nodeRoot = Node.builder().setName("/").setLevel(0).setIsFile(false).build();
        Node nodeDirLevel1 = Node.builder().setName("dir-000001").setLevel(1).setIsFile(false).setRoot(nodeRoot).build();
        Node nodeFileLevel1 = Node.builder().setName("file-00001.txt").setLevel(1).setIsFile(true).setRoot(nodeRoot).build();
        Node nodeFileLevel2 = Node.builder().setName("file-00002.xml").setLevel(2).setIsFile(true).setRoot(nodeDirLevel1).build();
        
        nodeRoot.getChildren().add(nodeDirLevel1);
        nodeRoot.getChildren().add(nodeFileLevel1);
        nodeRoot.getChildren().get(0).getChildren().add(nodeFileLevel2);
        
        finder = new XmlFileSearchProvider(nodeRoot);
        
        String result = finder.search(".*\\.xml");
        
        assertEquals(result, "/dir-000001/file-00002.xml\r\n");
    }
    
    @Test
    public void searchMethtodShuldRizeExceptionWhenSearchDataIsNull() {
        Exception exception = assertThrows(SaxXmlParserException.class, () -> {
            Node nodeRoot = Node.builder().setName("/").setLevel(0).setIsFile(false).build();
            finder = new XmlFileSearchProvider(nodeRoot);     
            finder.search(null);
        });
        
        String expectedMessage = "Node or searchData is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void searchMethtodShuldRizeExceptionWhenNodeIsNull() {
        Exception exception = assertThrows(SaxXmlParserException.class, () -> {
            Node nodeRoot = null;
            finder = new XmlFileSearchProvider(nodeRoot);     
            finder.search(".+");
        });
        
        String expectedMessage = "Node or searchData is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
