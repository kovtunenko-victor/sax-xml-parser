package com.ylab.kovtunenko.sax.filefinder.providers.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.filefinder.domain.Arguments;
import com.ylab.kovtunenko.sax.filefinder.enums.MaskType;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.ArgumentsParserProvider;
import com.ylab.kovtunenko.sax.filefinder.utils.TestUtils;

public class ArgumentsParserProviderTest {
    private final ArgumentsParserProvider argsParser = new ArgumentsParserProvider();; 
    
    @Test
    public void parseMethodShuldReturnArgumentsWithNoMask() {
        String filePath = TestUtils.getResourceFilePath("DataFile.xml");
        String[] args = {"-f", filePath };
        
        Arguments arguments = argsParser.parse(args);
        
        assertEquals(arguments.getFileName(), filePath);
        assertEquals(arguments.getSearchMaskType(), MaskType.NO_MASK);
    }
    
    @Test
    public void parseMethodShuldReturnArgumentsWithSimpleMaskType() {
        String filePath = TestUtils.getResourceFilePath("DataFile.xml");
        String mask = ".+";
        String[] args = {"-f", filePath, "-s", mask };
        
        Arguments arguments = argsParser.parse(args);
        
        assertEquals(arguments.getFileName(), filePath);
        assertEquals(arguments.getSearchMask(), mask);
        assertEquals(arguments.getSearchMaskType(), MaskType.MASK);
    }
    
    @Test
    public void parseMethodShuldReturnArgumentsWithRegexpMaskType() {
        String filePath = TestUtils.getResourceFilePath("DataFile.xml");
        String mask = ".+";
        String[] args = {"-f", filePath, "-S", mask };
        
        Arguments arguments = argsParser.parse(args);
        
        assertEquals(arguments.getFileName(), filePath);
        assertEquals(arguments.getSearchMask(), mask);
        assertEquals(arguments.getSearchMaskType(), MaskType.REGEXP);
    }
    
    @Test
    public void parseMethodShuldRizeExceptionWhenFileArgumentNotSet() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            String[] args = {"-s", "" };
            argsParser.parse(args);
        });
        
        String expectedMessage = "Error when parse properties";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void parseMethodShuldRizeExceptionWhenArgsIsNull() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            argsParser.parse(null);
        });
        
        String expectedMessage = "Incoming arguments is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
