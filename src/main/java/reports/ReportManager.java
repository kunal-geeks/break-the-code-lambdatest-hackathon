package reports;

import java.util.ArrayList;
import java.util.List;

public class ReportManager {

    private static final List<Reporter> reporters = new ArrayList<>();

    public static void init(String type) {
        reporters.clear();
        switch (type.toLowerCase()) {
            case "extent":
                reporters.add(new ExtentReporter());
                break;
            case "allure":
                reporters.add(new AllureReporter());
                break;
            case "both":
                reporters.add(new ExtentReporter());
                reporters.add(new AllureReporter());
                break;
            default:
                throw new IllegalArgumentException("Unsupported report type: " + type);
        }

        reporters.forEach(Reporter::init);
    }

    public static void createTest(String name) {
        reporters.forEach(r -> r.createTest(name));
    }

    public static void logInfo(String message) {
        reporters.forEach(r -> r.logInfo(message));
    }

    public static void logPass(String message) {
        reporters.forEach(r -> r.logPass(message));
    }

    public static void logWarning(String message) {
        reporters.forEach(r -> r.logWarning(message));
    }

    public static void logError(String message) {
        reporters.forEach(r -> r.logError(message));
    }

    public static void logWithStatus(String message, String status) {
        reporters.forEach(r -> r.logWithStatus(message, status));
    }

    public static void attachScreenshot(String path) {
        reporters.forEach(r -> r.attachScreenshot(path));
    }

    public static void flush() {
        reporters.forEach(Reporter::flush);
    }
}
