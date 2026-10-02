package com.vinaykumar.hrmauto.tests;

import com.vinaykumar.hrmauto.base.BaseTest;
import com.vinaykumar.hrmauto.config.ConfigReader;
import com.vinaykumar.hrmauto.pages.AddEmployeePage;
import com.vinaykumar.hrmauto.pages.EmployeeListPage;
import com.vinaykumar.hrmauto.pages.LoginPage;
import com.vinaykumar.hrmauto.pages.PersonalDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class TablePaginationTest extends BaseTest {

    @Test
    public void tablePaginationTest() throws InterruptedException {
        // 1. Login
        getDriver().get(ConfigReader.get("base.url"));
        new LoginPage(getDriver()).login("Admin", "admin123");

        // 2. Navigate to Employee List
        AddEmployeePage addPage = new AddEmployeePage(getDriver());
        addPage.navigateToPIM();
        EmployeeListPage searchEmployee = new EmployeeListPage(getDriver());
        searchEmployee.waitForSearchResultsToSettle();

        // 3. Get page 1 employee names
        List<String> firstPage = searchEmployee.getEmployeeNamesOnPage();
        System.out.println("Page 1: " + firstPage);

        // 4. Click Next
        searchEmployee.clickNextPage();
        searchEmployee.waitForSearchResultsToSettle();

        // 5. Get page 2 employee names
        List<String> secondPage = searchEmployee.getEmployeeNamesOnPage();
        System.out.println("Page 2: " + secondPage);

        // 6. Assert page 1 != page 2
        Assert.assertNotEquals(firstPage, secondPage);

        // 7. Click Previous
        searchEmployee.clickPreviousPage();
        searchEmployee.waitForSearchResultsToSettle();

        // 8. Assert current page is 1
        String currentPageNumber = searchEmployee.getCurrentPageNumber();
        Assert.assertEquals(currentPageNumber, "1");
    }
}