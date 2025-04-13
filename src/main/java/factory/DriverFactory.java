package factory;

import config.LambdaTestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.ConfigManager;

import java.net.URL;
import java.util.logging.Logger;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger logger = Logger.getLogger(DriverFactory.class.getName());

    /**
     * Get WebDriver instance for a given browser, version, and platform.
     * 
     * @param browser   the name of the browser to use (e.g., chrome, firefox, lambdatest)
     * @param version   the version of the browser (for LambdaTest compatibility)
     * @param platform  the platform to run the browser on (for LambdaTest compatibility)
     * @param headless  whether to run the browser in headless mode
     * @return WebDriver instance
     */
    public static WebDriver getDriver(String browser, String version, String platform, boolean headless) {
        if (driver.get() == null) {
            browser = browser != null ? browser : ConfigManager.getBrowser();
            version = version != null ? version : ConfigManager.getVersion();
            platform = platform != null ? platform : ConfigManager.getPlatform();
            headless = ConfigManager.isHeadless();

            switch (browser.toLowerCase()) {
                case "chrome":
                    driver.set(initializeChromeDriver(headless));
                    break;
                case "firefox":
                    driver.set(initializeFirefoxDriver(headless));
                    break;
                case "lambdatest":
                    // Actual browser to test on LambdaTest (e.g., chrome, firefox)
                    String realBrowser = ConfigManager.get("lt.browser", "chrome");
                    driver.set(initializeLambdaTestDriver(realBrowser, version, platform));
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
        }
        return driver.get();
    }

    /**
     * Initialize ChromeDriver with WebDriverManager and headless option.
     */
    private static WebDriver initializeChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new"); // Modern headless mode
        }
        logger.info("Initializing ChromeDriver with headless=" + headless);
        return new ChromeDriver(options);
    }

    /**
     * Initialize FirefoxDriver with WebDriverManager and headless option.
     */
    private static WebDriver initializeFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
        }
        logger.info("Initializing FirefoxDriver with headless=" + headless);
        return new FirefoxDriver(options);
    }

    /**
     * Initialize the LambdaTest WebDriver with desired capabilities.
     */
    public static WebDriver initializeLambdaTestDriver(String browser, String version, String platform) {
        try {
            DesiredCapabilities caps = LambdaTestConfig.getCapabilities(browser, version, platform);


            logger.info("Initializing LambdaTest RemoteWebDriver: browser=" + browser +
                        ", version=" + version + ", platform=" + platform);

            URL url = new URL(LambdaTestConfig.GRID_URL);
            return new RemoteWebDriver(url, caps);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize LambdaTest WebDriver: " + e.getMessage(), e);
        }
    }

    /**
     * Quit and clean up the WebDriver.
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            logger.info("Driver quit and resources cleaned up.");
        }
    }
}
