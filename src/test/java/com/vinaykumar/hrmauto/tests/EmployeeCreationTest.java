package com.vinaykumar.hrmauto.tests;

import com.vinaykumar.hrmauto.base.BaseTest;
import com.vinaykumar.hrmauto.config.ConfigReader;
import com.vinaykumar.hrmauto.pages.AddEmployeePage;
import com.vinaykumar.hrmauto.pages.LoginPage;
import com.vinaykumar.hrmauto.pages.PersonalDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class EmployeeCreationTest extends BaseTest {


    @Test
    public void employeeCreation() throws InterruptedException {
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

        // Now upload photo
        personalPage.clickProfileImage();
        personalPage.uploadPhoto("C:\\Users\\kumarv\\Downloads\\sundar_pichai_small.jpg");

        // Navigate back to Personal Details
        getDriver().navigate().back();

        // Assertions
        String expectedDate = year + "-" + day + "-" + monthMap.get(month);  // yyyy-dd-mm format
        String savedDate = personalPage.getDateOfBirthValue();
        Assert.assertEquals(savedDate, expectedDate);
        String actualUrl = personalPage.getCurrentUrl();
        System.out.println("Actual URL: " + actualUrl);
        Assert.assertTrue(personalPage.getCurrentUrl().contains("viewPersonalDetails"));
        Assert.assertEquals(personalPage.getEmployeeName(firstName + " " + lastName), firstName + " " + lastName);
    }
}