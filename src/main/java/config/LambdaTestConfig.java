package config;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.ConfigManager;

public class LambdaTestConfig {

    public static final String LT_USERNAME = ConfigManager.getLambdaTestUsername();
    public static final String LT_ACCESS_KEY = ConfigManager.getLambdaTestAccessKey();
    public static final String GRID_URL = "https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + ConfigManager.getHubURL();

    /**
     * Returns W3C-compliant capabilities for LambdaTest
     */
    public static DesiredCapabilities getCapabilities(String browser, String version, String platform) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", browser);
        caps.setCapability("browserVersion", version);

        // Construct LT:Options (W3C compliant) inside a separate MutableCapabilities
        MutableCapabilities ltOptions = new MutableCapabilities();
        ltOptions.setCapability("platformName", platform);
        ltOptions.setCapability("build", "LambdaTest Hackathon Build");
        ltOptions.setCapability("name", "LambdaTest Automation Test");
        ltOptions.setCapability("network", true);
        ltOptions.setCapability("visual", true);
        ltOptions.setCapability("video", true);
        ltOptions.setCapability("console", true);

        caps.setCapability("LT:Options", ltOptions);
        return caps;
    }
}
