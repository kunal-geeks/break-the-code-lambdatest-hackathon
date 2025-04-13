package tests;

import utils.ConfigManager;
import utils.DataProviderUtils;
import io.qameta.allure.*;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import reports.ReportManager;
import utils.LoggerUtil;

import java.util.Map;

@Epic("HerokuApp Testing")
@Feature("Login Page Functional Tests")
public class LoginTest extends BaseTest {

    private static final Logger logger = LoggerUtil.getLogger(LoginTest.class);
    private static final String BASE_URL = ConfigManager.getBaseUrl();

    @Test(dataProvider = "getLoginData", dataProviderClass = DataProviderUtils.class,
          description = "Verify login scenarios from JSON")
    @Story("User login based on dynamic test data")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("kunal-geeks")
    @Link(name = "Login Page", url = "https://the-internet.herokuapp.com/login")
    public void testLogin(Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        String type = credentials.get("type");

        ReportManager.createTest("Login Test - " + type + " credentials");
        driver.get(BASE_URL + "/login");

        logger.info("Testing login for user: " + username);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        if ("valid".equalsIgnoreCase(type)) {
            String successMsg = loginPage.getSuccessMessage();
            ReportManager.logInfo("Verifying success message: " + successMsg);
            Assert.assertTrue(successMsg.contains("You logged into a secure area!"),
                    "Expected success message not found.");
            ReportManager.logPass("Valid login verified successfully");
        } else {
            String errorMsg = loginPage.getErrorMessage();
            ReportManager.logInfo("Verifying error message: " + errorMsg);
            Assert.assertTrue(
                    errorMsg.contains("Your username is invalid!") || errorMsg.contains("Your password is invalid!"),
                    "Expected error message not found.");
            ReportManager.logPass("Invalid login error verified successfully");
        }
    }
}
