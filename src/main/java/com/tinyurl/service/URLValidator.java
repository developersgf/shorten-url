package com.tinyurl.service;


import org.apache.commons.validator.routines.UrlValidator;

/**
 * <pre>
 * validate the long url it is a URL, not a common string
 * </pre>
 */
public class URLValidator {

    private URLValidator() {
    }

    public static final URLValidator INSTANCE = new URLValidator();

    public boolean validateURL(String url) {
        final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        return urlValidator.isValid(url);
    }
}