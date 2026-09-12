package com.vinaykumar.hrmauto.pages;

import com.vinaykumar.hrmauto.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private By username = By.name("username");
    private By password = By.name("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By errorMessage = By.xpath("//p[contains(., 'Invalid credentials')]");

    //  constructor — takes a WebDriver called 'driver' as its parameter, assigns it to the field above using 'this.driver = driver;'
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
