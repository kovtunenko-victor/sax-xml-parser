package com.ylab.kovtunenko.xml.filefinder.argument;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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
        
        Arguments arguments;
        CommandLine cmd = parseArguments(args);

        if(cmd.getOptionValue("search") == null) {
            arguments = new Arguments(cmd.getOptionValue("file"));
        } else {
            arguments = new Arguments(cmd.getOptionValue("file")
                    , cmd.getOptionValue("search")
                    , getMaskType(cmd.getOptionValue("search")));
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
            throw new FileFinderAppException(ex.getMessage(), ex);
        }
    }
    
    private MaskType getMaskType(String maskValue) {
        Matcher matcher = Pattern.compile(".*[\\*].*").matcher(maskValue);
        
        if (matcher.find()) {
            return MaskType.MASK;
        }
        
        return MaskType.REGEXP;
    }
}
