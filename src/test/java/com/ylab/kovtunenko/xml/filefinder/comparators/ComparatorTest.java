package com.ylab.kovtunenko.xml.filefinder.comparators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ComparatorTest {
    private final Comparator comparator = new Comparator() { public boolean compare(String searchData) { return true; } };
    
    @Test
    public void getResultMethodShouldReturnListOfDirectories() {
        emulateOpenTag(false, "/");
        emulateOpenTag(true, "file-0001");
        emulateOpenTag(false, "directory-01");
        emulateOpenTag(true, "file-0002");
        emulateOpenTag(true, "file-0003");
        emulateOpenTag(false, "directory-02");
        emulateOpenTag(true, "file-0004");
        emulateCloseTag();
        emulateOpenTag(true, "file-0005");
        emulateCloseTag();
        emulateOpenTag(true, "file-0006");
        emulateCloseTag();
        
        String result = comparator.getResult();
        
        String expected = "/file-0001" + System.lineSeparator()
                + "/directory-01/file-0002" + System.lineSeparator()
                + "/directory-01/file-0003" + System.lineSeparator()
                + "/directory-01/directory-02/file-0004" + System.lineSeparator()
                + "/directory-01/file-0005" + System.lineSeparator()
                + "/file-0006" + System.lineSeparator();
    
      assertEquals(expected, result);
    }
    
    private void emulateOpenTag(boolean isFile, String name) {
        comparator.setIsFile(isFile);
        comparator.insert(name);
    }
    private void emulateCloseTag() {
        comparator.remuveLastFolder();
    }
}
