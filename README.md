### break-the-code-lambdatest-hackathon
🧪 Automation test suite for the LambdaTest "Break the Code" QA Hackathon — includes login validation and JavaScript alert handling using Java, TestNG, Selenium Grid, and LambdaTest integration. Features robust framework setup, parallel execution, reporting, logging, and secure credential handling.


# QA Hackathon Automation Challenge (Java)

## 💼 Tech Stack
- Selenium + TestNG
- Page Object Model
- Log4j2 Logging
- ExtentReports / Allure Reports (toggle via config)
- Retry Logic
- LambdaTest Integration
- GitHub Actions CI

## 🚀 How to Run

```bash
# Run locally with ExtentReports
mvn clean test -Dreport=extent

# Run locally with Allure Reports
mvn clean test -Dreport=allure
mvn allure:serve
```
```
break-the-code/
├── .github/workflows/test.yml
├── logs/                        # Logs directory (ignored in .gitignore)
├── reports/                     # ExtentReports output
├── src/
│   ├── main/java/
│   │   ├── base/                # BaseTest.java
│   │   ├── config/              # ConfigReader.java
│   │   ├── factory/             # DriverFactory.java
│   │   ├── pages/               # POM classes
│   │   ├── utils/               # RetryAnalyzer, LoggerUtil
│   │   └── reports/             # ReportManager.java
│   └── test/java/tests/         # LoginTest.java, AlertsTest.java
├── resources/
│   ├── config.properties
│   ├── log4j2.xml
├── testng.xml
├── pom.xml
├── .gitignore
├── README.md

```

```
✅ 1. Unit Test Class for ConfigManager
We'll add a ConfigManagerTest under src/test/java to verify property loading, environment overrides, and fallbacks.

✅ 2. Dynamic Environment Support in testng.xml
We’ll update the XML to read the environment dynamically using Java system properties, allowing runtime switching via:

bash
Copy
Edit
mvn clean test -Denv=stage
🔜 Next Framework Enhancements
📘 Logging (Advanced)
Integrate and configure Log4j2 for:

File & console logging

Environment-specific log levels

Rolling logs per day

📊 Advanced Test Data Strategy
Add a test data manager:

Load data from JSON, YAML, or Excel

Support parameterized tests using TestNG @DataProvider

🔄 CI/CD Pipeline via GitHub Actions
Trigger on:

Push to main

Pull Request to main

Jobs:

Checkout + Setup JDK

Cache dependencies

Run tests

Generate Allure/Extent Reports as artifacts

(Optional) Slack/Gmail/Teams notifications


✅ Bonus: Run a Specific Class or Group from Command Line (Optional)
Specific class:

mvn clean test -Dtest=YourTestClass

TestNG group:

mvn clean test -Dgroups="smoke"
```

