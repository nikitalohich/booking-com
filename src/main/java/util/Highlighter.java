package util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Highlighter {

    private static final String HIGHLIGHT_SCRIPT = "arguments[0].style.border='3px solid green'";
    private static final String UNHIGHLIGHT_SCRIPT = "arguments[0].style.border='0px'";

    public static void highlight(By locator) {
        WebDriver driver = DriverFactory.getInstance().getDriver();
        highlight(driver.findElement(locator));
    }

    public static void unhighlight(By locator) {
        WebDriver driver = DriverFactory.getInstance().getDriver();
        unhighlight(driver.findElement(locator));
    }

    public static void highlight(List<WebElement> list) {
        list.forEach(Highlighter::highlight);
    }

    public static void unhighlight(List<WebElement> list) {
        list.forEach(Highlighter::unhighlight);
    }

    public static void highlight(WebElement element) {
        doScript(HIGHLIGHT_SCRIPT, element);
    }

    public static void unhighlight(WebElement element) {
        doScript(UNHIGHLIGHT_SCRIPT, element);
    }

    private static void doScript(String script, WebElement element) {
        WebDriver driver = DriverFactory.getInstance().getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script, element);
    }
}
