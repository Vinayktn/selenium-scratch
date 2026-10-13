package com.vinaykumar.hrmauto.pages;

import com.vinaykumar.hrmauto.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserListPage extends BasePage {

    private By userNameInput = By.xpath("//label[normalize-space()='Username']/parent::div/following-sibling::div//input");
    private By userSearchButton = By.xpath("//button[normalize-space()='Search']");
    private By addUserButton = By.xpath("//i[contains(@class, 'plus oxd-button')]");

    public UserListPage(WebDriver driver) {
        super(driver);
    }

    public void searchByUsername(String userName) {
        sendText(userNameInput, userName);
        click(userSearchButton);
        // Wait for table results to load
        waitForListToSettle(By.xpath("//div[@class='oxd-table-card']"));
    }

    public boolean verifyUserInResults(String userName) {
        By userRow = By.xpath("//div[@class='oxd-table-card']//div[contains(@class, 'oxd-table-cell')]/div[contains(text(), '" + userName + "')]");
        try {
            return driver.findElement(userRow).isDisplayed();
        } catch (Exception e) {
            System.out.println("user not found " + e.getMessage());
        }
        return false;
    }

    public void deleteUser(String userName) {
        By deleteParticularUser = By.xpath("//div[@class='oxd-table-card']//div[contains(@class, 'oxd-table-cell')]/div[contains(text(), '"+userName+"')]/ancestor::div//div/button//i[contains(@class, 'trash')]");
        click(deleteParticularUser);

        // Wait for confirmation dialog and click "Yes, Delete" button
        By confirmButton = By.xpath("//button[contains(text(), 'Yes, Delete')]");
        waitForListToSettle(confirmButton);
        click(confirmButton);
    }

    public void clickAddUserButton() {
        click(addUserButton);
    }
}