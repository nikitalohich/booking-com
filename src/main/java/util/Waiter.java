package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.AbstractPage;

import java.util.List;

public class Waiter {
    static final Logger logger = LogManager.getLogger(AbstractPage.class);
    private static WebDriverWait webDriverWait;

    static {
        webDriverWait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 15);
    }

    public static void waitForElementPresent(By locator) {
        logger.debug("Wait for presence of element by locator: " + locator.toString());
        webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public static void waitForElementVisible(By locator) {
        logger.debug("Wait for visibility of element by locator: " + locator.toString());
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public static void waitForElementVisible(WebElement element) {
        logger.debug("Wait for presence of element: " + element.toString());
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForElementEnabled(WebElement element) {
        logger.debug("Wait for element to be clickable: " + element.toString());
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForElementInvisible(By locator) {
        logger.debug("Wait for invisibility of element by locator: " + locator.toString());
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void waitForTextPresent(By locator) {
        logger.debug("Wait for the text presence of element by locator: " + locator.toString());
        webDriverWait.until(e -> e.findElement(locator).getText().length() > 0);
    }

    public static void waitForPageLoad() {
        logger.debug("Wait for page to be loaded");
        webDriverWait.until(e -> (JavascriptExecutor) e).executeScript("return document.readyState").equals("complete");
    }

    public static void waitForListElementVisible(List<WebElement> element) {
        logger.debug("Wait for visibility of all elements from list");
        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(element));
    }

}
