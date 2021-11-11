package com.ylab.kovtunenko.xml.filefinder;

import com.ylab.kovtunenko.xml.filefinder.argument.ArgumentsParser;
import com.ylab.kovtunenko.xml.filefinder.domain.Arguments;
import com.ylab.kovtunenko.xml.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.xml.filefinder.xml.SaxParser;

public class Application {
    public static void main(String[] args) {
        try {
            Arguments arguments = new ArgumentsParser().parse(args);
            String result = new SaxParser().parse(arguments);

            System.out.println(result);
        } catch (FileFinderAppException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
