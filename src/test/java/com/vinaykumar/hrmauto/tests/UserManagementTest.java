package com.vinaykumar.hrmauto.tests;

import com.vinaykumar.hrmauto.base.BaseTest;
import com.vinaykumar.hrmauto.config.ConfigReader;
import com.vinaykumar.hrmauto.pages.LoginPage;
import com.vinaykumar.hrmauto.pages.UserManagementPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserManagementTest extends BaseTest {

    @Test
    public void createsUserSuccessfully() {
        getDriver().get(ConfigReader.get("base.url"));
        new LoginPage(getDriver()).login("Admin", "admin123");
        UserManagementPage manageUser = new UserManagementPage(getDriver());
        String username = "auto_" + System.currentTimeMillis();

        manageUser.goToAdmin();
        String url = manageUser.getCurrentUrl();
        Assert.assertTrue(url.contains("viewSystemUsers"), "Wrong URL");

        manageUser.clickAdd();
        manageUser.selectUserRoleAdmin();
        manageUser.enterEmployeeName("a");
        manageUser.enterUsername(username);
        manageUser.selectStatusEnabled();
        manageUser.enterPassword("Vinay@1234");
        manageUser.enterConfirmPassword("Vinay@1234");
        manageUser.clickSave();
        Assert.assertTrue(manageUser.getSuccessMessage().contains("Successfully Saved"), "Success toast not shown");
    }

    @Test
    public void createdUserAppearsInSearch()
    {
        getDriver().get(ConfigReader.get("base.url"));
        new LoginPage(getDriver()).login("Admin", "admin123");
        UserManagementPage manageUser = new UserManagementPage(getDriver());
        String username = "auto_" + System.currentTimeMillis();

        manageUser.goToAdmin();
        String url = manageUser.getCurrentUrl();
        Assert.assertTrue(url.contains("viewSystemUsers"), "Wrong URL");

        manageUser.clickAdd();
        manageUser.selectUserRoleAdmin();
        manageUser.enterEmployeeName("a");
        manageUser.enterUsername(username);
        manageUser.selectStatusEnabled();
        manageUser.enterPassword("Vinay@1234");
        manageUser.enterConfirmPassword("Vinay@1234");
        manageUser.clickSave();
        Assert.assertTrue(manageUser.getSuccessMessage().contains("Successfully Saved"), "Success toast not shown");

        manageUser.searchByUsername(username);
        Assert.assertTrue(manageUser.isUserInTable(username), "User not found in table");
    }

}
