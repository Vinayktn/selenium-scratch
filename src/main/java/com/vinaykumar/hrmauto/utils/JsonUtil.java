package com.vinaykumar.hrmauto.utils;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonUtil {

    public static Object[][] readJsonData(String filePath) throws IOException {


        // STEP 1: Read the JSON file as a String
        Path path = Paths.get(filePath);
        byte[] fileBytes = Files.readAllBytes(path);
        String jsonString = new String(fileBytes);
        //so this above code will give us the data in this format - then we need to deserialize this and fetch username passowrd and their respective values
        // String jsonString = "[{\"username\":\"Admin\",\"password\":\"admin123\",\"expectedResult\":true}]";

        // STEP 2: Parse the JSON string into an array of objects
        // Create a Gson object (GSON's main tool)
        Gson gson = new Gson();

        // Parse jsonString into LoginData[] array
        // gson.fromJson(text, class) converts JSON → Java objects
        LoginData[] loginDataArray = gson.fromJson(jsonString, LoginData[].class);

        // STEP 3: Create an Object[][] (2D array) to store the data
        // Size: rows = number of login scenarios, columns = 3 (username, password, expectedResult)
        Object[][] testData = new Object[loginDataArray.length][3];


        // How many rows? = loginDataArray.length (3 objects from JSON)
        // How many columns? = 3 (username, password, expectedResult)

        // STEP 4: Loop through each object in the parsed JSON array
        // For each object, extract 3 values: username, password, expectedResult
        for (int i = 0; i < loginDataArray.length; i++) {
            // i = current row (0, 1, 2)
            // Inside here, we'll fill testData[i][0], testData[i][1], testData[i][2]
            testData[i][0] = loginDataArray[i].username;
            testData[i][1] = loginDataArray[i].password;
            testData[i][2] = loginDataArray[i].expectedResult;
        }

        // STEP 5: Store these 3 values in the current row of Object[][]
        // currentRow[0] = username (String)
        // currentRow[1] = password (String)
        // currentRow[2] = expectedResult (Boolean)


        // STEP 6: Return the populated Object[][] (2D array with all test data)
        return testData;  // Placeholder for now
    }
}