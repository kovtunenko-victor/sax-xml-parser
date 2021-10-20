package com.ylab.kovtunenko.sax.xml.parser;

import java.io.File;

import com.ylab.kovtunenko.sax.xml.parser.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.xml.parser.providers.InputPropertiesParserProvider;
import com.ylab.kovtunenko.sax.xml.parser.providers.ParserProvider;

public class Application {
    public static void main(String[] args) {
        ParserProvider<ApplicationProperties> propertiesParser = new InputPropertiesParserProvider(args);
        ApplicationProperties appProps = propertiesParser.parse();
        
        File file = new File(appProps.getFileName());
        
        System.out.println(appProps.getFileName());
        System.out.println(file.exists());
    }
}
