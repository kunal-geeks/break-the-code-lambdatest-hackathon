package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import factory.DriverFactory;
import listeners.TestListener;
import reports.ReportManager;
import utils.ConfigManager;

public class BaseTest {
    protected WebDriver driver;
    
    @BeforeSuite
    public void beforeSuite() {
        ReportManager.init(ConfigManager.getReportType()); 
    }
    
    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "version", "platform", "headless"})
    public void setUp(@Optional String browser,
                      @Optional String version,
                      @Optional String platform,
                      @Optional String headlessParam) {

        boolean headless = headlessParam != null ? Boolean.parseBoolean(headlessParam)
                                                 : ConfigManager.isHeadless();

        driver = DriverFactory.getDriver(browser, version, platform, headless);
    }


    @AfterMethod(alwaysRun = true)
    public void teardown() {
        DriverFactory.quitDriver();
    }

}
