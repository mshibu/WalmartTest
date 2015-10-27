package com.walmart.Pages;

import com.walmart.Utils.PageDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Set;

/**
 * Created by shiba_000 on 10/23/2015.
 */
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

            Set<String> handles = driver.getAllWindowHandles();
            driver.switchWindow(handles);

            txtValue = driver.findElement("xpath=homePop.txtShopping").getText();
           if(txtValue == "Start Shopping")
               txtPresent = true;
        }
        catch(Exception e){
            _logger.error(e);
        }
        return txtPresent;
    }

   /* public boolean isSignInLinkPresent(){

    }*/
}

