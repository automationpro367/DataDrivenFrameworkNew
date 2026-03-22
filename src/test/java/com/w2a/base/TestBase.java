package com.w2a.base;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

// LOG4J 2 KE SAHI IMPORTS (Java 21 ke liye compatible)
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.w2a.utilities.ExcelReader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

    public static WebDriver driver;
    public static Properties configue = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;
    public static ExcelReader excel= new ExcelReader(System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
    public static WebDriverWait wait;
    
    // LOG4J 2 INITIALIZATION (Ab error nahi aayega)
    public static Logger log = LogManager.getLogger(TestBase.class);

    @BeforeSuite
    public void setUp() {
        if (driver == null) {
            try {
                // Configue properties load karein
                fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Configue.properties");
                configue.load(fis);
                log.info("Configue file loaded successfully"); // log.info ya log.debug dono chalenge
                
                // OR properties load karein
                fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
                OR.load(fis);
                log.info("OR file loaded successfully");

                String browser = configue.getProperty("browser");

                // Browser selection using WebDriverManager (No .exe needed)
                if (browser.equalsIgnoreCase("chrome")) {
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    log.info("Chrome Browser Launched");
                } else if (browser.equalsIgnoreCase("firefox")) {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    log.info("Firefox Browser Launched");
                } else if (browser.equalsIgnoreCase("edge")) {
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    log.info("Edge Browser Launched");
                }

                driver.get(configue.getProperty("testsiteurl"));
                driver.manage().window().maximize();
                
                // Selenium 4 Implicit Wait (Duration class use kar rahe hain)
                int waitTime = Integer.parseInt(configue.getProperty("implicit.wait"));
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime));
                wait= new WebDriverWait(driver,Duration.ofSeconds(10));

            } catch (Exception e) {
                log.error("Setup method fail ho gaya: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public boolean isElementPresent(By by) {
    	try{
    		driver.findElement(by);
    		return true;
    	}
    	catch(NoSuchElementException e){
    		return false;
    	}
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.info("Browser closed and test execution finished");
        }
    }
}