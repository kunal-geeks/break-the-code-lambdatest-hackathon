package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.AlertPage;
import io.qameta.allure.*;
import reports.ReportManager;
import utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import utils.DataProviderUtils;

import java.util.Map;

@Epic("HerokuApp Testing")
@Feature("JavaScript Alerts Functional Tests")
public class AlertsTest extends BaseTest {

    private static final Logger logger = LoggerUtil.getLogger(AlertsTest.class);

    @Test(description = "Handle JavaScript Alert", dataProvider = "getAlertData", dataProviderClass = DataProviderUtils.class)
    @Story("User handles simple JS alert")
    @Severity(SeverityLevel.NORMAL)
    @Owner("kunal-geeks")
    public void testJSAlert(Map<String, Object> data) {
        if (!"alert".equals(data.get("type"))) return;

        String expected = (String) data.get("expectedText");
        ReportManager.createTest("JS Alert Handling Test");
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        logger.info("Opened JavaScript Alerts page");

        AlertPage alertPage = new AlertPage(driver);
        alertPage.triggerJSAlert();

        Assert.assertEquals(alertPage.getResultText(), expected);
        ReportManager.logPass("JS Alert handled successfully");
    }

    @Test(description = "Handle JavaScript Confirm Accept", dataProvider = "getAlertData", dataProviderClass = DataProviderUtils.class)
    @Story("User accepts JS confirm")
    @Severity(SeverityLevel.MINOR)
    @Owner("kunal-geeks")
    public void testJSConfirmAccept(Map<String, Object> data) {
        if (!"confirm".equals(data.get("type"))) return;

        boolean accept = Boolean.parseBoolean(data.get("accept").toString());
        String expected = (String) data.get("expectedText");

        ReportManager.createTest("JS Confirm Accept Test");
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        logger.info("Opened JavaScript Alerts page");

        AlertPage alertPage = new AlertPage(driver);
        alertPage.triggerJSConfirm(accept);

        Assert.assertEquals(alertPage.getResultText(), expected);
        ReportManager.logPass("JS Confirm Accept verified");
    }

    @Test(description = "Handle JavaScript Prompt Input", dataProvider = "getAlertData", dataProviderClass = DataProviderUtils.class)
    @Story("User enters text in JS prompt")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("kunal-geeks")
    public void testJSPrompt(Map<String, Object> data) {
        if (!"prompt".equals(data.get("type"))) return;

        String input = (String) data.get("inputText");
        String expected = (String) data.get("expectedText");

        ReportManager.createTest("JS Prompt Input Test");
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        logger.info("Opened JavaScript Alerts page");

        AlertPage alertPage = new AlertPage(driver);
        alertPage.triggerJSPrompt(input);

        Assert.assertEquals(alertPage.getResultText(), expected);
        ReportManager.logPass("JS Prompt input verified successfully");
    }
}
