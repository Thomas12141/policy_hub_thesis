package org.example.validation.semantic_validation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class SupportedLocalesTester {
    private SupportedLocalesTester() {
    }
    protected static final Set<String> SUPPORTED_LOCALES = new HashSet<>(Arrays.stream(Locale.IsoCountryCode.values()).
            map(s -> s.toString().toUpperCase()).toList());
    public static boolean isSupportedLocale(String locale) {
        return SUPPORTED_LOCALES.contains(locale.toUpperCase());
    }
}
