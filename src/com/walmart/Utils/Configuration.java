package com.walmart.Utils;

import com.walmart.Utils.web.Browsers;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

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

    public Configuration(boolean isWebTest) {
        try {
            _logger = Logger.getLogger(Configuration.class);

            Properties props = loadProperties(isWebTest);
            setCommonProps(props);
            if(isWebTest)
            {
                setWebProps(props);
            }
            else
            {
                setRestProps(props);
            }

        } catch (Exception ex) {
            _logger.error(ex);
        }

    }

    private Properties loadProperties(boolean isWebTest)
    {
        Properties props = new Properties();
        try {
            if (isWebTest) {
                props.load(new FileReader(String.format("%s/config.properties", System.getProperty("user.dir"))));
            } else {
                props.load(new FileReader(String.format("%s/restConfig.properties", System.getProperty("user.dir"))));
            }
        } catch (IOException e) {
            _logger.error(e);
        }

        return props;
    }


    public void setCommonProps(Properties mProps) {
        WaitTimeout = Integer.parseInt(mProps.getProperty("wait-timeout"));
        FilePath = mProps.getProperty("file-path");

    }
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

    public void setRestProps(Properties mProps)
    {
        BaseURI = mProps.getProperty("uri");
    }
}
