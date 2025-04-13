package reports;

public interface Reporter {
    void init();
    void createTest(String name);
    void logInfo(String message);
    void logPass(String message);
    void logWarning(String message);
    void logError(String message);
    void logWithStatus(String message, String status);
    void attachScreenshot(String path);
    void flush();
}
