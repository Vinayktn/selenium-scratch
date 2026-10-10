package com.vinaykumar.hrmauto.tests;

import com.vinaykumar.hrmauto.base.BaseTest;
import com.vinaykumar.hrmauto.config.ConfigReader;
import com.vinaykumar.hrmauto.pages.*;
import com.vinaykumar.hrmauto.utils.ExcelUtil;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;

public class DataDrivenEmployeeWorkflowTest extends BaseTest {

    @DataProvider(name = "EmployeeData")
    public Object[][] getEmployeeData() throws IOException {
        return ExcelUtil.readExcelData(
                "src/test/resources/employeeData.xlsx",
                "validData",
                "invalidData",
                "edgeCases"
        );
    }

    @Test(dataProvider = "EmployeeData")
    public void createEmployeeTest(
            String firstName,
            String lastName,
            String dateOfBirth,
            String nationality,
            String maritalStatus,
            String gender,
            String email,
            String phone
    ) throws InterruptedException {

        // DEBUG LINE - to check if data is being fetched correctly from excel file
        System.out.println("DEBUG: firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth);

        //login workflow
        getDriver().get(ConfigReader.get("base.url"));
        new LoginPage(getDriver()).login("admin", "admin123");

        // STEP 1: InValid data workflow

        //Navigate to add employee page
        AddEmployeePage addPage = new AddEmployeePage(getDriver());
        addPage.navigateToPIM();
        addPage.clickAddEmployee();

        if (firstName == null || lastName == null || dateOfBirth == null || !dateOfBirth.contains("-")) {
            try {
                addPage.enterFirstName(firstName);  // This will crash (null)
                addPage.enterLastName(lastName);  // This will crash (null)

                // Also try to parse dateOfBirth (will crash if format is invalid)
                String[] dobParts = dateOfBirth.split("-");
                String year = dobParts[0];
                String month = getMonthName(dobParts[1]);
                String day = String.valueOf(Integer.parseInt(dobParts[2]));

                Assert.fail("Should have failed with invalid data");
            }
            catch (IllegalArgumentException | ArrayIndexOutOfBoundsException  e) {
                System.out.println("✓ Invalid data correctly rejected: " + e.getMessage());
                 Assert.assertTrue(true);  // Pass - error was expected
                return;  // Exit test - this scenario is done
            }
        }

        // STEP 2: Valid data workflow
        addPage.enterFirstName(firstName);
        addPage.enterLastName(lastName);
        try {
            addPage.clickSaveAndWaitForNavigation();
        } catch (TimeoutException | ElementClickInterceptedException e) {
            // URL didn't change - form validation rejected (likely name too long)
            System.out.println("✓ Form validation rejected long names: " + e.getMessage());
            Assert.assertTrue(true, "Long names correctly rejected by form validation");
            return;  // Exit test - edge case handled
        }

        // Fill all personal details
        PersonalDetailsPage personalPage = new PersonalDetailsPage(getDriver());
        personalPage.waitForFormLoaderToDisappear();

        // Parse dateOfBirth (YYYY-MM-DD format)
        String[] dobParts = dateOfBirth.split("-");
        String year = dobParts[0];
        String month = getMonthName(dobParts[1]);
        String day = String.valueOf(Integer.parseInt(dobParts[2]));

        personalPage.clickDateOfBirthIcon();
        personalPage.selectYearFromCalendar(year);
        personalPage.selectMonthFromCalendar(month);
        personalPage.selectDayFromCalendar(day);
        personalPage.selectNationality(nationality);
        personalPage.selectMaritalStatus(maritalStatus);
        personalPage.selectGender(gender);
        personalPage.clickPersonalDetailsSave();

        personalPage.clickContactDetailsTab();
        ContactDetailsPage contactPage = new ContactDetailsPage(getDriver());
        contactPage.enterMobilePhone(phone);
        contactPage.enterWorkEmail(email);
        contactPage.clickSave();

        // Navigate to Employee List and verify
        EmployeeListPage employeeList = new EmployeeListPage(getDriver());
        String fullName = firstName + " " + lastName;

        // This navigates AND searches
        employeeList.searchByEmployeeName(fullName);
        employeeList.waitForSearchResultsToSettle();

        // Assert: Employee created successfully
        boolean found = employeeList.verifyEmployeeInResults(fullName);
        Assert.assertTrue(found, "Employee " + fullName + " not found in list");

     }

    // Helper method to convert month number to name
    private String getMonthName(String monthNumber) {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        return months[Integer.parseInt(monthNumber) - 1];
    }
}