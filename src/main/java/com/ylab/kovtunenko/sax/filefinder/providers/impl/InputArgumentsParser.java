package com.ylab.kovtunenko.sax.filefinder.providers.impl;

import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.ylab.kovtunenko.sax.filefinder.constants.GlobalConstants;
import com.ylab.kovtunenko.sax.filefinder.domain.Arguments;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.FactoryProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.ParserProvider;

public class InputArgumentsParser implements ParserProvider<Arguments> {
    private final String[] arguments;
    private final Options options;
    private final FactoryProvider<RegexpSearchValueParser> searchValueParserBuilder;
    
    public InputArgumentsParser(String[] arguments, FactoryProvider<RegexpSearchValueParser> searchValueParserBuilder) {
        options = new Options();
        initOptions(); 
        this.searchValueParserBuilder = searchValueParserBuilder;
        this.arguments = Arrays.copyOf(arguments, arguments.length);  
    }

    @Override
    public Arguments parse() {
        Arguments arguments;
        CommandLine cmd = parseArguments();

        if(cmd.getOptionValue("search") == null) {
            arguments = new Arguments(cmd.getOptionValue("file"));
        } else {
            arguments = new Arguments(cmd.getOptionValue("file"), formatSearchValue(cmd.getOptionValue("search")));
        }
        
        return arguments;
    }
    
    private String formatSearchValue (String searchValue) {
        return searchValueParserBuilder.addParam(GlobalConstants.SEARCH_DATA, searchValue).build().parse();
    }
    
    private void initOptions() {
        Option file = new Option("f", "file", true, "input file");
        file.setRequired(true);
        
        Option search = new Option("s", "search", true, "simple search");
        search.setRequired(false);
        
        options.addOption(file);
        options.addOption(search);
    }
    
    private CommandLine parseArguments() {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, arguments);
            return cmd;
        } catch (ParseException ex) {
            throw new FileFinderAppException("Error when parse properties", ex);
        }
    }
}
