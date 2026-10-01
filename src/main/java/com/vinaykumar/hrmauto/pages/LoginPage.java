package com.vinaykumar.hrmauto.pages;

import com.vinaykumar.hrmauto.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private By username = By.name("username");
    private By password = By.name("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By errorMessage = By.xpath("//p[contains(., 'Invalid credentials')]");

    // Call the parent class (BasePage) constructor with this driver.
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String user, String pass) {
        sendText(username, user);
        sendText(password, pass);
        click(loginButton);
    }

    public String getErrorText() {
        return getText(errorMessage);
    }
}
