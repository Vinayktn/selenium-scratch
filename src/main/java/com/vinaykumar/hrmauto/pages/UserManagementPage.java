package com.vinaykumar.hrmauto.pages;

import com.vinaykumar.hrmauto.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserManagementPage extends BasePage {

    private By adminMenuItem = By.xpath("//span[text()='Admin']");

    private By addButton = By.xpath("//button[normalize-space()='Add']");

    private By usernameInput = By.xpath("//label[normalize-space()='Username']/ancestor::div[contains(@class, 'oxd-input-group')]//input");

    private By passwordInput = By.xpath("//label[normalize-space()='Password']/ancestor::div[contains(@class, 'oxd-input-group')]//input");

    private By confirmPasswordInput = By.xpath("//label[normalize-space()='Confirm Password']/ancestor::div[contains(@class, 'oxd-input-group')]//input");

    private By userRoleSelect = By.xpath("//label[contains(normalize-space(),'User Role')]/ancestor::div[contains(@class,'oxd-input-group')]//div[@class='oxd-select-text-input']");

    private By userRoleAdminOption = By.xpath("//div[@role='option']//span[text()='Admin']");

    private By statusSelect = By.xpath("//label[contains(normalize-space(),'Status')]/ancestor::div[contains(@class,'oxd-input-group')]//div[@class='oxd-select-text-input']");

    private By statusEnabledOption = By.xpath("//div[@role='option']//span[text()='Enabled']");

    private By employeeNameInput = By.xpath("//input[contains(@placeholder, 'Type for hints')]");

    private By employeeSuggestions = By.xpath("(//div[contains(@class,'oxd-autocomplete-option') and not(contains(., 'Searching'))])");

    private By firstEmployeeSuggestion = By.xpath("(//div[contains(@class,'oxd-autocomplete-option') and not(contains(., 'Searching'))])[1]");

    private By saveButton = By.xpath("//button[normalize-space()='Save']");

    private By successToast = By.xpath("//p[contains(., 'Successfully Saved')]");

    public UserManagementPage(WebDriver driver) {
        super(driver);
    }

    public void goToAdmin() {
        click(adminMenuItem);
    }

    public void clickAdd()
    {
        click(addButton);
    }

    public void enterUsername(String username)
    {
        sendText(usernameInput, username);
    }

    public void enterPassword(String password)
    {
        sendText(passwordInput, password);
    }

    public void enterConfirmPassword(String password)
    {
        sendText(confirmPasswordInput, password);
    }

    public void clickSave()
    {
        click(saveButton);
    }

    public String getSuccessMessage()
    {
        return getText(successToast);
    }

    public void selectUserRoleAdmin()
    {
        click(userRoleSelect);
        click(userRoleAdminOption);
    }

    public void selectStatusEnabled()
    {
        click(statusSelect);
        click(statusEnabledOption);
    }

    public void enterEmployeeName(String partial) {
        sendText(employeeNameInput, partial);
        waitForListToSettle(employeeSuggestions);
        click(firstEmployeeSuggestion);
    }
}
