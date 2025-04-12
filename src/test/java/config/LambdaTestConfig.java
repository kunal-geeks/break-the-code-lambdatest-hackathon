package config;

import org.openqa.selenium.remote.DesiredCapabilities;

public class LambdaTestConfig {
	
    public static final String LT_USERNAME = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
    public static final String LT_ACCESS_KEY = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");
    
    static String hub = "@hub.lambdatest.com/wd/hub";
    
    public static final String GRID_URL = "https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + hub;

    public static DesiredCapabilities getCapabilities(String browser, String version, String platform) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", browser);
        caps.setCapability("version", version);
        caps.setCapability("platform", platform);
        caps.setCapability("build", "LambdaTest Hackathon Build");
        caps.setCapability("name", "LambdaTest Automation Test");
        caps.setCapability("network", true);
        caps.setCapability("visual", true);
        caps.setCapability("video", true);
        caps.setCapability("console", true);
        return caps;
    }
}
