package com.vinaykumar.hrmauto.tests;

import com.vinaykumar.hrmauto.base.BaseTest;
import com.vinaykumar.hrmauto.config.ConfigReader;
import com.vinaykumar.hrmauto.pages.DashboardPage;
import com.vinaykumar.hrmauto.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstTest extends BaseTest {

    @Test
    public void loginWorks() {
        getDriver().get(ConfigReader.get("base.url"));
        new LoginPage(getDriver()).login("Admin", "admin123");
        DashboardPage dashboard = new DashboardPage(getDriver());
        Assert.assertEquals(dashboard.getHeaderText(), "Dashboard");
     }

    @Test
    public void invalidPasswordShowsError() {
        getDriver().get(ConfigReader.get("base.url"));

        LoginPage login = new LoginPage(getDriver());
        login.login("Admin", "wrongpassword");

        Assert.assertEquals(login.getErrorText(), "Invalid credentials");
    }
}