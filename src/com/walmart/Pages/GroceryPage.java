package com.walmart.Pages;

import com.walmart.Utils.web.PageDriver;

public class GroceryPage extends PortalPage {

    public GroceryPage(PageDriver driver){
        super(driver);
        _logger.debug("Open Grocery Page");
    }

   public int getSliderItemsCount(){

        return driver.findElements("grocery.slider.items").size();
    }

   /* public boolean presenceOfFeaturedCategories(){

    }*/
}
