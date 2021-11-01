package com.ylab.kovtunenko.sax.filefinder.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.ylab.kovtunenko.sax.filefinder.exceptions.SaxXmlParserException;
import com.ylab.kovtunenko.sax.filefinder.providers.RegexSearchProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.builders.RegexSearchProviderBuilder;

public class RegexSearchProviderBuilderTest {
    private RegexSearchProviderBuilder builder;

    @Test
    public void buildMethodShuldReturnXmlFileParserProviderInstance() {
        builder = new RegexSearchProviderBuilder();
        builder.addParam(RegexSearchProviderBuilder.SEARCH_DATA, "File.txt");

        RegexSearchProvider provider = builder.build();

        assertNotNull(provider);
    }

    @Test
    public void buildMethodShuldRizeExceptionWhenApplicationPropertiesIsNotSet() {
        Exception exception = assertThrows(SaxXmlParserException.class, () -> {
            builder = new RegexSearchProviderBuilder();
            builder.build();
        });

        String expectedMessage = "Can`t build RegexSearchProvider";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void buildMethodShuldRizeExceptionWhenParamsIsNull() {
        Exception exception = assertThrows(SaxXmlParserException.class, () -> {
            builder = new RegexSearchProviderBuilder();
            builder.addParam(RegexSearchProviderBuilder.SEARCH_DATA, null);

            builder.build();
        });

        String expectedMessage = "Can`t build RegexSearchProvider";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
