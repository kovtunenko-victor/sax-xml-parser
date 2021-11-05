package com.ylab.kovtunenko.sax.filefinder.providers;

import com.ylab.kovtunenko.sax.filefinder.enums.MaskType;
import com.ylab.kovtunenko.sax.filefinder.exceptions.FileFinderAppException;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.ExtensionSearchProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.NameSearchProvider;
import com.ylab.kovtunenko.sax.filefinder.providers.impl.RegexpSearchProvider;

public class SearchProviderFactory {
    public static SearchProvider<String, String> newInstance(MaskType maskType) {
        if (maskType == null) {
            throw new FileFinderAppException("Mask type is null");
        }

        switch (maskType) {
            case NO_MASK:
                return new SearchProvider<String, String>() {
                    @Override
                    public boolean search(String searchData, String searchValue) {
                        return true;
                    }
                };
            case REGEXP:
                return new RegexpSearchProvider();
            case NAME:
                return new NameSearchProvider();
            case EXTENSION:
                return new ExtensionSearchProvider();
            default:
                throw new FileFinderAppException(String.format("MaskType [%s] is unknown", maskType.name()));
        }
    }
}
