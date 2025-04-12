package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import config.LambdaTestConfig;
import java.net.URL;
import java.util.logging.Logger;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger logger = Logger.getLogger(DriverFactory.class.getName());

    /**
     * Get WebDriver instance for a given browser, version, and platform.
     * 
     * @param browser the name of the browser to use (e.g., chrome, firefox, lambdatest)
     * @param version the version of the browser (for LambdaTest compatibility)
     * @param platform the platform to run the browser on (for LambdaTest compatibility)
     * @return WebDriver instance
     */
    public static WebDriver getDriver(String browser, String version, String platform, boolean headless) {
        if (driver.get() == null) {
            switch (browser.toLowerCase()) {
                case "chrome":
                    driver.set(initializeChromeDriver(headless));
                    break;
                case "firefox":
                    driver.set(initializeFirefoxDriver(headless));
                    break;
                case "lambdatest":
                    driver.set(initializeLambdaTestDriver(browser, version, platform));
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
        }
        return driver.get();
    }

    /**
     * Initialize ChromeDriver with options, including headless if specified.
     * 
     * @param headless whether to run the browser in headless mode
     * @return a new ChromeDriver instance
     */
    private static WebDriver initializeChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless");  // Run headlessly
        }
        logger.info("Initializing ChromeDriver with headless=" + headless);
        return new ChromeDriver(options);
    }

    /**
     * Initialize FirefoxDriver with options, including headless if specified.
     * 
     * @param headless whether to run the browser in headless mode
     * @return a new FirefoxDriver instance
     */
    private static WebDriver initializeFirefoxDriver(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");  // Run headlessly
        }
        logger.info("Initializing FirefoxDriver with headless=" + headless);
        return new FirefoxDriver(options);
    }

    /**
     * Initialize the LambdaTest WebDriver with desired capabilities.
     * 
     * @param browser the browser name for LambdaTest
     * @param version the version of the browser for LambdaTest
     * @param platform the platform for LambdaTest
     * @return a new RemoteWebDriver instance connected to LambdaTest
     */
    private static WebDriver initializeLambdaTestDriver(String browser, String version, String platform) {
        try {
            DesiredCapabilities capabilities = LambdaTestConfig.getCapabilities(browser, version, platform);
            logger.info("Initializing LambdaTest driver with browser=" + browser + ", version=" + version + ", platform=" + platform);
            return new RemoteWebDriver(new URL(LambdaTestConfig.GRID_URL), capabilities);
        } catch (Exception e) {
            logger.severe("Failed to initialize LambdaTest driver: " + e.getMessage());
            throw new RuntimeException("LambdaTest driver initialization failed", e);
        }
    }

    /**
     * Quit the WebDriver and clean up.
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            logger.info("Driver quit and resources cleaned up.");
        }
    }
}
