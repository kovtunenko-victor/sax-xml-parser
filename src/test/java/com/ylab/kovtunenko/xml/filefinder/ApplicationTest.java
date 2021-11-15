package com.ylab.kovtunenko.xml.filefinder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.xml.filefinder.arguments.Arguments;
import com.ylab.kovtunenko.xml.filefinder.arguments.ArgumentsParser;
import com.ylab.kovtunenko.xml.filefinder.utils.TestUtils;
import com.ylab.kovtunenko.xml.filefinder.xml.SaxParser;

public class ApplicationTest {
    
    @Test
    public void mainMethodShouldReturnAllFiles() {
        String filePath = TestUtils.getResourceFilePath("DataFile.xml");
        
        Arguments arguments = new ArgumentsParser().parse(new String[] {"-f", filePath});
        String result = new SaxParser().parse(arguments);
        
        String expected = "/file-776194140.xml" + System.lineSeparator()
                        + "/dir-880176375/file-1073842118.java" + System.lineSeparator()
                        + "/dir-880176375/dir-2145307015/file-1498940214.xhtml" + System.lineSeparator();
        
        assertEquals(expected, result);
    }
    
    @Test
    public void mainMethodShouldReturnOnlyFindedFilesByFixedName() {
        String filePath = TestUtils.getResourceFilePath("DataFile.xml");
        
        Arguments arguments = new ArgumentsParser().parse(new String[] {"-f", filePath, "-s", "file-1498940214.xhtml"});
        String result = new SaxParser().parse(arguments);
        
        String expected = "/dir-880176375/dir-2145307015/file-1498940214.xhtml" + System.lineSeparator();
        
        assertEquals(expected, result);
    }
    
    @Test
    public void mainMethodShouldReturnOnlyFindedFilesByMaskComparator() {
        String filePath = TestUtils.getResourceFilePath("DataFile.xml");
        
        Arguments arguments = new ArgumentsParser().parse(new String[] {"-f", filePath, "-s", "*.java"});
        String result = new SaxParser().parse(arguments);
        
        String expected = "/dir-880176375/file-1073842118.java" + System.lineSeparator();
        
        assertEquals(expected, result);
    }

    @Test
    public void mainMethodShouldReturnOnlyFindedFilesByRegexpComparator() {
        String filePath = TestUtils.getResourceFilePath("DataFile.xml");
        
        Arguments arguments = new ArgumentsParser().parse(new String[] {"-f", filePath, "-S", ".*?[a-z]{4}-\\d+.\\.[a-z]+"});
        String result = new SaxParser().parse(arguments);
        
        String expected = "/file-776194140.xml" + System.lineSeparator()
                        + "/dir-880176375/file-1073842118.java" + System.lineSeparator()
                        + "/dir-880176375/dir-2145307015/file-1498940214.xhtml" + System.lineSeparator();
        
        assertEquals(expected, result);
    }
}
