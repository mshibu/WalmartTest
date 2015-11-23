package com.walmart.Pages;

import com.walmart.Utils.web.PageDriver;
import org.openqa.selenium.WebElement;


public class HomePage extends PortalPage {

    public HomePage(PageDriver driver){
        super(driver);
        _logger.debug("Open Home Page");
    }

    public boolean performZipAvailability(String zip){
        boolean txtPresent = false;
        String txtValue = " ";
        WebElement zipBox = driver.findElement("id=home.zip");
        zipBox.click();
        try{
            zipBox.clear();
            zipBox.sendKeys(zip);
            driver.findElement("home.checkAvailability").click();
            Thread.sleep(3000);

            txtPresent = driver.findElement("xpath=homePop.txtShopping").isDisplayed();
        }
        catch(Exception e){
            _logger.error(e);
        }
        return txtPresent;
    }

    public boolean signInLink(){
        boolean linkPresent = false;
        String txt = " ";
        try{
            txt = driver.findElement("home.SignIn").getText();
            System.out.println("text is: "+ txt);
            linkPresent = driver.findElement("home.SignIn").isDisplayed();
        }
        catch(Exception e) {
            _logger.error(e);
        }
            return linkPresent;
        }

    public GroceryPage navigateToGroceryPage(String zip){
        WebElement zipBox = driver.findElement("id=home.zip");
        zipBox.click();
        try{
            zipBox.clear();
            zipBox.sendKeys(zip);
            driver.findElement("home.checkAvailability").click();
            Thread.sleep(3000);

            driver.findElement("xpath=homePop.txtShopping").click();
        }
        catch(Exception e){
            _logger.error(e);
        }
        GroceryPage _gp = new GroceryPage(driver);
        return _gp;
    }
}

