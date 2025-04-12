### break-the-code-lambdatest-hackathon
🧪 Automation test suite for the LambdaTest "Break the Code" QA Hackathon — includes login validation and JavaScript alert handling using Java, TestNG, Selenium Grid, and LambdaTest integration. Features robust framework setup, parallel execution, reporting, logging, and secure credential handling.


# Run tests:
```bash
mvn clean test -Dsuite=testng.xml
```
# Generate Allure report:
```bash
allure serve target/allure-results
```

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
