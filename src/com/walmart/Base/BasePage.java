package com.walmart.Base;

import com.walmart.Utils.web.PageDriver;
import org.apache.log4j.Logger;


public abstract class BasePage {
    public Logger _logger;
    public PageDriver driver;

    public BasePage(PageDriver driver){
        _logger = Logger.getLogger(BaseTest.class);
        this.driver = driver;
    }
}
