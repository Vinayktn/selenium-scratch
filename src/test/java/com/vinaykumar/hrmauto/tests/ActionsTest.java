package com.vinaykumar.hrmauto.tests;

import com.vinaykumar.hrmauto.base.BaseTest;
import com.vinaykumar.hrmauto.config.ConfigReader;
import com.vinaykumar.hrmauto.pages.AddEmployeePage;
import com.vinaykumar.hrmauto.pages.EmployeeListPage;
import com.vinaykumar.hrmauto.pages.LoginPage;
import com.vinaykumar.hrmauto.pages.PersonalDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ActionsTest extends BaseTest {

    @Test
    public void testHoverAndClickEditButton() {
        // Login
        getDriver().get(ConfigReader.get("base.url"));
        new LoginPage(getDriver()).login("Admin", "admin123");

        // Navigate to Employee List
        AddEmployeePage addPage = new AddEmployeePage(getDriver());
        addPage.navigateToPIM();

        PersonalDetailsPage personalPage = new PersonalDetailsPage(getDriver());
        personalPage.waitForFormLoaderToDisappear();

        // Call hoverAndClickEditButton method
        new EmployeeListPage(getDriver()).hoverAndClickEditButton("Jane", "Citizen");

        // Assert URL contains /viewPersonalDetails/
        Assert.assertTrue(getDriver().getCurrentUrl().contains("viewPersonalDetails"));    }
}