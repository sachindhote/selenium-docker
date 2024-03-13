package com.automation.tests;

import com.automation.util.Config;
import com.automation.util.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URL;

public class AbstractTest {

    protected WebDriver driver;
    private static final Logger log = LoggerFactory.getLogger(AbstractTest.class);

    @BeforeSuite
    public void setupConfig(){
        Config.initialize();
    }

    @BeforeTest
    public void setDriver(ITestContext ctx) throws MalformedURLException {
      //  WebDriverManager.chromedriver().setup();
      if( Boolean.parseBoolean(Config.get(Constants.GRID_ENABLED))){
          this.driver = getRemoteDriver();
      }else
          this.driver = getLocalDriver();
      ctx.setAttribute(Constants.DRIVER,this.driver);

// above if-ese condition cna be written as below
        //this.driver = Boolean.parseBoolean(Config.get(Constants.GRID_ENABLED)) ? getRemoteDriver() : getLocalDriver();

//        if(Boolean.getBoolean("selenium.grid.enabled")){
//            this.driver = getRemoteDriver();
//        }else
//            this.driver = getLocalDriver();
       // this.driver = new ChromeDriver();
       // driver.manage().window().maximize();
    }

    private WebDriver getRemoteDriver() throws MalformedURLException {
        Capabilities capabilities= new ChromeOptions();
        if(Constants.FIREFOX.equalsIgnoreCase(Config.get(Constants.BROWSER))){
            capabilities = new FirefoxOptions();
        }
//        if(System.getProperty("browser").equalsIgnoreCase("chrome")){
//            capabilities = new ChromeOptions();
//        }else
//            capabilities = new FirefoxOptions();

        String urlFormat = Config.get(Constants.GRID_URL_FORMAT);
        String hubHost = Config.get(Constants.GRID_HUB_HOST);
        String url = String.format(urlFormat,hubHost);
        log.info("grid url: {}", url);
        return new RemoteWebDriver(new URL(url),capabilities);
    }
    private WebDriver getLocalDriver(){
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }
    @AfterTest
    public void quitDriver(){
        this.driver.quit();
    }


}
