package com.ylab.kovtunenko.sax.filefinder.utils;

import java.io.File;
import java.net.URISyntaxException;

public class TestUtils {
    public static String getResourceFilePath(String fileName) {
        try {
            return new File(TestUtils.class.getClassLoader().getResource(fileName).toURI()).getAbsolutePath();
        } catch(URISyntaxException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
