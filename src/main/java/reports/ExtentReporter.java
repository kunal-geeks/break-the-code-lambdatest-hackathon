package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public class ExtentReporter implements Reporter {
    private ExtentReports extent;
    private ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void init() {
        ExtentSparkReporter spark = new ExtentSparkReporter("reports/extent-report.html");
        spark.config().setDocumentTitle("Automation Test Report");
        spark.config().setReportName("Functional Test Results");
        spark.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java", System.getProperty("java.version"));
        extent.setSystemInfo("Tester", "Kunal Sharma");
    }

    @Override
    public void createTest(String name) {
        test.set(extent.createTest(name));  // Use ThreadLocal to create a new test per thread
    }

    @Override
    public void logInfo(String message) {
        test.get().info(message);
    }

    @Override
    public void logPass(String message) {
        test.get().pass(message);
    }

    @Override
    public void logWarning(String message) {
        test.get().warning(message);
    }

    @Override
    public void logError(String message) {
        test.get().fail(message);
    }

    @Override
    public void logWithStatus(String message, String status) {
        switch (status.toLowerCase()) {
            case "pass":
                logPass(message);
                break;
            case "fail":
            case "error":
                logError(message);
                break;
            case "warn":
                logWarning(message);
                break;
            default:
                logInfo(message);
        }
    }

    @Override
    public void attachScreenshot(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                test.get().addScreenCaptureFromPath(path);
            } else {
                test.get().warning("Screenshot not found: " + path);
            }
        } catch (Exception e) {
            test.get().warning("Failed to attach screenshot: " + e.getMessage());
        }
    }

    @Override
    public void flush() {
        extent.flush();
    }
}
