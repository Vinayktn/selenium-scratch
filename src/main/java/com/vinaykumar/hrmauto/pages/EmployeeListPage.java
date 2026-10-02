package com.vinaykumar.hrmauto.pages;

import com.vinaykumar.hrmauto.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class EmployeeListPage extends BasePage {

    private By searchButton = By.xpath("//button[@type='submit']");
    private By employeeNameInput = By.xpath("//label[text()='Employee Name']/parent::div/following-sibling::div//input");
    private By tableRow = By.xpath("//div[@class='oxd-table-card']");

    //Pagination xpaths
    private By nextPageButton = By.xpath("//i[@class='oxd-icon bi-chevron-right']/parent::button");
    private By previousPageButton = By.xpath("//ul[@class='oxd-pagination__ul']//i[contains(@class, 'bi-chevron-left')]/parent::button");
    private By currentPageNumber = By.xpath("//button[contains(@class, 'page-selected')]");
    private By employeeList = By.xpath("//a[normalize-space()='Employee List']");

    //Alert Handling
    private By confirmDeleteYesButton = By.xpath("//button[normalize-space()='Yes, Delete']");
    private By confirmDeleteNoButton = By.xpath("//button[normalize-space()='No, Cancel']");

    //constructor injection
    public EmployeeListPage(WebDriver driver) {
        super(driver);
    }

    public void searchByEmployeeName(String employeeName) {
        driver.findElement(employeeList).click();
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

    //Handling pagination
    public void clickNextPage() {
        click(nextPageButton);
    }

    public void clickPreviousPage() {
        click(previousPageButton);
    }

    public void clickPageNumber(int pageNum) {
        By pageNumLocator = By.xpath("//ul[@class='oxd-pagination__ul']/li/button[normalize-space()='" + pageNum + "']");
        click(pageNumLocator);
    }

    public String getCurrentPageNumber() {
        return driver.findElement(currentPageNumber).getText();
    }

    public List<String> getEmployeeNamesOnPage() {
        List<WebElement> rows = driver.findElements(tableRow);
        List<String> fullNames = new ArrayList<>();
        for (WebElement row : rows) {
            {
                String firstName = row.findElement(By.xpath(".//div[contains(@class, 'oxd-table-cell')][3]//div")).getText();
                String lastName = row.findElement(By.xpath(".//div[contains(@class, 'oxd-table-cell')][4]//div")).getText();
                fullNames.add(firstName + " " + lastName);
            }
        }
        return fullNames;
    }

    public void acceptAlert() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteYesButton));
        driver.findElement(confirmDeleteYesButton).click();
    }

    public void dismissAlert() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteNoButton));
        driver.findElement(confirmDeleteNoButton).click();
    }

    public String getAlertText() {
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }

    public void deleteEmployee(String fullName) {
        String[] names = fullName.split(" ");
        String firstName = names[0];
        String lastName = names[names.length - 1];
        By deleteButton = By.xpath("//div[@class='oxd-table-card'][contains(., '" + firstName + "') and contains(., '" + lastName + "')]//i[contains(@class, 'bi-trash')]/parent::button");
        System.out.println(deleteButton);
        WebElement element = driver.findElement(deleteButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}