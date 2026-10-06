package com.vinaykumar.hrmauto.utils;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * JsonUtil — Reads JSON test data files and converts them to Object[][] format for TestNG @DataProvider
 *
 * PURPOSE: Separate test data (JSON files) from test logic (test methods)
 * This allows non-technical people to add/modify test scenarios without touching code
 *
 * EXAMPLE:
 *   Input: loginData.json with 3 test scenarios
 *   Output: Object[][] with 3 rows (each row = one test iteration)
 *   TestNG: Calls @Test method 3 times (once per row)
 */
public class JsonUtil {

    /**
     * Reads a JSON file and converts it to Object[][] for data-driven testing
     *
     * @param filePath Path to JSON file (e.g., "src/test/resources/loginData.json")
     * @return Object[][] where each row = one test scenario, each column = one parameter
     * @throws IOException If file not found or cannot be read
     *
     * FLOW:
     *   JSON file → Read as bytes → Decode to String → Parse with GSON → Convert to Object[][]
     *
     * EXAMPLE:
     *   JSON: [{"username":"Admin", "password":"admin123", "expectedResult":true}]
     *   Result: Object[1][3] = [["Admin", "admin123", true]]
     */
    public static Object[][] readJsonData(String filePath) throws IOException {

        // ============================================================================
        // STEP 1: Read JSON file as bytes and convert to String (UTF-8 decoding)
        // ============================================================================
        // Why bytes? Files on disk are stored as raw bytes (binary data)
        // Why String? GSON needs human-readable text to parse

        Path path = Paths.get(filePath);
        // filePath example: "src/test/resources/loginData.json"
        // Paths.get() converts String path → Path object

        byte[] fileBytes = Files.readAllBytes(path);
        // Reads entire file as byte array
        // Example: [65, 100, 109, 105, 110] (ASCII values for "Admin")

        String jsonString = new String(fileBytes);
        // Decodes bytes → String using UTF-8 encoding
        // Example result: "[{\"username\":\"Admin\",\"password\":\"admin123\",\"expectedResult\":true}]"

        // ============================================================================
        // STEP 2: Create GSON object (GSON's main tool for JSON parsing)
        // ============================================================================
        // GSON = Google's JSON parsing library
        // fromJson() method converts JSON text → Java objects

        Gson gson = new Gson();

        // ============================================================================
        // STEP 3: Parse JSON string → LoginData[] array using GSON deserialization
        // ============================================================================
        // gson.fromJson(jsonText, targetClass)
        // - jsonText: JSON string from file
        // - targetClass: LoginData[].class tells GSON to parse as array of LoginData objects
        //
        // How GSON works:
        //   1. Reads field names in JSON (username, password, expectedResult)
        //   2. Matches them with LoginData class fields (must match exactly)
        //   3. Creates LoginData objects with extracted values
        //   4. Returns LoginData[] array
        //
        // Result example:
        //   loginDataArray[0].username = "Admin"
        //   loginDataArray[0].password = "admin123"
        //   loginDataArray[0].expectedResult = true

        LoginData[] loginDataArray = gson.fromJson(jsonString, LoginData[].class);

        // ============================================================================
        // STEP 4: Create empty Object[][] (2D array) to store converted data
        // ============================================================================
        // Dimensions:
        //   Rows = Number of LoginData objects (how many test scenarios)
        //   Columns = 3 (always 3: username, password, expectedResult)
        //
        // Why Object[][]?
        //   Because data has mixed types (String + String + boolean)
        //   Object is superclass of all types → can hold anything
        //
        // Example: Object[3][3] creates:
        //   testData[0] = [null, null, null]
        //   testData[1] = [null, null, null]
        //   testData[2] = [null, null, null]

        Object[][] testData = new Object[loginDataArray.length][3];

        // ============================================================================
        // STEP 5: Loop through LoginData[] and fill Object[][]
        // ============================================================================
        // Purpose: Extract values from LoginData objects and store in Object[][]
        // Loop executes loginDataArray.length times (once per test scenario)
        //
        // Example with i=0:
        //   testData[0][0] = loginDataArray[0].username = "Admin"
        //   testData[0][1] = loginDataArray[0].password = "admin123"
        //   testData[0][2] = loginDataArray[0].expectedResult = true

        for (int i = 0; i < loginDataArray.length; i++) {
            // i = current row index (0, 1, 2, ...)
            // loginDataArray[i] = current LoginData object
            // testData[i] = current row in output array

            testData[i][0] = loginDataArray[i].username;
            // Column 0 = username (String)

            testData[i][1] = loginDataArray[i].password;
            // Column 1 = password (String)

            testData[i][2] = loginDataArray[i].expectedResult;
            // Column 2 = expectedResult (boolean)
        }

        // ============================================================================
        // STEP 6: Return populated Object[][] to TestNG @DataProvider
        // ============================================================================
        // TestNG receives this 2D array
        // For each row, TestNG calls @Test method once with values as parameters
        //
        // Example with 3 rows:
        //   Row 0: @Test(username="Admin", password="admin123", shouldSucceed=true)
        //   Row 1: @Test(username="Admin", password="wrong", shouldSucceed=false)
        //   Row 2: @Test(username="baduser", password="admin123", shouldSucceed=false)

        return testData;
    }
}