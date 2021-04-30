package com.tinyurl.bean;

import org.junit.Assert;
import org.junit.Test;

public class URLValidatorTest {

    private URLValidator urlValidator = new URLValidator();

    @Test
    public void testSuccess() {
        Assert.assertTrue(urlValidator.validateURL("http://127.0.0.1:8080"));
        Assert.assertTrue(urlValidator.validateURL("https://travis-ci.com"));

        Assert.assertTrue(urlValidator.validateURL("https://mail.google.com"));
        Assert.assertTrue(urlValidator.validateURL("https://calendar.google.com/calendar/u/0/r?tab=rc"));
        Assert.assertTrue(urlValidator.validateURL("https://www.youtube.com/c/%E6%AF%8F%E6%97%A5%E4%B8%80%E4%B9%A6/videos"));
    }

    @Test
    public void testFailed() {
        Assert.assertFalse(urlValidator.validateURL("http://localhost:8080"));
        Assert.assertFalse(urlValidator.validateURL("htt://127.0.0.1:8080"));
    }

}
