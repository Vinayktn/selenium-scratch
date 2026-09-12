package com.vinaykumar.hrmauto.listeners;

import com.vinaykumar.hrmauto.config.ConfigReader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;
    private final int max = Integer.parseInt(ConfigReader.get("retry.count"));

    @Override
    public boolean retry(ITestResult result) {
        if (count < max) {
            count++;
            return true;
        }
        return false;
     }
}
