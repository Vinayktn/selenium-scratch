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

    // Initializes the WebDriver and creates an explicit wait using
    // the timeout value configured in the properties file.
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(ConfigReader.get("timeout.explicit"))));
    }

    // Utility method to enter text into an input field.
    // Waits until the element is visible before interacting with it.
    protected void sendText(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    // Utility method to click an element.
    // Waits until the element is clickable before performing the click action.
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    // Utility method to retrieve the visible text of an element.
    // Waits until the element is visible before reading its text.
    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    // Returns the current URL of the browser.
    // Useful for validating navigation after an action such as login.
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // Waits for a dynamically loaded list to stabilize.
    // The method compares the number of elements across multiple checks
    // and returns when the count remains unchanged and is greater than zero.
    protected void waitForListToSettle(By locator) {

        int previousCount = -1;

        // Check the element count up to 10 times.
        for (int i = 0; i < 10; i++) {

            int currentCount = driver.findElements(locator).size();

            // If the element count remains unchanged and at least one
            // element is present, consider the list fully loaded.
            if (previousCount == currentCount && currentCount > 0) {
                return;
            } else {
                previousCount = currentCount;
            }

            try {
                // Pause for one second before checking the list again.
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                // Restore the interrupted status of the current thread
                // instead of silently ignoring the interruption.
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}