package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();

    static {
        loadBaseConfig();                    // Load config.properties
        loadEnvironmentSpecificConfig();     // Load config.qa.properties, etc.
        overrideWithEnvVars();               // Override via environment variables
    }

    private static void loadBaseConfig() {
        loadPropertiesFromClasspath("config/config.properties");
    }

    private static void loadEnvironmentSpecificConfig() {
        String env = System.getProperty("env", System.getenv().getOrDefault("ENV", "prod")).toLowerCase();
        System.out.println("[ConfigManager] Active environment: " + env);
        loadPropertiesFromClasspath("config/config." + env + ".properties");
    }

    private static void loadPropertiesFromClasspath(String path) {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(path)) {
            if (input != null) {
                properties.load(input);
                System.out.println("[ConfigManager] Loaded: " + path);
            } else {
                System.err.println("[ConfigManager] File not found on classpath: " + path);
            }
        } catch (IOException e) {
            System.err.println("[ConfigManager] Failed to load config: " + path + " -> " + e.getMessage());
        }
    }

    private static void overrideWithEnvVars() {
        for (String key : properties.stringPropertyNames()) {
            String envKey = key.toUpperCase().replace(".", "_");
            String envValue = System.getenv(envKey);
            if (envValue != null) {
                properties.setProperty(key, envValue);
                System.out.println("[ConfigManager] Overridden from ENV: " + key + " = " + envValue);
            }
        }
    }

    private static String getOrDefault(String key, String fallback) {
        return System.getenv().getOrDefault(key.toUpperCase().replace(".", "_"),
                properties.getProperty(key, fallback));
    }

    // === Public Getters ===

    public static String getBaseUrl() {
        return getOrDefault("base_url", "http://localhost");
    }

    public static String getBrowser() {
        return getOrDefault("browser", "chrome");
    }

    public static String getPlatform() {
        return getOrDefault("platform", "Windows 10");
    }

    public static String getVersion() {
        return getOrDefault("version", "latest");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getOrDefault("headless", "true"));
    }

    public static int getTimeout() {
        return Integer.parseInt(getOrDefault("timeout", "10"));
    }

    public static String getExecutionEnv() {
        return getOrDefault("execution_env", "local");
    }

    public static String getReportType() {
        return getOrDefault("report_type", "allure");
    }

    public static String getUsername() {
        return getOrDefault("username", "admin");
    }

    public static String getPassword() {
        return getOrDefault("password", "admin123");
    }

    public static String getLambdaTestUsername() {
        return getOrDefault("lt.username", "your_lambdatest_username");
    }

    public static String getLambdaTestAccessKey() {
        return getOrDefault("lt.accessKey", "your_lambdatest_key");
    }

    public static String getHubURL() {
        return getOrDefault("hub", "@hub.lambdatest.com/wd/hub");
    }

	public static String get(String string, String string2) {
		return getOrDefault(string, string2);
	}
}
