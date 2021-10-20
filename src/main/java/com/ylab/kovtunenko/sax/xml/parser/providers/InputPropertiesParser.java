package com.ylab.kovtunenko.sax.xml.parser.providers;

import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.ylab.kovtunenko.sax.xml.parser.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.xml.parser.exceptions.SaxXmlParserException;

public class InputPropertiesParser implements Parser<ApplicationProperties> {
    private final String[] properties;
    private final Options options = new Options();
    
    public InputPropertiesParser(String[] properties) {
        initOptions(); 
        this.properties = Arrays.copyOf(properties, properties.length);  
    }

    @Override
    public ApplicationProperties read() {
        ApplicationProperties appProps;
        CommandLine cmd = parseProperties();

        if(cmd.getOptionValue("search") == null) {
            appProps = new ApplicationProperties(cmd.getOptionValue("file"));
        } else {
            appProps = new ApplicationProperties(cmd.getOptionValue("file"), cmd.getOptionValue("search"));
        }
        
        return appProps;
    }
    
    private void initOptions() {
        Option file = new Option("f", "file", true, "input file");
        file.setRequired(true);
        
        Option search = new Option("s", "search", true, "regexp for search");
        search.setRequired(false);
        
        options.addOption(file);
        options.addOption(search);
    }
    
    private CommandLine parseProperties() {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, properties);
            return cmd;
        } catch (ParseException ex) {
            throw new SaxXmlParserException("Error when parse properties", ex);
        }
    }
}
