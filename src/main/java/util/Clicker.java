package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class Clicker {

    private static final Logger logger = LogManager.getLogger(Clicker.class);
    private static final WebDriver driver = DriverFactory.getInstance().getDriver();

    public static void click(WebElement element) {
        try {
            element.click();
            logger.debug("Element " + element.toString() + " was clicked in the normal way.");
        } catch (WebDriverException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            logger.debug("Element was clicked using JavascriptExecutor. Caused by: " + ex.getMessage());
        }
    }
}
