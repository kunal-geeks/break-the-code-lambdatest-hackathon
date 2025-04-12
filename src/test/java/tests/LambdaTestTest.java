package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import utils.LoggerUtil;



public class LambdaTestTest extends BaseTest {

    private static final Logger logger = LoggerUtil.getLogger(LambdaTestTest.class);

    @Test
    public void verifyTodoListFunctionality() throws InterruptedException {
        String spanText;

        logger.info("Opening LambdaTest Todo App");
        driver.get("https://lambdatest.github.io/sample-todo-app/");

        // Interact with existing todo list items
        logger.info("Clicking existing list items");
        driver.findElement(By.name("li1")).click();
        driver.findElement(By.name("li2")).click();
        driver.findElement(By.name("li3")).click();
        driver.findElement(By.name("li4")).click();

        // Add new items
        logger.info("Adding new list items");
        driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 6");
        driver.findElement(By.id("addbutton")).click();

        driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 7");
        driver.findElement(By.id("addbutton")).click();

        driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 8");
        driver.findElement(By.id("addbutton")).click();

        // Interact with more items
        logger.info("Interacting with added list items");
        driver.findElement(By.name("li1")).click();
        driver.findElement(By.name("li3")).click();
        driver.findElement(By.name("li7")).click();
        driver.findElement(By.name("li8")).click();

        // Final add and assert
        logger.info("Adding final list item and asserting its presence");
        driver.findElement(By.id("sampletodotext")).sendKeys("Get Taste of Lambda and Stick to It");
        driver.findElement(By.id("addbutton")).click();
        driver.findElement(By.name("li9")).click();

        spanText = driver.findElement(By.xpath("/html/body/div/div/div/ul/li[9]/span")).getText();
        Assert.assertEquals(spanText, "Get Taste of Lambda and Stick to It");

        logger.info("Test completed successfully.");
    }
}
