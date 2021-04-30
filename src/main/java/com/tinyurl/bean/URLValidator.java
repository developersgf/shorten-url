package com.tinyurl.bean;

import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;

/**
 * <pre>
 * validate the long url it is a URL, not a common string
 * </pre>
 */
@AllArgsConstructor
public class URLValidator {

    public boolean validateURL(String url) {
        final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        return urlValidator.isValid(url);
    }

}