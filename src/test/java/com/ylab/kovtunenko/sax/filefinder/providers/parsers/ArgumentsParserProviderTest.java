package com.ylab.kovtunenko.sax.filefinder.providers.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.filefinder.domain.Arguments;
import com.ylab.kovtunenko.sax.filefinder.enums.MaskType;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.ArgumentsParserProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.SearchValueParserProvider;
import com.ylab.kovtunenko.sax.filefinder.utils.TestUtils;

public class ArgumentsParserProviderTest {
    private SearchValueParserProvider  searchValueParser = mock(SearchValueParserProvider.class);
    private ArgumentsParserProvider argsParser;
    
    @Test
    public void parseMethodShuldReturnArgumentsWithNoMask() {
        String filePath = TestUtils.getResourceFilePath("DataFile.xml");
        String[] args = {"-f", filePath };
        argsParser = new ArgumentsParserProvider(searchValueParser);
        
        Arguments arguments = argsParser.parse(args);
        
        assertEquals(arguments.getFileName(), filePath);
        assertEquals(arguments.getSearchMaskType(), MaskType.NO_MASK);
    }
    
    @Test
    public void parseMethodShuldReturnArgumentsWithMask() {
        String filePath = TestUtils.getResourceFilePath("DataFile.xml");
        String mask = ".+";
        
        String[] args = {"-f", filePath, "-s", mask };
        when(searchValueParser.parse(mask)).thenReturn(MaskType.REGEXP);
        
        argsParser = new ArgumentsParserProvider(searchValueParser);
        
        Arguments arguments = argsParser.parse(args);
        
        assertEquals(arguments.getFileName(), filePath);
        assertEquals(arguments.getSearchMask(), mask);
        assertEquals(arguments.getSearchMaskType(), MaskType.REGEXP);
    }
    
    @Test
    public void parseMethodShuldRizeExceptionWhenFileArgumentNotSet() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            String[] args = {"-s", "" };
            argsParser = new ArgumentsParserProvider(searchValueParser);
            argsParser.parse(args);
        });
        
        String expectedMessage = "Error when parse properties";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void parseMethodShuldRizeExceptionWhenArgsIsNull() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            argsParser = new ArgumentsParserProvider(searchValueParser);
            argsParser.parse(null);
        });
        
        String expectedMessage = "Incoming arguments is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
