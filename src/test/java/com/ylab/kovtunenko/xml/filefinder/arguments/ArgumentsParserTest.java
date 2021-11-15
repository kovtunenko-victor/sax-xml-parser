package com.ylab.kovtunenko.xml.filefinder.arguments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.xml.filefinder.constants.GlobalConstants;
import com.ylab.kovtunenko.xml.filefinder.constants.GlobalConstants.MaskType;
import com.ylab.kovtunenko.xml.filefinder.exceptions.FileFinderAppException;

public class ArgumentsParserTest {
    private final ArgumentsParser parser = new ArgumentsParser();
    
    @Test
    public void parseMethodShouldReturnParsedArgumentsWithoutSearch() {
        String expectedFileName = "file.xml";
        String expectedMask = GlobalConstants.EMPTY_STRING;
        MaskType expectedMaskType = MaskType.NO_MASK;
        
        Arguments arguments = parser.parse(new String[] {"-f", expectedFileName});
        
        assertNotNull(arguments);
        assertEquals(expectedFileName, arguments.getFileName());
        assertEquals(expectedMask, arguments.getSearchMask());
        assertEquals(expectedMaskType, arguments.getSearchMaskType());
    }
    
    @Test
    public void parseMethodShouldReturnParsedArgumentsWithSearchTypeMask() {
        String expectedFileName = "file.xml";
        String expectedMask = "asd*.txt";
        MaskType expectedMaskType = MaskType.MASK;
        
        Arguments arguments = parser.parse(new String[] {"-" + GlobalConstants.FILE, expectedFileName, "-" + GlobalConstants.SIMPLE_SEARCH, expectedMask});
        
        assertNotNull(arguments);
        assertEquals(expectedFileName, arguments.getFileName());
        assertEquals(expectedMask, arguments.getSearchMask());
        assertEquals(expectedMaskType, arguments.getSearchMaskType());
    }
    
    @Test
    public void parseMethodShouldReturnParsedArgumentsWithSearchTypeRegexp() {
        String expectedFileName = "file.xml";
        String expectedMask = ".+[a-z]";
        MaskType expectedMaskType = MaskType.REGEXP;
        
        Arguments arguments = parser.parse(new String[] {"-" + GlobalConstants.FILE, expectedFileName, "-" + GlobalConstants.REGULAR_SEARCH, expectedMask});
        
        assertNotNull(arguments);
        assertEquals(expectedFileName, arguments.getFileName());
        assertEquals(expectedMask, arguments.getSearchMask());
        assertEquals(expectedMaskType, arguments.getSearchMaskType());
    }
    
    @Test
    public void parseMethodShouldRizeExceptionWhenArgsIsNull() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            parser.parse(null);
        });

        String expected = "Incoming arguments is null";

        assertEquals(expected, exception.getMessage());
    }
    
    @Test
    public void parseMethodShouldRizeExceptionWhenTranslateUnknownArgumentParam() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            parser.parse(new String[] {"-t", "123"});
        });

        String expected = "Unrecognized option: -t";

        assertEquals(expected, exception.getMessage());
    }
    
    @Test
    public void parseMethodShouldRizeExceptionWhenWhenF_ArgumentIsNotExists() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            parser.parse(new String[] {"-s", "123"});
        });

        String expected = "Missing required option: f";

        assertEquals(expected, exception.getMessage());
    }
}
