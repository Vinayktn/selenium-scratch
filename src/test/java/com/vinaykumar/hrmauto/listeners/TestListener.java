package com.vinaykumar.hrmauto.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.vinaykumar.hrmauto.base.BaseTest;
import com.vinaykumar.hrmauto.reports.ExtentManager;
import com.vinaykumar.hrmauto.utils.ScreenshotUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TestListener implements ITestListener {
    private static ThreadLocal<ExtentTest> test =  new ThreadLocal<>();

    @Override
    public void onTestFailure(ITestResult result) {
        ScreenshotUtil.capture(BaseTest.getDriver(), result.getName());
        test.get().fail(result.getThrowable());
    }

    @Override
    public void onStart(ITestContext context) {
        ExtentManager.getInstance();
        Path folder = Paths.get("screenshots");
        try {
            if (Files.exists(folder)) {
                try (Stream<Path> files = Files.list(folder)) {
                    files.forEach(file -> {
                        try {
                            Files.deleteIfExists(file);
                        } catch (Exception e) {
                            System.out.println("Could not delete: " + file);
                        }
                    });
                }
            }
        } catch (Exception e) {
            System.out.println("Screenshot cleanup failed: " + e.getMessage());
        }
    }


    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest page = ExtentManager.getInstance().createTest(result.getName());
        test.set(page);
     }


    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Passed");
     }


    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getInstance().flush();
      }


    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentManager.getInstance().removeTest(test.get());
    }
}