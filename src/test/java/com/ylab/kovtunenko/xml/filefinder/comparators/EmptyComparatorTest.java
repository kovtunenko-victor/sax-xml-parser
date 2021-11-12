package com.ylab.kovtunenko.xml.filefinder.comparators;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EmptyComparatorTest {
    private final EmptyComparator comparator = new EmptyComparator();
    
    @Test
    public void compareMethodShouldAlwaysReturnTrue() {
        assertTrue(comparator.compare(null));
        assertTrue(comparator.compare("123"));
        comparator.setSearchValue("123");
        assertTrue(comparator.compare("123"));
        assertTrue(comparator.compare("456"));
    }
}
