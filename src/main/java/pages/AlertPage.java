package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

public class AlertPage extends BasePage {

    public AlertPage(WebDriver driver) {
        super(driver);
    }

    public void triggerJSAlert() {
        clickWithFluentWait("jsAlertBtn");
        driver.switchTo().alert().accept();
    }

    public void triggerJSConfirm(boolean accept) {
        clickWithFluentWait("jsConfirmBtn");
        Alert alert = driver.switchTo().alert();
        if (accept) alert.accept(); else alert.dismiss();
    }

    public void triggerJSPrompt(String input) {
        clickWithFluentWait("jsPromptBtn");
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(input);
        alert.accept();
    }

    public String getResultText() {
        return getTextWithFluentWait("resultText");
    }
}
