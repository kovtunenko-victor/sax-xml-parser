package com.ylab.kovtunenko.xml.filefinder;

import com.ylab.kovtunenko.xml.filefinder.argument.Arguments;
import com.ylab.kovtunenko.xml.filefinder.argument.ArgumentsParser;
import com.ylab.kovtunenko.xml.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.xml.filefinder.xml.SaxParser;

public class Application {
    public static void main(String[] args) {
        try {
            Arguments arguments = new ArgumentsParser().parse(args);
            new SaxParser().parse(arguments);
        } catch (FileFinderAppException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
