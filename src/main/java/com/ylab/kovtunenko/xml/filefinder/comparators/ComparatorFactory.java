package com.ylab.kovtunenko.xml.filefinder.comparators;

import com.ylab.kovtunenko.xml.filefinder.constants.GlobalConstants.MaskType;
import com.ylab.kovtunenko.xml.filefinder.exceptions.FileFinderAppException;

public class ComparatorFactory {
    public static Comparator newInstance(MaskType maskType) {
        if (maskType == null) {
            throw new FileFinderAppException("Mask type is null");
        }

        switch (maskType) {
            case NO_MASK:
                return new EmptyComparator();
            case MASK:
                return new MaskComparator();
            case REGEXP:
                return new RegexpComparator();
            default:
                throw new FileFinderAppException(String.format("MaskType [%s] is unknown", maskType.name()));
        }
    }
}
