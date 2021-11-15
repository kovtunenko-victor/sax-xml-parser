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
        
        String expected = "/file-77194797.xml" + System.lineSeparator()
                        + "/dir-88971375/dir-414754795/file-09938329.pdf" + System.lineSeparator()
                        + "/dir-88971375/dir-414754795/file-88874222.java" + System.lineSeparator()
                        + "/dir-88971375/dir-414754795/file-33399233.dbf" + System.lineSeparator()
                        + "/dir-88971375/dir-414754795/dir-67567567/file-12321321.rtf" + System.lineSeparator()
                        + "/dir-88971375/dir-414754795/file-12333233.xml" + System.lineSeparator()
                        + "/dir-88971375/file-9738721998.java" + System.lineSeparator()
                        + "/dir-88971375/file-777939833.doc" + System.lineSeparator()
                        + "/dir-88971375/dir-219753795/file-974842197.xhtml" + System.lineSeparator()
                        + "/dir-88971375/dir-219753795/file-1541224222.txt" + System.lineSeparator()
                        + "/dir-88971375/file-1236624789.cpp" + System.lineSeparator()
                        + "/file-2576624200.xlsx" + System.lineSeparator();
        
        assertEquals(expected, result);
    }
    
    @Test
    public void mainMethodShouldReturnOnlyFindedFilesByFixedName() {
        String filePath = TestUtils.getResourceFilePath("DataFile.xml");
        
        Arguments arguments = new ArgumentsParser().parse(new String[] {"-f", filePath, "-s", "file-12321321.rtf"});
        String result = new SaxParser().parse(arguments);
        
        String expected = "/dir-88971375/dir-414754795/dir-67567567/file-12321321.rtf" + System.lineSeparator();
        
        assertEquals(expected, result);
    }
    
    @Test
    public void mainMethodShouldReturnOnlyFindedFilesByMaskComparator() {
        String filePath = TestUtils.getResourceFilePath("DataFile.xml");
        
        Arguments arguments = new ArgumentsParser().parse(new String[] {"-f", filePath, "-s", "*.java"});
        String result = new SaxParser().parse(arguments);
        
        String expected = "/dir-88971375/dir-414754795/file-88874222.java" + System.lineSeparator()
                        + "/dir-88971375/file-9738721998.java" + System.lineSeparator();
        
        assertEquals(expected, result);
    }

    @Test
    public void mainMethodShouldReturnOnlyFindedFilesByRegexpComparator() {
        String filePath = TestUtils.getResourceFilePath("DataFile.xml");
        
        Arguments arguments = new ArgumentsParser().parse(new String[] {"-f", filePath, "-S", ".*?[a-z]{4}-\\d+.\\.[a-z]+"});
        String result = new SaxParser().parse(arguments);
        
        String expected = "/file-77194797.xml" + System.lineSeparator()
                        + "/dir-88971375/dir-414754795/file-09938329.pdf" + System.lineSeparator()
                        + "/dir-88971375/dir-414754795/file-88874222.java" + System.lineSeparator()
                        + "/dir-88971375/dir-414754795/file-33399233.dbf" + System.lineSeparator()
                        + "/dir-88971375/dir-414754795/dir-67567567/file-12321321.rtf" + System.lineSeparator()
                        + "/dir-88971375/dir-414754795/file-12333233.xml" + System.lineSeparator()
                        + "/dir-88971375/file-9738721998.java" + System.lineSeparator()
                        + "/dir-88971375/file-777939833.doc" + System.lineSeparator()
                        + "/dir-88971375/dir-219753795/file-974842197.xhtml" + System.lineSeparator()
                        + "/dir-88971375/dir-219753795/file-1541224222.txt" + System.lineSeparator()
                        + "/dir-88971375/file-1236624789.cpp" + System.lineSeparator()
                        + "/file-2576624200.xlsx" + System.lineSeparator();
        
        assertEquals(expected, result);
    }
}
