package testutil;

import com.epam.reportportal.message.ReportPortalMessage;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import util.DriverFactory;
import util.Highlighter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Screenshoter {

    private static final String screensFolder = "screenshots";
    static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(Screenshoter.class);

    public static void take() {
        WebDriver driver = DriverFactory.getInstance().getDriver();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String screenshotName = screensFolder + "/scr_" + System.nanoTime();
            File copy = new File(screenshotName + ".png");
            FileUtils.copyFile(screenshot, copy);
            ReportPortalMessage message = new ReportPortalMessage(copy, "screenshot " + copy.getName() + " logged");
            logger.info(message);
            logger.info("Saved screenshot: " + screenshotName);
        } catch (IOException e) {
            logger.error("Failed to make screenshot");
        }
    }

    public static void takeWithHighlight(By locator) {
        Highlighter.highlight(locator);
        take();
        Highlighter.unhighlight(locator);
    }

    public static void clean() {
        File screenshotsPackage = new File(screensFolder);
        File[] list = screenshotsPackage.listFiles();
        if (list.length > 100) {
            List<File> screenshots = Arrays.asList(list);
            for (int i = 0; i < 80; i++) {
                if (screenshots.get(i).delete()) {
                    logger.log(Level.INFO, "screenshot " + screenshots.get(i).getName() + " has been deleted successfully");
                }
            }
            logger.log(Level.INFO, "screenshots folder has been cleared");
        }
    }
}
