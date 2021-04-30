package com.tinyurl.service;

import org.junit.Test;
import org.junit.Assert;

public class URLValidatorTest {

    @Test
    public void testSuccess() {
        Assert.assertTrue(URLValidator.INSTANCE.validateURL("http://127.0.0.1:8080"));
        Assert.assertTrue(URLValidator.INSTANCE.validateURL("https://travis-ci.com"));

        Assert.assertTrue(URLValidator.INSTANCE.validateURL("https://mail.google.com"));
        Assert.assertTrue(URLValidator.INSTANCE.validateURL("https://calendar.google.com/calendar/u/0/r?tab=rc"));
        Assert.assertTrue(URLValidator.INSTANCE.validateURL("https://www.youtube.com/c/%E6%AF%8F%E6%97%A5%E4%B8%80%E4%B9%A6/videos"));
    }

    @Test
    public void testFailed() {
        Assert.assertFalse(URLValidator.INSTANCE.validateURL("http://localhost:8080"));
        Assert.assertFalse(URLValidator.INSTANCE.validateURL("htt://127.0.0.1:8080"));
    }

}
