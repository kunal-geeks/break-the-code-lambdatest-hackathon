package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LambdaTestTest extends BaseTest {

    @Test
    public void testLambdaTestWebsite() {
        // Navigate to the LambdaTest website
        driver.get("https://www.lambdatest.com");

        // Assert that the LambdaTest title is present
        Assert.assertTrue(driver.getTitle().contains("LambdaTest"));
    }

    @Test
    public void testHomePageTitle() {
        // Navigate to a sample website
        driver.get("https://www.google.com");

        // Assert that the Google title is correct
        Assert.assertEquals(driver.getTitle(), "Google");
    }
}
