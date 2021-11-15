package com.ylab.kovtunenko.xml.filefinder.arguments;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.ylab.kovtunenko.xml.filefinder.constants.GlobalConstants;
import com.ylab.kovtunenko.xml.filefinder.constants.GlobalConstants.MaskType;
import com.ylab.kovtunenko.xml.filefinder.exceptions.FileFinderAppException;

public class ArgumentsParser {
    private final Options options;
    
    public ArgumentsParser() {
        options = new Options();
        initOptions(); 
    }

    public Arguments parse(String[] args) {
        if(args == null) {
            throw new FileFinderAppException("Incoming arguments is null");
        }
        
        CommandLine cmd = parseArguments(args);
        
        return new Arguments(cmd.getOptionValue(GlobalConstants.FILE), getMask(cmd), getMaskType(cmd));
    }
    
    private void initOptions() {
        Option file = new Option(GlobalConstants.FILE, GlobalConstants.FILE, true, "input file");
        file.setRequired(true);
        
        Option simpleSearch = new Option(GlobalConstants.SIMPLE_SEARCH, GlobalConstants.SIMPLE_SEARCH, true, "simple search");
        simpleSearch.setRequired(false);
        
        Option regularSearch = new Option(GlobalConstants.REGULAR_SEARCH, GlobalConstants.REGULAR_SEARCH, true, "regular search");
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
            throw new FileFinderAppException(ex.getMessage(), ex);
        }
    }
    
    private String getMask(CommandLine cmd) {
        if(cmd.getOptionValue(GlobalConstants.REGULAR_SEARCH) != null) {
            return cmd.getOptionValue(GlobalConstants.REGULAR_SEARCH);
        } 
        
        if(cmd.getOptionValue(GlobalConstants.SIMPLE_SEARCH) != null) {
            return cmd.getOptionValue(GlobalConstants.SIMPLE_SEARCH);
        }
        
        return GlobalConstants.EMPTY_STRING;
    }
    
    private MaskType getMaskType(CommandLine cmd) {
        if(cmd.getOptionValue(GlobalConstants.REGULAR_SEARCH) != null) {
            return MaskType.REGEXP;
        } 
        
        if(cmd.getOptionValue(GlobalConstants.SIMPLE_SEARCH) != null) {
            return MaskType.MASK;
        }
        
        return MaskType.NO_MASK;
    }
}
