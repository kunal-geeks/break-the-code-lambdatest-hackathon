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

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver(String browser, String version, String platform) {
        if (driver.get() == null) {
            switch (browser.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless"); // Headless option
                    driver.set(new ChromeDriver(chromeOptions));
                    break;
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("-headless"); // Headless option
                    driver.set(new FirefoxDriver(firefoxOptions));
                    break;
                case "lambdatest":
                    driver.set(initializeLambdaTestDriver(browser, version, platform));
                    break;
                default:
                    throw new IllegalArgumentException("Browser not supported: " + browser);
            }
        }
        return driver.get();
    }

    private static WebDriver initializeLambdaTestDriver(String browser, String version, String platform) {
        try {
            DesiredCapabilities caps = LambdaTestConfig.getCapabilities(browser, version, platform);
            return new RemoteWebDriver(new URL(LambdaTestConfig.GRID_URL), caps);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize LambdaTest driver", e);
        }
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
