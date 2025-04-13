package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;
import reports.ReportManager;
import utils.LoggerUtil;
import utils.ScreenshotUtil;
import factory.DriverManager;
import org.apache.logging.log4j.Logger;

public class TestListener implements ITestListener {

    private static final Logger logger = LoggerUtil.getLogger(TestListener.class); 
    private static ThreadLocal<String> testName = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        String name = result.getMethod().getMethodName();
        testName.set(name);
        ReportManager.createTest(name);
        logger.info("Test '{}' started", name);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ReportManager.logPass("Test passed");
        logger.info("Test '{}' passed", testName.get()); 
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String errorMessage = "Test failed: " + result.getThrowable();
        ReportManager.logError(errorMessage);
        logger.error(errorMessage, result.getThrowable());

        WebDriver driver = DriverManager.getDriver(); 

        if (driver != null) {
            String path = ScreenshotUtil.takeScreenshot(driver, testName.get());
            if (path != null) {
                ReportManager.attachScreenshot(path);
                logger.info("Screenshot captured and attached: {}", path);
            }
        } else {
            logger.warn("WebDriver was null in onTestFailure. Screenshot not captured."); 
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String skippedMessage = "Test skipped: " + result.getThrowable();
        ReportManager.logWarning(skippedMessage);
        logger.warn(skippedMessage); 
    }

    @Override
    public void onFinish(ITestContext context) {
        ReportManager.flush();
        logger.info("Test execution finished"); 
    }
}
