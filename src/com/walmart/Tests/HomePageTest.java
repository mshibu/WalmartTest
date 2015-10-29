package com.walmart.Tests;

import com.walmart.Base.BaseWebTest;
import com.walmart.Pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;


public class HomePageTest extends BaseWebTest {

    private HomePage _hp;

    @BeforeClass
    public void beforeClass(){
        _hp = new HomePage(driver);
    }

    @DataProvider(name = "zip-data")
    public Object[][] getZip() throws Exception {
        Object[][] data = excelUtils.getSimpleExcelData(driver._configuration.FilePath);
        return data;
    }

    @Test(dataProvider = "zip-data")
    public void testZipAvailability(String zip){
        boolean txtAvailable = _hp.performZipAvailability(zip);
        Assert.assertTrue(txtAvailable);
    }
}
