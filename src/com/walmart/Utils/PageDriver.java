package com.walmart.Utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class PageDriver {

    public final Configuration _configuration;
    private final Browsers _browser;
    private WebDriver _webDriver;
    private String _mainWindowHandler;
    private Set<String> _windowHandles;
    private Logger _logger;

    public PageDriver(Configuration configuration) {
        _configuration = configuration;
        _browser = _configuration.Browser;
        _logger = Logger.getLogger(PageDriver.class);
        start();
    }

    public WebDriver getDriver() {
        if (_webDriver == null) {
            try {
                start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return _webDriver;
    }

    public Browsers getBrowser() {
        return _browser;
    }

    public String getMainWindowHandler() {
        return _mainWindowHandler;
    }

   /* public Set<String> getAllWindowHandles() {return _webDriver.getWindowHandles(); }

    public void switchWindow(Set<String> handles){
        for(String handle : handles){
            _webDriver.switchTo().window(handle);
        }
    }*/

    private void start(){
        try{
            switch (_browser){
                case InternetExplorer:
                    _webDriver = startInternetExplorer();
                    break;
                case Firefox:
                    _webDriver = startFirefox();
                    break;
                case Chrome:
                    _webDriver = startChrome();
                    break;
                case HtmlUnit:
                    _webDriver = startHtmlUnit();
                    break;
                default:
                    _webDriver = startHtmlUnit();
                    break;
            }
            _webDriver.get(_configuration.BaseUrl);
            if(_browser != Browsers.HtmlUnit){
                _webDriver.manage().window().maximize();
                _webDriver.manage().deleteAllCookies();
            }
            _mainWindowHandler = _webDriver.getWindowHandle();
        }

        catch (Exception e){
            _logger.error(e);
        }
    }

    private InternetExplorerDriver startInternetExplorer() {
        System.setProperty("webdriver.ie.driver", String.format("%s/IEDriverServer.exe", System.getProperty("user.dir")));
        DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
        caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        caps.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, false);
        caps.setCapability(InternetExplorerDriver.IE_SWITCHES, "-private");
        caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, "true");
        caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        return new InternetExplorerDriver(caps);
    }

    private FirefoxDriver startFirefox(){
        return new FirefoxDriver();
    }

    private ChromeDriver startChrome(){
        System.setProperty("webdriver.chrome.driver", String.format("%s/chromedriver.exe", System.getProperty("user.dir")));
        return new ChromeDriver();
    }

    private HtmlUnitDriver startHtmlUnit(){
        return new HtmlUnitDriver();
    }

    public WebElement findElement(String locator) {
        try {
            WebElement _element = _webDriver.findElement(WBy.get(locator));
            return _element;
        }
        catch (Exception ex) {
            _logger.error(ex);
            return null;
        }
    }

    public Collection<WebElement> findElements(String locator) {
        Collection<WebElement> elements = null;
        try {
            Collection<WebElement> webElements = _webDriver.findElements(WBy.get(locator));
            if (webElements.size() > 0) {
                elements = new ArrayList<WebElement>();
            }
            return elements;
        }

        catch (Exception ex) {
            _logger.error(ex);
            return null;
        }
    }

    public void quit() {
        if (_webDriver == null) {
            return;
        }
        _webDriver.quit();
        _webDriver = null;
    }

}
