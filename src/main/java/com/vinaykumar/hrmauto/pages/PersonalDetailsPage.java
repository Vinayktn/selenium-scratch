package com.vinaykumar.hrmauto.pages;

import com.vinaykumar.hrmauto.base.BasePage;
import com.vinaykumar.hrmauto.config.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PersonalDetailsPage extends BasePage {

    private By dateOfBirthInput = By.xpath("//label[normalize-space()='Date of Birth']/../..//div//input[@placeholder='yyyy-dd-mm']");

    private By dateOfBirthIcon = By.xpath("//label[normalize-space()='Date of Birth']/../..//div//input[@placeholder='yyyy-dd-mm']");

    private By calendarYearDropdown = By.xpath("//li[@class='oxd-calendar-selector-year']");

    private By calendarMonthDropdown = By.xpath("//li[@class='oxd-calendar-selector-month']");

    private By personalDetailsSaveButton = By.xpath("//p[normalize-space()='* Required']/parent::div//button[normalize-space()='Save']");

    private By formLoader = By.xpath("//div[@class='oxd-form-loader']");

    private By nationalityDropdown = By.xpath("//label[normalize-space()='Nationality']/parent::div/parent::div//div[@clear='false' and contains(., 'Select')]");

    private By maritalStatusDropdown = By.xpath("//label[normalize-space()='Marital Status']/parent::div/parent::div//div[@clear='false' and contains(., 'Select')]");

    private By profileImage = By.xpath("//div[@class='orangehrm-edit-employee-image']//img[@alt='profile picture']");

    private By fileInput = By.xpath("//input[@type='file']");

    private By profilePicSavebtn = By.xpath("//button[normalize-space()='Save']");

    private By personalDetailsOption = By.xpath("//a[normalize-space()='Personal Details']");

    //constructor injection
    public PersonalDetailsPage(WebDriver driver) {
        super(driver);
    }

    private By getCalendarDayButton(String day) {
        return By.xpath("//div[@class='oxd-calendar-date' and text()='" + day + "']");
    }

    public void clickDateOfBirthIcon() {
        waitForFormLoaderToDisappear();
        click(dateOfBirthIcon);
    }

    public void selectYearFromCalendar(String year) {
        // click year dropdown, then select the year
        click(calendarYearDropdown);
        By yearOption = By.xpath("//ul[@class='oxd-calendar-dropdown']//li[text()='" + year + "']");
        click(yearOption);  // Click the year
    }

    public void selectMonthFromCalendar(String month) {
        // click month dropdown, then select the month
        click(calendarMonthDropdown);
        By yearOption = By.xpath("//ul[@role='menu']//li[text()='" + month + "']");
        click(yearOption);  // Click the year
    }

    public void selectDayFromCalendar(String day) {
        click(getCalendarDayButton(day));
    }

    public void clickPersonalDetailsSave() {
        click(personalDetailsSaveButton);
    }

    public String getDateOfBirthValue() {
        return driver.findElement(dateOfBirthInput).getAttribute("value");
    }

    public String getEmployeeName(String empName) {
        By employeeNameHeading = By.xpath("//h6[normalize-space()='" + empName + "']");
        return getText(employeeNameHeading);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

/*
    public void waitForFormLoaderToDisappear() {
        long timeout = Long.parseLong(ConfigReader.get("timeout.explicit"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(formLoader));
    }
*/

    public void waitForFormLoaderToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(formLoader));
        } catch (TimeoutException e) {
            System.out.println("Form loader didn't disappear after file upload, continuing...");
        }
    }


    public void selectNationality(String nationality) {
        // Click dropdown, then select option
        click(nationalityDropdown);
        By nationalityOption = By.xpath("//div[@role='listbox']//span[text()='" + nationality + "']");
        click(nationalityOption);
    }

    public void selectMaritalStatus(String status) {
        // Click dropdown, then select option
        click(maritalStatusDropdown);
        By maritalStatusOption = By.xpath("//div[@role='listbox']//span[text()='" + status + "']");
        click(maritalStatusOption);
    }

    private By getGenderRadio(String gender) //Helper method
    {
        return By.xpath("//label[normalize-space()='"+ gender +"']");
    }

    // Now reuse it
    public void selectGender(String gender)
    {
        click(getGenderRadio(gender));   // Uses the helper
    }

    public  boolean isGenderSelected(String gender)
    {
        return driver.findElement(getGenderRadio(gender)).isSelected(); // Uses the helper
    }

    public void clickProfileImage() {
        WebElement image = driver.findElement(profileImage);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", image);
        click(profileImage);
    }

    public void uploadPhoto(String filePath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(fileInput));
        System.out.println("Uploading file: " + filePath);
        driver.findElement(fileInput).sendKeys(filePath);
    }

    public void saveprofilePic()
    {
        click(profilePicSavebtn);
    }

    public void clickPersonalDetailsOption()
    {
        click(personalDetailsOption);
    }
}