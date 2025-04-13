package factory;

import org.openqa.selenium.WebDriver;

public class DriverManager {

    /**
     * Get the WebDriver instance from ThreadLocal storage
     */
    public static WebDriver getDriver() {
        return DriverFactory.getDriver(null, null, null, false);
    }
}
