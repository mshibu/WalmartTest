package com.walmart.Base;

import com.walmart.Utils.PageDriver;
import org.apache.log4j.Logger;

/**
 * Created by shiba_000 on 10/17/2015.
 */
public abstract class BasePage {
    public Logger _logger;
    public PageDriver driver;

    public BasePage(PageDriver driver){
        _logger = Logger.getLogger(BaseTest.class);
        this.driver = driver;
    }
}
