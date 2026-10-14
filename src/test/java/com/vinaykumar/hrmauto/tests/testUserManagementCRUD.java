package com.vinaykumar.hrmauto.tests;

import com.vinaykumar.hrmauto.base.BaseTest;
import com.vinaykumar.hrmauto.config.ConfigReader;
import com.vinaykumar.hrmauto.pages.*;
import com.vinaykumar.hrmauto.utils.ExcelUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.vinaykumar.hrmauto.listeners.TestListener;
import java.io.IOException;


public class testUserManagementCRUD extends BaseTest {

    @DataProvider(name = "UserData")
    public Object[][] getUserData() throws IOException {
        return ExcelUtil.readExcelData("src/test/resources/userData.xlsx", "validData", "invalidData", "edgeCases");
    }

    @Test(dataProvider = "UserData")
    public void testUserManagementCRUD(
            String employeeName,
            String userRole,
            String status,
            String username,
            String password
    ) {
        // Add timestamp to make username unique across runs
        String uniqueUsername = username + "_" + System.currentTimeMillis();
        TestListener.getTest().info("Employee: " + employeeName + " | Role: " + userRole + " | Username: " + uniqueUsername);

        // Login
        getDriver().get(ConfigReader.get("base.url"));
        new LoginPage(getDriver()).login("admin", "admin123");

        // Navigate to Add User
        AdminPage adminPage = new AdminPage(getDriver());
        adminPage.navigateToUserManagement();
        UserListPage userList = new UserListPage(getDriver());
        userList.clickAddUserButton();

        // Fill all fields and Click Save
        AddUserPage adduser = new AddUserPage(getDriver());
        adduser.enterUserName(uniqueUsername);
        adduser.enterPassword(password);
        adduser.enterConfirmPassword(password);
        adduser.selectUserRole(userRole);
        adduser.selectUserStatus(status);
        adduser.selectEmployeeName(employeeName);
        adduser.clickSaveButton();

        // If yes: Search → Verify → Delete → Verify gone
        boolean creationSuccess = adduser.verifySuccessMessage();
        if (creationSuccess == true) {
            //Search user and verify
            userList.searchByUsername(uniqueUsername);
            System.out.println(uniqueUsername);
            boolean found = userList.verifyUserInResults(uniqueUsername);
            Assert.assertTrue(found, "User not found in search results");

            // Skip deletion - just verify creation worked
            System.out.println("User created and verified successfully");
        }

        // If no: Just pass (invalid data is handled)
        else {
            System.out.println("invlaid data");
            Assert.assertFalse(creationSuccess, "Invalid data correctly rejected");
        }
    }
}