package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.InputStream;
import java.time.Duration;
import java.util.Map;

public class BasePage {
    protected WebDriver driver;
    protected Map<String, Map<String, String>> locators;
    private WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds timeout
        loadLocators();
    }

    private void loadLocators() {
        try {
            // Get the page class name and use it to find the locator YAML
            String className = this.getClass().getSimpleName();
            String fileName = "locators/" + className.replace("Page", "").toLowerCase() + "Page.yaml";

            // Load YAML file using Jackson
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);

            if (is == null) {
                throw new RuntimeException("Locator file not found: " + fileName);
            }

            // Use specific TypeReference to handle generics safely
            locators = mapper.readValue(is, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Map<String, String>>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load locators for " + this.getClass().getSimpleName(), e);
        }
    }

    // Convert locator name to By object
    protected By getBy(String name) {
        Map<String, String> locator = locators.get(name);
        String type = locator.get("type");
        String value = locator.get("value");

        switch (type.toLowerCase()) {
            case "id": return By.id(value);
            case "name": return By.name(value);
            case "xpath": return By.xpath(value);
            case "css": return By.cssSelector(value);
            case "class": return By.className(value);
            case "tag": return By.tagName(value);
            case "linktext": return By.linkText(value);
            case "partiallinktext": return By.partialLinkText(value);
            default: throw new IllegalArgumentException("Unsupported locator type: " + type);
        }
    }

    // Wait until the element is visible and interactable
    protected WebElement waitForElement(String name) {
        return wait.until(ExpectedConditions.elementToBeClickable(getBy(name)));
    }

    // Utility methods for actions with explicit waits
    protected WebElement find(String name) {
        return waitForElement(name);
    }

    protected void click(String name) {
        waitForElement(name).click();
    }

    protected void type(String name, String text) {
        waitForElement(name).sendKeys(text);
    }

    protected String getText(String name) {
        return waitForElement(name).getText();
    }

    // FluentWait (Advanced Waiting strategy)
    protected WebElement waitForElementUsingFluentWait(String name) {
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        return fluentWait.until(ExpectedConditions.elementToBeClickable(getBy(name)));
    }

    protected void clickWithFluentWait(String name) {
        waitForElementUsingFluentWait(name).click();
    }

    protected void typeWithFluentWait(String name, String text) {
        waitForElementUsingFluentWait(name).sendKeys(text);
    }

    protected String getTextWithFluentWait(String name) {
        return waitForElementUsingFluentWait(name).getText();
    }
}
