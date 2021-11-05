package com.ylab.kovtunenko.sax.filefinder.providers.impl;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import com.ylab.kovtunenko.sax.filefinder.domain.Arguments;
import com.ylab.kovtunenko.sax.filefinder.enums.MaskType;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.ParserProvider;

public class ArgumentsParserProvider implements ParserProvider<Arguments, String[]> {
    private final Options options;
    private final ParserProvider<MaskType, String> searchValueParser;
    
    public ArgumentsParserProvider(ParserProvider<MaskType, String> searchValueParser) {
        options = new Options();
        initOptions(); 
        this.searchValueParser = searchValueParser;
    }

    @Override
    public Arguments parse(String[] args) {
        if(args == null) {
            throw new FileFinderAppException("Incoming arguments is null");
        }
        
        Arguments arguments;
        CommandLine cmd = parseArguments(args);

        if(cmd.getOptionValue("search") == null) {
            arguments = new Arguments(cmd.getOptionValue("file"));
        } else {
            arguments = new Arguments(cmd.getOptionValue("file")
                    , cmd.getOptionValue("search")
                    , searchValueParser.parse(cmd.getOptionValue("search")));
        }
        
        return arguments;
    }
    
    private void initOptions() {
        Option file = new Option("f", "file", true, "input file");
        file.setRequired(true);
        
        Option search = new Option("s", "search", true, "simple search");
        search.setRequired(false);
        
        options.addOption(file);
        options.addOption(search);
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
}
