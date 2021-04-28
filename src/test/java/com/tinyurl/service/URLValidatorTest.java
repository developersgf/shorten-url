package com.tinyurl.service;

import org.junit.Assert;
import org.junit.Test;

public class URLValidatorTest {

    @Test
    public void testSuccess() {
        Assert.assertTrue(URLValidator.INSTANCE.validateURL("https://calendar.google.com/calendar/u/0/r?tab=rc"));
        Assert.assertTrue(URLValidator.INSTANCE.validateURL("https://www.youtube.com/c/%E6%AF%8F%E6%97%A5%E4%B8%80%E4%B9%A6/videos"));
        Assert.assertTrue(URLValidator.INSTANCE.validateURL("http://127.0.0.1:8080"));
    }

    @Test
    public void testFailed() {
        Assert.assertFalse(URLValidator.INSTANCE.validateURL("http://localhost:8080"));
    }

}
