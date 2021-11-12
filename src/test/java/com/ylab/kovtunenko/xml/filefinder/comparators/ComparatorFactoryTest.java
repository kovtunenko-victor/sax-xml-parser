package com.ylab.kovtunenko.xml.filefinder.comparators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import com.ylab.kovtunenko.xml.filefinder.constants.GlobalConstants.MaskType;
import com.ylab.kovtunenko.xml.filefinder.exceptions.FileFinderAppException;

public class ComparatorFactoryTest {
    
    @Test
    public void newInstanceMethodShouldReturnEmptyComparatorIfTypeEqualsNoMask() {
        Comparator comparator = ComparatorFactory.newInstance(MaskType.NO_MASK);
        assertTrue(comparator instanceof EmptyComparator);
    }
    
    @Test
    public void newInstanceMethodShouldReturnMaskComparatorIfTypeEqualsMask() {
        Comparator comparator = ComparatorFactory.newInstance(MaskType.MASK);
        assertTrue(comparator instanceof MaskComparator);
    }
    
    @Test
    public void newInstanceMethodShouldReturnRegexpComparatorIfTypeEqualsRegexp() {
        Comparator comparator = ComparatorFactory.newInstance(MaskType.REGEXP);
        assertTrue(comparator instanceof RegexpComparator);
    }
    
    @Test
    public void newInstanceMethodShouldRizeErrorWhenMaskTypeIsNull() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            ComparatorFactory.newInstance(null);
        });

        String expected = "Mask type is null";

        assertEquals(expected, exception.getMessage());
    }
    
    @Test
    public void newInstanceMethodShouldRizeExeptionWhenMaskTypeIsUnknown() {
        Exception exception = assertThrows(FileFinderAppException.class, () -> {
            ComparatorFactory.newInstance(MaskType.UNKNOWN);
        });

        String expected = "MaskType [UNKNOWN] is unknown";

        assertEquals(expected, exception.getMessage());
    }
}
