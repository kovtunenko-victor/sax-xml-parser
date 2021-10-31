package com.ylab.kovtunenko.sax.filefinder.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.filefinder.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.filefinder.exceptions.SaxXmlParserException;
import com.ylab.kovtunenko.sax.filefinder.providers.FileReaderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.InputPropertiesParserProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.ParserProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.ReaderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.XmlFileFinderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.XmlFileParserProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.builders.BuilderProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.builders.XmlFileParserProviderBuilder;

public class XmlFileFinderProviderTest {
    private XmlFileFinderProvider finder;
    private ParserProvider<ApplicationProperties> propertiesParser = mock(InputPropertiesParserProvider.class);
    private BuilderProvider<XmlFileParserProvider> parserBuilder = mock(XmlFileParserProviderBuilder.class);
    private ReaderProvider<File, String> reader = mock(FileReaderProvider.class);
    private XmlFileParserProvider parser = mock(XmlFileParserProvider.class);
    private ApplicationProperties appProps;

    @Test
    public void searchMethodShuldReturnResultFilesData() {
        appProps = new ApplicationProperties("TestFile.xml");

        when(propertiesParser.parse()).thenReturn(appProps);
        when(parser.parse()).thenReturn("/file-0000001.ext\r\n/file-0000002.ext\r\n");
        when(parserBuilder.addParam("ApplicationProperties", appProps)).thenReturn(parserBuilder);
        when(parserBuilder.addParam("ReaderProvider", reader)).thenReturn(parserBuilder);
        when(parserBuilder.build()).thenReturn(parser);

        finder = new XmlFileFinderProvider(propertiesParser, parserBuilder, reader);
        String result = finder.search();

        assertEquals(result, "/file-0000001.ext\r\n/file-0000002.ext\r\n");
    }

    @Test
    public void searchMethodShuldRizeExceptionWhenPropertiesParserIsNull() {
        Exception exception = assertThrows(SaxXmlParserException.class, () -> {
            finder = new XmlFileFinderProvider(null, parserBuilder, reader);
            finder.search();
        });
        
        String expectedMessage = "Properties parser is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void searchMethodShuldRizeExceptionWhenParserBuilderIsNull() {
        Exception exception = assertThrows(SaxXmlParserException.class, () -> {
            finder = new XmlFileFinderProvider(propertiesParser, null, reader);
            finder.search();
        });
        
        String expectedMessage = "Xml parser builder is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void searchMethodShuldRizeExceptionWhenReaderIsNull() {
        Exception exception = assertThrows(SaxXmlParserException.class, () -> {
            when(parserBuilder.addParam("ApplicationProperties", appProps)).thenReturn(parserBuilder);
            when(parserBuilder.addParam("ReaderProvider", null)).thenReturn(parserBuilder);
            when(parserBuilder.build()).thenThrow(new SaxXmlParserException("Can`t build XmlFileParserProvider"));
            
            finder = new XmlFileFinderProvider(propertiesParser, parserBuilder, null);
            finder.search();
        });
        
        String expectedMessage = "Can`t build XmlFileParserProvider";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
