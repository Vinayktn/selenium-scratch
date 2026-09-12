package com.vinaykumar.hrmauto.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenshotUtil {
    public static void capture(WebDriver driver, String testName) {
        // cast driver to TakesScreenshot, get bytes, build a folder path and a filename, write the bytes to that file

        try {
            // GAP 1: take the picture as bytes
            byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            // 2. build a folder path and a filename
            Path folder = Paths.get("screenshots");
            Files.createDirectories(folder);

            // GAP 3: build the full file path, e.g. screenshots/loginWorks.png
            Path file = folder.resolve(testName + ".png");

            // GAP 4: write the bytes to that file
            Files.write(file, png);

            System.out.println("Screenshot saved: " + file.toAbsolutePath());

        } catch (Exception e) {
            System.out.println("Screenshot failed: " + e.getMessage());
        }
    }
}