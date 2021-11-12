package com.ylab.kovtunenko.xml.filefinder.comparators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import com.ylab.kovtunenko.xml.filefinder.exceptions.FileFinderAppException;

public class MaskComparatorTest {
    private final MaskComparator comparator = new MaskComparator();
    
    @Test
    public void compareMethodShouldReturnTrueIfDataFindedByMask() {
        comparator.setSearchValue("12*.txt");
        assertTrue(comparator.compare("12345.txt"));
    }
    
    @Test
    public void compareMethodShouldReturnTrueIfDataNotFindedByMask() {
        comparator.setSearchValue("45*.txt");
        assertTrue(comparator.compare("12345.txt"));
    }
    
    @Test
    public void compareMethodShouldReturnTrueIfSearchValueIsNull() {
        comparator.setSearchValue(null);
        assertTrue(comparator.compare("12345"));
    }
    
    @Test
    public void compareMethodShouldRizeExeptionIfSearchDataIsNull() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            comparator.setSearchValue("[a-zA-Z]+");
            comparator.compare(null);
        });

        String expected = "Data for search is null";

        assertEquals(expected, exception.getMessage());
    }
}
