package com.vinaykumar.hrmauto.pages;

import com.vinaykumar.hrmauto.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EmployeeListPage extends BasePage {

    private By searchButton = By.xpath("//button[@type='submit']");
    private By employeeNameInput = By.xpath("//label[text()='Employee Name']/parent::div/following-sibling::div//input");
    private By tableRow = By.xpath("//div[@class='oxd-table-card']");

    //constructor injection
    public EmployeeListPage(WebDriver driver) {
        super(driver);
    }

    public void searchByEmployeeName(String employeeName) {
        driver.findElement(By.xpath("//a[normalize-space()='Employee List']")).click();
        sendText(employeeNameInput, employeeName);
        click(searchButton);
    }

    public boolean verifyEmployeeInResults(String employeeName) {
        try {
            By employeeLocator = By.xpath("//div[@class='oxd-table-card' and contains(., '" + employeeName + "')]");
            return driver.findElement(employeeLocator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForSearchResultsToSettle() {
        waitForListToSettle(tableRow);
    }
}