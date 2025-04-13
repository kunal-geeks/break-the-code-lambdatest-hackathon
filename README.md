<h1 align="center">🧪 Break the Code - LambdaTest Hackathon Project</h1>

<p align="center">
  <img src="https://img.shields.io/github/actions/workflow/status/kunal-geeks/break-the-code-lambdatest-hackathon/test.yml?label=CI%20Build&logo=github&style=for-the-badge" />
  <img src="https://img.shields.io/badge/Java-11-blue?style=for-the-badge&logo=java" />
  <img src="https://img.shields.io/badge/TestNG-7.7-orange?style=for-the-badge&logo=testng" />
  <img src="https://img.shields.io/github/license/kunal-geeks/break-the-code-lambdatest-hackathon?style=for-the-badge" />
</p>

<p align="center">
  <b>Automation suite for the <a href="https://www.lambdatest.com/">LambdaTest "Break the Code" QA Hackathon</a></b><br>
  Java + Selenium + TestNG with LambdaTest, CI/CD, parallel execution, reporting, and logging.
</p>

---

## 🔧 Tech Stack

- ⚙️ **Test Framework:** TestNG
- 🧱 **Design Pattern:** Page Object Model (POM)
- 🌐 **Grid Provider:** LambdaTest (Cross-browser Testing)
- 📊 **Reports:** Allure / ExtentReports (Dynamic switch)
- 📦 **Logging:** Log4j2 (Daily rolling, environment-based)
- 🔄 **Retry Mechanism:** Custom RetryAnalyzer
- ☁️ **CI/CD:** GitHub Actions
- 🔑 **Secrets Handling:** ConfigManager with Env Overrides

---

## 📁 Project Structure

```bash
break-the-code/
├── .github/workflows/test.yml
├── logs/                         # Log4j2 logs
├── reports/                      # ExtentReports output
├── src/
│   ├── main/java/
│   │   ├── base/                 # BaseTest setup
│   │   ├── config/               # ConfigManager
│   │   ├── factory/              # DriverFactory
│   │   ├── pages/                # Page Objects
│   │   ├── reports/              # ReportManager
│   │   └── utils/                # RetryAnalyzer, LoggerUtil
│   └── test/java/tests/          # LoginTest, AlertsTest
├── resources/
│   ├── config.properties         # Base config
│   ├── config.qa.properties      # QA overrides
│   ├── config.stage.properties   # Stage overrides
│   ├── log4j2.xml                # Logging config
├── testng.xml
├── pom.xml
├── .gitignore
├── README.md
```

# 🚀 How to Run

## ▶️ Run with Environment, Report & Browser Options

```bash

# Clone the repository
git clone https://github.com/kunal-geeks/break-the-code-lambdatest-hackathon.git && cd break-the-code-lambdatest-hackathon

# Edit the config.properties file to add LambdaTest credentials
echo "Please edit config.properties to add your LambdaTest credentials (lt.username and lt.accessKey)"

# Install dependencies
mvn clean install

# Run on LambdaTest grid with Allure report (Production environment)
mvn clean test -Dsuite=testng.xml -Denv=prod -Dreport_type=allure -Dbrowser=lambdatest

# Run locally on Chrome with Extent report (Staging environment)
mvn clean test -Dsuite=testng.xml -Denv=stage -Dreport_type=extent -Dbrowser=chrome

# Run locally on Firefox with both Allure and Extent reports (QA environment)
mvn clean test -Dsuite=testng.xml -Denv=qa -Dreport_type=both -Dbrowser=firefox
```

#$ ▶️ Open Allure Report
```basg
allure serve allure-results
```

## ☁️ LambdaTest Integration

- Remote execution on LambdaTest Grid

- Secure credentials via System.getenv()

- Auto capability handling from DriverFactory

## 🆔 LambdaTest Test IDs
```bash

TWRUR-4JSDX-FSGSK-UUFZW
PNPZX-PXYM2-LXY29-JEGFO
UENNU-XSJ6C-KBGNS-FECY4
AKEXE-8SJOQ-43FZI-R7V67
KEJPK-XSHDA-QTY5O-DUJZL
HWURA-YPSDT-YBIYO-RXCAV
R8D2X-FPR92-2VMMP-W1QQF
BCPBS-E8HTY-AYIKT-X4UPH
ANZHK-FJGYQ-DN68Q-ZKDSJ
GV3XV-WVHLY-AXLFY-QEY3U
P2KTH-JJ8GM-QTQMH-FA4WE
```

## 🔄 GitHub Actions CI

- Trigger: Push or PR to main

Steps:

- JDK setup & dependency caching

- Maven test execution

- Allure/Extent report generation

- Report uploaded as artifact

## ✅ Features

- Login form tests

- JavaScript alert validation

- Parallel execution

- Configurable reporting

- Robust logging

- Cross-browser testing on LambdaTest

🧑‍💻 Author
Kunal Sharma

✉️ kunal.sdet001@gmail.com


