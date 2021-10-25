package com.ylab.kovtunenko.sax.filefinder.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.filefinder.domain.Node;
import com.ylab.kovtunenko.sax.filefinder.exceptions.SaxXmlParserException;
import com.ylab.kovtunenko.sax.filefinder.providers.NodeSearchProvider;

public class NodeSearchProviderTest {
    private NodeSearchProvider finder;
    private static Node nodeRoot;
    
    @BeforeAll
    public static void buildNode() {
        nodeRoot = Node.builder().setName("/").setLevel(0).setIsFile(false).build();
        Node nodeDirLevel1 = Node.builder().setName("dir-000001").setLevel(1).setIsFile(false).setRoot(nodeRoot).build();
        Node nodeFileLevel1 = Node.builder().setName("file-00001.txt").setLevel(1).setIsFile(true).setRoot(nodeRoot).build();
        Node nodeFileLevel2 = Node.builder().setName("file-00002.xml").setLevel(2).setIsFile(true).setRoot(nodeDirLevel1).build();
        Node nodeFileLevel2_1 = Node.builder().setName("dir-000002").setLevel(2).setIsFile(false).setRoot(nodeDirLevel1).build();
        Node nodeFileLevel2_2 = Node.builder().setName("file-00003.xsd").setLevel(2).setIsFile(true).setRoot(nodeDirLevel1).build();
        
        nodeRoot.getChildren().add(nodeDirLevel1);
        nodeRoot.getChildren().add(nodeFileLevel1);
        nodeRoot.getChildren().get(0).getChildren().add(nodeFileLevel2);
        nodeRoot.getChildren().get(0).getChildren().add(nodeFileLevel2_1);
        nodeRoot.getChildren().get(0).getChildren().add(nodeFileLevel2_2);
    }
    
    @Test
    public void searchMethtodShuldReturnFindedFilePath() {
        finder = new NodeSearchProvider(nodeRoot);
        String result = finder.search(".*\\.xml");

        assertEquals(result, "/dir-000001/file-00002.xml\r\n");
    }
    
    @Test
    public void searchMethtodShuldReturnAllFilePath() {
        finder = new NodeSearchProvider(nodeRoot);
        String result = finder.search("");
        
        assertEquals(result, "/dir-000001/file-00002.xml\r\n/dir-000001/file-00003.xsd\r\n/file-00001.txt\r\n");
    }
    
    @Test
    public void searchMethtodShuldRizeExceptionWhenSearchDataIsNull() {
        Exception exception = assertThrows(SaxXmlParserException.class, () -> {
            Node nodeRoot = Node.builder().setName("/").setLevel(0).setIsFile(false).build();
            finder = new NodeSearchProvider(nodeRoot);     
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
            finder = new NodeSearchProvider(nodeRoot);     
            finder.search(".+");
        });
        
        String expectedMessage = "Node or searchData is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
