package com.vinaykumar.hrmauto.driver;

import com.vinaykumar.hrmauto.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {


    public static WebDriver create( ) {
        String browser = ConfigReader.get("browser");

        WebDriver driver;

        // Browser selection
        if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            driver = new ChromeDriver();
        }

        // Maximize the browser
        driver.manage().window().maximize();

        return driver;
    }
}
