package reports;

import io.qameta.allure.Allure;

import java.io.FileInputStream;

public class AllureReporter implements Reporter {

    @Override
    public void init() {}

    @Override
    public void createTest(String name) {
        Allure.step("Starting test: " + name);
    }

    @Override
    public void logInfo(String message) {
        Allure.step(message);
    }

    @Override
    public void logPass(String message) {
        Allure.step("✅ " + message);
    }

    @Override
    public void logWarning(String message) {
        Allure.step("⚠️ " + message);
    }

    @Override
    public void logError(String message) {
        Allure.step("❌ " + message);
    }

    @Override
    public void logWithStatus(String message, String status) {
        String emoji;
        switch (status.toLowerCase()) {
            case "pass": emoji = "✅ "; break;
            case "fail":
            case "error": emoji = "❌ "; break;
            case "warn": emoji = "⚠️ "; break;
            default: emoji = "ℹ️ ";
        }
        Allure.step(emoji + message);
    }

    @Override
    public void attachScreenshot(String path) {
        try (FileInputStream fis = new FileInputStream(path)) {
            Allure.addAttachment("Screenshot", fis);
        } catch (Exception e) {
            Allure.step("Failed to attach screenshot: " + e.getMessage());
        }
    }

    @Override
    public void flush() {}
}
