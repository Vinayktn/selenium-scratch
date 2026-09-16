package com.vinaykumar.hrmauto.base;

import com.vinaykumar.hrmauto.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(ConfigReader.get("timeout.explicit"))));
    }

    //utility methods
    protected void sendText(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected void waitForListToSettle(By locator) {
        int previousCount = -1;
        for (int i = 0; i < 10; i++) {
            int currentCount = driver.findElements(locator).size();

            if (previousCount == currentCount && currentCount > 0)
                return;
            else
                previousCount = currentCount;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // nothing to do here - we are just pausing between counts
            }
        }
    }

}


