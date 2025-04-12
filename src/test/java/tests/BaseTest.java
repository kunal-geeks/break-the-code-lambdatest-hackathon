package tests;

import driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    @Parameters({"browser", "version", "platform", "headless"})
    public void setUp(String browser, String version, String platform, boolean headless) {
        driver = DriverFactory.getDriver(browser, version, platform, headless);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Driver quit...");
        }
    }
}
