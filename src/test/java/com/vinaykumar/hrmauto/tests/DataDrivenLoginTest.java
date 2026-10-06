package com.vinaykumar.hrmauto.tests;

import com.vinaykumar.hrmauto.base.BaseTest;
import com.vinaykumar.hrmauto.config.ConfigReader;
import com.vinaykumar.hrmauto.pages.LoginPage;
import com.vinaykumar.hrmauto.utils.JsonUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class DataDrivenLoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws IOException {
        return JsonUtil.readJsonData("src/test/resources/loginData.json");
    }


    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, boolean shouldSucceed) {
        getDriver().get(ConfigReader.get("base.url"));
        new LoginPage(getDriver()).login(username, password);

        // Check result based on shouldSucceed flag
        if (shouldSucceed) {
            // Login should succeed — check URL contains pim or dashboard
            Assert.assertTrue(getDriver().getCurrentUrl().contains("dashboard"));
        } else {
            // Login should fail — check URL still on login page
            Assert.assertTrue(getDriver().getCurrentUrl().contains("login"));
        }
    }
}
