package com.vinaykumar.hrmauto.tests;

import com.vinaykumar.hrmauto.base.BaseTest;
import com.vinaykumar.hrmauto.config.ConfigReader;
import com.vinaykumar.hrmauto.listeners.TestListener;
import com.vinaykumar.hrmauto.pages.DashboardPage;
import com.vinaykumar.hrmauto.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void loginWorks() {
        getDriver().get(ConfigReader.get("base.url"));
        new LoginPage(getDriver()).login("Admin", "admin123");
        DashboardPage dashboard = new DashboardPage(getDriver());
        Assert.assertEquals(dashboard.getHeaderText(), "Dashboard");

        // Log successful login to Extent Report for test execution visibility
        TestListener.getTest().info("✓ Login successful - Dashboard loaded");
     }

    @Test
    public void invalidPasswordShowsError() {
        getDriver().get(ConfigReader.get("base.url"));

        LoginPage login = new LoginPage(getDriver());
        login.login("Admin", "wrongpassword");
        Assert.assertEquals(login.getErrorText(), "Invalid credentials");

        // Log error validation to Extent Report to confirm error handling works
        TestListener.getTest().info("✓ Invalid login - Error message displayed");
    }
}