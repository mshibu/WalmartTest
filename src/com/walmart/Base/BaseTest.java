package com.walmart.Base;

import com.walmart.Utils.Configuration;
import org.apache.log4j.Logger;


public abstract class BaseTest {

    public static final Configuration _config;

    static {
        _config = new Configuration(true);
    }

    //Rally connection
    public Logger _logger;

    public BaseTest() {
        _logger = Logger.getLogger(BaseTest.class);
    }
}
