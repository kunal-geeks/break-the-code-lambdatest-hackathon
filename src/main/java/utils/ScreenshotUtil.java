package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    // Create a logger instance using Log4j2
    private static final Logger logger = LoggerUtil.getLogger(ScreenshotUtil.class);

    /**
     * Captures a screenshot and stores it in the screenshots directory.
     * 
     * @param driver   the WebDriver instance
     * @param testName the name of the test for which the screenshot is captured
     * @return the path to the saved screenshot file
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            // Capture screenshot
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            
            // Format timestamp for unique screenshot name
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String path = "screenshots/" + testName + "_" + timestamp + ".png";
            
            // Create the destination file and copy the screenshot
            File dest = new File(path);
            dest.getParentFile().mkdirs();
            Files.copy(src.toPath(), dest.toPath());

            // Log success message with Log4j2
            logger.info("✅ Screenshot captured: " + path);
            return path;
        } catch (Exception e) {
            // Log the error message with Log4j2
            logger.error("❌ Screenshot capture failed: " + e.getMessage(), e);
            return null;
        }
    }
}
