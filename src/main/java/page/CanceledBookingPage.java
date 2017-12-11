package page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class CanceledBookingPage extends AbstractPage {
    private static final Logger logger = LogManager.getLogger(CanceledBookingPage.class);

    @FindAll({@FindBy(css = ".mb-negative-text")})
    private List<WebElement> cancelText;

    public CanceledBookingPage() {
        logger.debug("Page has been created successfully");
    }

    public boolean isPageOpened() {
        logger.debug("(isPageOpened) check message");

        if (cancelText.size() != 0) {
            return cancelText.get(0).isDisplayed();
        }
        return false;
    }

}
