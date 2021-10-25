package com.ylab.kovtunenko.sax.filefinder.providers;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.ylab.kovtunenko.sax.filefinder.domain.ApplicationProperties;
import com.ylab.kovtunenko.sax.filefinder.exceptions.SaxXmlParserException;

public class InputPropertiesParserProvider implements ParserProvider<ApplicationProperties> {
    private final String[] properties;
    private final Options options = new Options();
    
    public InputPropertiesParserProvider(String[] properties) {
        initOptions(); 
        this.properties = Arrays.copyOf(properties, properties.length);  
    }

    @Override
    public ApplicationProperties parse() {
        ApplicationProperties appProps;
        CommandLine cmd = parseProperties();

        if(cmd.getOptionValue("search") == null) {
            appProps = new ApplicationProperties(cmd.getOptionValue("file"));
        } else {
            appProps = new ApplicationProperties(cmd.getOptionValue("file"), formatSearchValue(cmd.getOptionValue("search")));
        }
        
        return appProps;
    }
    
    private String formatSearchValue (String searchValue) {
        String value = new String (searchValue);
        value = value.replaceAll("'", "");

        Matcher matcher = Pattern.compile("[\\*]\\..+").matcher(value);
        if (matcher.find()) {
            value = "." + value.replaceAll("\\*", "\\*\\\\");
            return value;
        }
        
        matcher = Pattern.compile(".+\\.\\*").matcher(value);
        if (matcher.find()) {
            value = value.replaceAll("\\.", "\\\\\\.\\.");
            return value;
        }
        
        return value;
    }
    
    private void initOptions() {
        Option file = new Option("f", "file", true, "input file");
        file.setRequired(true);
        
        Option search = new Option("s", "search", true, "simple search");
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
