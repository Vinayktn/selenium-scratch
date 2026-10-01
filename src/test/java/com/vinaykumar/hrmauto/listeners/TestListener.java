package com.vinaykumar.hrmauto.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.vinaykumar.hrmauto.base.BaseTest;
import com.vinaykumar.hrmauto.reports.ExtentManager;
import com.vinaykumar.hrmauto.utils.ScreenshotUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TestListener implements ITestListener {
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentTest getTest() {
        return test.get();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ScreenshotUtil.capture(BaseTest.getDriver(), result.getName());
        ExtentTest page = test.get();
        page.fail(result.getThrowable());
    }

    @Override
    public void onStart(ITestContext context) {
        ExtentManager.getInstance();

        File folder = new File("screenshots");
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                try {
                    Files.deleteIfExists(file.toPath());
                } catch (IOException e) {
                    System.out.println("Could not delete " + file.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest page = ExtentManager.getInstance().createTest(result.getName());
        test.set(page);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest page = test.get();   // pull the handle out
        page.pass("Passed");            // use it
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