package com.ylab.kovtunenko.sax.filefinder.providers.impl;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.ylab.kovtunenko.sax.filefinder.constants.GlobalConstants;
import com.ylab.kovtunenko.sax.filefinder.domain.Arguments;
import com.ylab.kovtunenko.sax.filefinder.enums.MaskType;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.ParserProvider;

public class ArgumentsParserProvider implements ParserProvider<Arguments, String[]> {
    private final Options options;
    
    private static final String FILE = "f";
    private static final String SIMPLE_SEARCH = "s";
    private static final String REGULAR_SEARCH = "S";  
    
    public ArgumentsParserProvider() {
        options = new Options();
        initOptions(); 
    }

    @Override
    public Arguments parse(String[] args) {
        if(args == null) {
            throw new FileFinderAppException("Incoming arguments is null");
        }
        
        Arguments arguments;
        CommandLine cmd = parseArguments(args);

        arguments = new Arguments(cmd.getOptionValue(FILE)
                , getMask(cmd)
                , getMaskType(cmd));
        
        return arguments;
    }
    
    private void initOptions() {       
        Option file = new Option(FILE, FILE, true, "input file");
        file.setRequired(true);
        
        Option simpleSearch = new Option(SIMPLE_SEARCH, SIMPLE_SEARCH, true, "simple search");
        simpleSearch.setRequired(false);
        
        Option regularSearch = new Option(REGULAR_SEARCH, REGULAR_SEARCH, true, "regular search");
        regularSearch.setRequired(false);
        
        options.addOption(file);
        options.addOption(simpleSearch);
        options.addOption(regularSearch);
    }
    
    private CommandLine parseArguments(String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            return cmd;
        } catch (ParseException ex) {
            throw new FileFinderAppException("Error when parse properties", ex);
        }
    }
    
    private String getMask(CommandLine cmd) {
        if(cmd.getOptionValue(REGULAR_SEARCH) != null) {
            return cmd.getOptionValue(REGULAR_SEARCH);
        } 
        
        if(cmd.getOptionValue(SIMPLE_SEARCH) != null) {
            return cmd.getOptionValue(SIMPLE_SEARCH);
        }
        
        return GlobalConstants.EMPTY_STRING;
    }
    
    private MaskType getMaskType(CommandLine cmd) {
        if(cmd.getOptionValue(REGULAR_SEARCH) != null) {
            return MaskType.REGEXP;
        } 
        
        if(cmd.getOptionValue(SIMPLE_SEARCH) != null) {
            return MaskType.MASK;
        }
        
        return MaskType.NO_MASK;
    }
}
