package com.vinaykumar.hrmauto.pages;

import com.vinaykumar.hrmauto.base.BasePage;
import com.vinaykumar.hrmauto.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddEmployeePage extends BasePage {

    private By pimMenu  = By.xpath("//span[normalize-space()='PIM']");
    private By addEmployeeTab = By.xpath("//button[normalize-space()='Add']");
    private By firstNameInput = By.xpath("//input[@placeholder='First Name']");
    private By lastNameInput = By.xpath("//input[@placeholder='Last Name']");
    private By saveButton = By.xpath("//button[normalize-space()='Save']");
    private By formLoader = By.xpath("//div[@class='oxd-form-loader']");

    public AddEmployeePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToPIM()
    {
        click(pimMenu);
    }

    public void clickAddEmployee()
    {
        click(addEmployeeTab);
    }

    public void enterFirstName(String name)
    {
        sendText(firstNameInput, name);
    }

    public void enterLastName(String name)
    {
        sendText(lastNameInput, name);
    }

    public void clickSaveAndWaitForNavigation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(ConfigReader.get("timeout.explicit"))));

        // STEP 1: Wait for loader to disappear FIRST
        wait.until(ExpectedConditions.invisibilityOfElementLocated(formLoader));

        // STEP 2: Use JavaScript click (bypasses any overlay)
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(saveButton));

        // STEP 3: Wait for navigation
        wait.until(ExpectedConditions.urlContains("viewPersonalDetails"));
    }
}
