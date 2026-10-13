package com.vinaykumar.hrmauto.pages;

import com.vinaykumar.hrmauto.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdminPage extends BasePage {

    private By userManagementTab = By.xpath("//span[normalize-space()='User Management']");
    private By adminTab = By.xpath("//span[normalize-space()='Admin']");


    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToUserManagement() {
        click(adminTab);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(userManagementTab));
        click(userManagementTab);
    }
}
