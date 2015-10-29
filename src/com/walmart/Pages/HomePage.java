package com.walmart.Pages;

import com.walmart.Utils.PageDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Set;


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
           /* txtValue = driver.findElement("xpath=homePop.txtShopping").getText();
           if(txtValue.equals("Start Shopping")) {
               txtPresent = true;
           }*/
        }
        catch(Exception e){
            _logger.error(e);
        }
        return txtPresent;
    }

   /* public boolean isSignInLinkPresent(){

    }*/
}

