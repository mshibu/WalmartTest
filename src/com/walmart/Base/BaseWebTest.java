package com.walmart.Base;

import com.walmart.Utils.ExcelUtils;
import com.walmart.Utils.PageDriver;
import com.walmart.Utils.WBy;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public abstract class BaseWebTest extends BaseTest {
    public PageDriver driver;
    public ExcelUtils excelUtils;

    @BeforeSuite
    public void beforeSuite() {
        driver = new PageDriver(_config);
        WBy.loadJsonMap(String.format("%s/locators.json", System.getProperty("user.dir")));
        excelUtils = new ExcelUtils();
        System.out.println("BaseWebTest");
    }

    @AfterSuite
    public void afterSuite() {
        driver.quit();
    }
}
