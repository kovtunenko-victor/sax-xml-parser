package com.ylab.kovtunenko.sax.xml.parser.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.xml.parser.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.xml.parser.exceptions.SaxXmlParserException;
import com.ylab.kovtunenko.sax.xml.parser.providers.InputPropertiesParserProvider;

public class InputPropertiesParserProviderTest {
    private InputPropertiesParserProvider parser;
    
    @Test
    public void parseMethtodShuldReturnApplicationPropertiesClassWithSearchMask() {
        String[] args = {"-f", "FileName.xml", "-s", "'*.txt'" };
        parser = new InputPropertiesParserProvider(args);
        ApplicationProperties props = parser.parse();
        
        assertEquals(props.getFileName(), "FileName.xml");
        assertEquals(props.getSearchMask(), ".*\\.txt");
    }
    
    @Test
    public void parseMethtodShuldReturnApplicationPropertiesClassWithoutSearchMask() {
        String[] args = {"-f", "FileName.xml" };
        parser = new InputPropertiesParserProvider(args);
        ApplicationProperties props = parser.parse();
        
        assertEquals(props.getFileName(), "FileName.xml");
        assertEquals(props.getSearchMask(), "");
    }
    
    @Test
    public void parseMethtodShuldRizeExceptionWhenArgument_F_Missing() {
        String[] args = {"-d", "FileName.xml" };
        
        Exception exception = assertThrows(SaxXmlParserException.class, () -> {
            parser = new InputPropertiesParserProvider(args);
            parser.parse();
        });
       
        String expectedMessage = "Error when parse properties";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
