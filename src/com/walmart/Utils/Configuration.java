package com.walmart.Utils;

import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by shiba_000 on 10/17/2015.
 */
public class Configuration {

    public Browsers Browser;
    public String Device;
    public String BaseUrl;
    public String BaseURI;
    public String TestResultPath;
    public String TestDataPath;
    public int WaitTimeout;
    private Logger _logger;
    public String FilePath;

    public Configuration() {
        try {
            _logger = Logger.getLogger(Configuration.class);

            Properties props = loadProperties();
           // setCommonProps(props);
            setWebProps(props);

        } catch (Exception ex) {
            _logger.error(ex);
        }

    }

    private Properties loadProperties()
    {
        Properties props = new Properties();
        try {
            props.load(new FileReader(String.format("%s/config.properties", System.getProperty("user.dir"))));
        }
        catch (IOException e) {
            _logger.error(e);
        }

        return props;
    }


   /* public void setCommonProps(Properties mProps) {
        WaitTimeout = Integer.parseInt(mProps.getProperty("wait-timeout"));
        FilePath = mProps.getProperty("file-path");

    }*/
    public void setWebProps(Properties mProps)
    {
        WaitTimeout = Integer.parseInt(mProps.getProperty("wait-timeout"));
        FilePath = mProps.getProperty("file-path");
        //Browser = Browsers.valueOf(props.getProperty("browser"));
        String browser = mProps.getProperty("browser");
        Browser = Browsers.Firefox;
        if(browser.toLowerCase().equals("firefox"))
        {
            Browser = Browsers.Firefox;
        }
        else if(browser.toLowerCase().equals("chrome"))
        {
            Browser = Browsers.Chrome;
        }
        else if(browser.toLowerCase().equals("htmlunit"))
        {
            Browser = Browsers.HtmlUnit;
        }
        else if(browser.toLowerCase().equals("ie"))
        {
            Browser = Browsers.InternetExplorer;
        }

        BaseUrl = mProps.getProperty("url");
        Device = mProps.getProperty("device");
        TestResultPath = mProps.getProperty("test-result-path");
        TestDataPath = mProps.getProperty("test-data-path");
        System.out.println("Configuration");
    }
}
