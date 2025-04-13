package pages;

import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        typeWithFluentWait("usernameInput", username);
        typeWithFluentWait("passwordInput", password);
        clickWithFluentWait("loginButton");
    }

    public String getSuccessMessage() {
        return getTextWithFluentWait("successMessage");
    }

    public String getErrorMessage() {
        return getTextWithFluentWait("errorMessage");
    }
}
