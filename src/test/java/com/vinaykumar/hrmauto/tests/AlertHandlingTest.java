package com.vinaykumar.hrmauto.tests;

import com.vinaykumar.hrmauto.base.BaseTest;
import com.vinaykumar.hrmauto.config.ConfigReader;
import com.vinaykumar.hrmauto.pages.AddEmployeePage;
import com.vinaykumar.hrmauto.pages.EmployeeListPage;
import com.vinaykumar.hrmauto.pages.LoginPage;
import com.vinaykumar.hrmauto.pages.PersonalDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class AlertHandlingTest extends BaseTest {

    @Test(priority = 2)
    public void testAcceptAlert() {
        // 1. Login
        getDriver().get(ConfigReader.get("base.url"));
        new LoginPage(getDriver()).login("Admin", "admin123");

        String year = "1990";
        String month = "May";
        String day = "15";
        String nationality = "Indian";
        String maritalStatus = "Married";
        String gender = "Male";

        // Month name to number mapping
        Map<String, String> monthMap = new HashMap<>();
        monthMap.put("January", "01");
        monthMap.put("February", "02");
        monthMap.put("March", "03");
        monthMap.put("April", "04");
        monthMap.put("May", "05");
        monthMap.put("June", "06");
        monthMap.put("July", "07");
        monthMap.put("August", "08");
        monthMap.put("September", "09");
        monthMap.put("October", "10");
        monthMap.put("November", "11");
        monthMap.put("December", "12");

        //Generate unique name
        String firstName = "Test";
        String lastName = "Employee" + System.currentTimeMillis();
        String employeeName = firstName +" "+ lastName;
        System.out.println(employeeName);

        AddEmployeePage addPage = new AddEmployeePage(getDriver());
        addPage.navigateToPIM();
        addPage.clickAddEmployee();
        addPage.enterFirstName(firstName);
        addPage.enterLastName(lastName);
        addPage.clickSaveAndWaitForNavigation();

        //Fill all personal details
        PersonalDetailsPage personalPage = new PersonalDetailsPage(getDriver());
        personalPage.waitForFormLoaderToDisappear();
        personalPage.clickDateOfBirthIcon();
        personalPage.selectYearFromCalendar(year);
        personalPage.selectMonthFromCalendar(month);
        personalPage.selectDayFromCalendar(day);
        personalPage.selectNationality(nationality);
        personalPage.selectMaritalStatus(maritalStatus);
        personalPage.selectGender(gender);
        personalPage.clickPersonalDetailsSave();

        // Navigate back to Personal Details
        getDriver().navigate().back();

        // 3. Search for employee
        EmployeeListPage searchEmployee = new EmployeeListPage(getDriver());
        searchEmployee.waitForSearchResultsToSettle();
        searchEmployee.searchByEmployeeName(employeeName);
        searchEmployee.waitForSearchResultsToSettle();

        // 4. Delete employee
        searchEmployee.deleteEmployee(employeeName);

        // 5. Accept alert
        searchEmployee.acceptAlert();

        // 6. Verify employee deleted
        searchEmployee.searchByEmployeeName(employeeName);
        searchEmployee.waitForSearchResultsToSettle();
        boolean found = searchEmployee.verifyEmployeeInResults(employeeName);
        Assert.assertFalse(found);
    }

    @Test(priority = 1)
    public void testDismissAlert() {
        // 1. Login
        getDriver().get(ConfigReader.get("base.url"));
        new LoginPage(getDriver()).login("Admin", "admin123");

        String year = "1990";
        String month = "May";
        String day = "15";
        String nationality = "Indian";
        String maritalStatus = "Married";
        String gender = "Male";

        // Month name to number mapping
        Map<String, String> monthMap = new HashMap<>();
        monthMap.put("January", "01");
        monthMap.put("February", "02");
        monthMap.put("March", "03");
        monthMap.put("April", "04");
        monthMap.put("May", "05");
        monthMap.put("June", "06");
        monthMap.put("July", "07");
        monthMap.put("August", "08");
        monthMap.put("September", "09");
        monthMap.put("October", "10");
        monthMap.put("November", "11");
        monthMap.put("December", "12");

        //Generate unique name
        String firstName = "Test";
        String lastName = "Employee" + System.currentTimeMillis();
        String employeeName = firstName +" "+ lastName;
        System.out.println(employeeName);

        AddEmployeePage addPage = new AddEmployeePage(getDriver());
        addPage.navigateToPIM();
        addPage.clickAddEmployee();
        addPage.enterFirstName(firstName);
        addPage.enterLastName(lastName);
        addPage.clickSaveAndWaitForNavigation();

        //Fill all personal details
        PersonalDetailsPage personalPage = new PersonalDetailsPage(getDriver());
        personalPage.waitForFormLoaderToDisappear();
        personalPage.clickDateOfBirthIcon();
        personalPage.selectYearFromCalendar(year);
        personalPage.selectMonthFromCalendar(month);
        personalPage.selectDayFromCalendar(day);
        personalPage.selectNationality(nationality);
        personalPage.selectMaritalStatus(maritalStatus);
        personalPage.selectGender(gender);
        personalPage.clickPersonalDetailsSave();

        // Navigate back to Personal Details
        getDriver().navigate().back();

        // 3. Search for employee
        EmployeeListPage searchEmployee = new EmployeeListPage(getDriver());
        searchEmployee.waitForSearchResultsToSettle();
        searchEmployee.searchByEmployeeName(employeeName);
        searchEmployee.waitForSearchResultsToSettle();

        // 4. Delete employee
        searchEmployee.deleteEmployee(employeeName);

        // 5. Accept alert
        searchEmployee.dismissAlert();

        // 6. Verify employee still exists
        searchEmployee.searchByEmployeeName(employeeName);
        searchEmployee.waitForSearchResultsToSettle();
        boolean found = searchEmployee.verifyEmployeeInResults(employeeName);
        Assert.assertTrue(found, "Employee Still Exists");
    }
}
