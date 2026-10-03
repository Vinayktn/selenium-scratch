package com.vinaykumar.hrmauto.tests;

import com.aventstack.extentreports.Status;
import com.vinaykumar.hrmauto.base.BaseTest;
import com.vinaykumar.hrmauto.config.ConfigReader;
import com.vinaykumar.hrmauto.listeners.TestListener;
import com.vinaykumar.hrmauto.pages.LoginPage;
import com.vinaykumar.hrmauto.pages.UserManagementPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserManagementTest extends BaseTest {
    private static final String TEST_PASSWORD = "Vinay@1234";
    private static final String EMPLOYEE_NAME_PARTIAL = "a";

    @Test
    public void createsUserSuccessfully() {
        getDriver().get(ConfigReader.get("base.url"));
        new LoginPage(getDriver()).login("Admin", "admin123");
        UserManagementPage manageUser = new UserManagementPage(getDriver());
        String username = "auto_" + System.currentTimeMillis();

        manageUser.goToAdmin();
        String url = manageUser.getCurrentUrl();
        Assert.assertTrue(url.contains("viewSystemUsers"), "Wrong URL");
        TestListener.getTest().log(Status.PASS, "navigated to Admin → User Management");

        manageUser.clickAdd();
        manageUser.selectUserRoleAdmin();
        TestListener.getTest().log(Status.PASS, "select user role as Admin");

        manageUser.enterEmployeeName(EMPLOYEE_NAME_PARTIAL);
        TestListener.getTest().log(Status.PASS, "filled the partial employee name: " + EMPLOYEE_NAME_PARTIAL);

        manageUser.enterUsername(username);
        TestListener.getTest().log(Status.PASS, "filled Add User form with username: " + username);

        manageUser.selectStatusEnabled();
        TestListener.getTest().log(Status.PASS, "made the status as enabled");

        manageUser.enterPassword(TEST_PASSWORD);
        TestListener.getTest().log(Status.PASS, "filled Add User form with password: " + TEST_PASSWORD);

        manageUser.enterConfirmPassword(TEST_PASSWORD);
        TestListener.getTest().log(Status.PASS, "filled Add User form with confirm password: " + TEST_PASSWORD);

        manageUser.clickSave();
        Assert.assertTrue(manageUser.getSuccessMessage().contains("Successfully Saved"), "Success toast not shown");
        TestListener.getTest().log(Status.PASS, "saved user — success toast appeared");
    }

    @Test
    public void createdUserAppearsInSearch()
    {
        getDriver().get(ConfigReader.get("base.url"));
        new LoginPage(getDriver()).login("Admin", "admin123");
        TestListener.getTest().log(Status.PASS, "logged in as Admin");

        UserManagementPage manageUser = new UserManagementPage(getDriver());
        String username = "auto_" + System.currentTimeMillis();

        manageUser.goToAdmin();
        String url = manageUser.getCurrentUrl();
        Assert.assertTrue(url.contains("viewSystemUsers"), "Wrong URL");
        TestListener.getTest().log(Status.PASS, "navigated to Admin → User Management");

        manageUser.clickAdd();
        manageUser.selectUserRoleAdmin();
        TestListener.getTest().log(Status.PASS, "select user role as Admin");

        manageUser.enterEmployeeName(EMPLOYEE_NAME_PARTIAL);
        TestListener.getTest().log(Status.PASS, "filled the partial employee name: " + EMPLOYEE_NAME_PARTIAL);

        manageUser.enterUsername(username);
        TestListener.getTest().log(Status.PASS, "filled Add User form with username: " + username);

        manageUser.selectStatusEnabled();
        TestListener.getTest().log(Status.PASS, "made the status as enabled");

        manageUser.enterPassword(TEST_PASSWORD);
        TestListener.getTest().log(Status.PASS, "filled Add User form with password");

        manageUser.enterConfirmPassword(TEST_PASSWORD);
        TestListener.getTest().log(Status.PASS, "filled confirm password");

        manageUser.clickSave();
        Assert.assertTrue(manageUser.getSuccessMessage().contains("Successfully Saved"), "Success toast not shown");
        TestListener.getTest().log(Status.PASS, "saved user — success toast appeared");

        manageUser.searchByUsername(username);
        TestListener.getTest().log(Status.PASS, "searched for user: " + username);

        Assert.assertTrue(manageUser.isUserInTable(username), "User not found in table");
        TestListener.getTest().log(Status.PASS, "user found in table");
    }
}
