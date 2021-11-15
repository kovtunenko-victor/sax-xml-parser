package com.ylab.kovtunenko.xml.filefinder.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.xml.filefinder.arguments.Arguments;
import com.ylab.kovtunenko.xml.filefinder.constants.GlobalConstants;
import com.ylab.kovtunenko.xml.filefinder.constants.GlobalConstants.MaskType;
import com.ylab.kovtunenko.xml.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.xml.filefinder.utils.TestUtils;

public class SaxParserTest {
    private final SaxParser parser = new SaxParser();
    
    @Test
    public void parseMethodShouldReturnTestFilePath() {
        String fileName = TestUtils.getResourceFilePath("TestFile3.xml");
        Arguments args = new Arguments(fileName, GlobalConstants.EMPTY_STRING, MaskType.NO_MASK);
        String result = parser.parse(args);
        
        String expected = "/file-09938329.pdf" + System.lineSeparator();
        
        assertEquals(expected, result);
    }
    
    @Test
    public void parseMethodShouldReturnEmptyLineWhenFormatIsUnknown() {
        String fileName = TestUtils.getResourceFilePath("TestFile1.xml");
        Arguments args = new Arguments(fileName, GlobalConstants.EMPTY_STRING, MaskType.NO_MASK);
        String result = parser.parse(args);
        
        String expected = "";
        
        assertEquals(expected, result);
    }
    
    @Test
    public void parseMethodShouldRizeExeptionWhenFileNotFound() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            Arguments args = new Arguments("TestFile0.xml", GlobalConstants.EMPTY_STRING, MaskType.NO_MASK);
            parser.parse(args);
        });

        String expected = "File [TestFile0.xml] not found";

        assertEquals(expected, exception.getMessage());
    }
}
