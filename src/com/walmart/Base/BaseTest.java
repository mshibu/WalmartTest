package com.walmart.Base;

import com.walmart.Utils.Configuration;
import org.apache.log4j.Logger;

/**
 * Created by shiba_000 on 10/23/2015.
 */
public abstract class BaseTest {

    public static final Configuration _config;

    static {
        _config = new Configuration();
    }

    //Rally connection
    public Logger _logger;

    public BaseTest() {
        _logger = Logger.getLogger(BaseTest.class);
    }
}
