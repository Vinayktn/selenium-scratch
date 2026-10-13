package com.vinaykumar.hrmauto.pages;

import com.vinaykumar.hrmauto.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddUserPage extends BasePage {
    private By employeeNameInput = By.xpath("//label[normalize-space()='Employee Name']/parent::div/following-sibling::div//input");
    private By usernameInput = By.xpath("//label[normalize-space()='Username']/parent::div/following-sibling::div//input");
    private By passwordInput = By.xpath("//label[normalize-space()='Password']/parent::div/following-sibling::div//input");
    private By confirmPasswordInput = By.xpath("//label[normalize-space()='Confirm Password']/parent::div/following-sibling::div//input");
    private By saveButton = By.xpath("//button[normalize-space()='Save']");
    private By userRoleDropdown = By.xpath("//label[normalize-space()='User Role']/parent::div/following-sibling::div//div[@class='oxd-select-text-input']");
    private By statusDropdown = By.xpath("//label[normalize-space()='Status']/parent::div/following-sibling::div//div[@class='oxd-select-text-input']");
    private By employeeSuggestions = By.xpath("(//div[contains(@class,'oxd-autocomplete-option') and not(contains(., 'Searching'))])");
    private By successMessage = By.xpath("//div[contains(@class, 'toast')]//p[contains(text(), 'Success')]");
    private By formLoader = By.xpath("//div[@class='oxd-form-loader']");


    public AddUserPage(WebDriver driver) {
        super(driver);
    }

    public void enterUserName(String userName) {
        sendText(usernameInput, userName);
    }

    public void enterPassword(String password) {
        sendText(passwordInput, password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        sendText(confirmPasswordInput, confirmPassword);
    }

    public void selectEmployeeName(String employeeName) {
        sendText(employeeNameInput, employeeName);
        waitForListToSettle(employeeSuggestions);
        click(By.xpath("//div[contains(@class,'oxd-autocomplete-option') and not(contains(., 'Searching'))]/span[normalize-space()='" + employeeName + "']"));
    }

    public void selectUserRole(String role) {
        click(userRoleDropdown);
        By userRole = By.xpath("//div[@role='listbox']//div[@role='option'][normalize-space()='" + role + "']");
        click(userRole);
    }

    public void selectUserStatus(String status) {
        click(statusDropdown);
        By statusOption = By.xpath("//div[@role='listbox']//div[@role='option'][normalize-space()='" + status + "']");
        click(statusOption);
    }

    public void clickSaveButton() {
        click(saveButton);
    }

    public boolean verifySuccessMessage(){
        try {
            // Wait for the toast container to have child elements
            By toastContainer = By.xpath("//div[contains(@class, 'oxd-toast-container')]");
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'oxd-toast-container')]//*")));

            // Then check if success message is there
            String toastText = driver.findElement(toastContainer).getText();
            if (toastText.contains("Success")) {
                return true;
            }
            return false;
        }
        catch(Exception e)
        {
            System.out.println("Success message not found: " + e.getMessage());
            return false;
        }


    }
}
