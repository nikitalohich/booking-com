package page;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import util.Waiter;

import java.util.List;


public class BookingConfirmationPage extends AbstractPage {

    private static final Logger logger = LogManager.getLogger(BookingConfirmationPage.class);

    @FindBy(xpath = ".//li[@class = '']/a")
    private WebElement cancelBookingLink;

    @FindAll({@FindBy(xpath = ".//div[@class='mb-conf-print']/a")})
    private List<WebElement> printConfirmationButton;

    @FindBy(css = ".modal-mask-closeBtn")
    private WebElement closePopUpsButton;

    public BookingConfirmationPage() {
        logger.log(Level.INFO, "Page has been created successfully");
    }

    public ManageBookingPage cancelBooking() {
        logger.info("Go to Manage booking page");
        Waiter.waitForElementEnabled(cancelBookingLink);

        cancelBookingLink.click();
        return new ManageBookingPage();
    }

    public boolean isPageOpened() {
        return printConfirmationButton.isEmpty()
                ? closePopUpsButton.isDisplayed()
                : printConfirmationButton.get(0).isDisplayed();
    }

    public BookingConfirmationPage closeCreatePasswordPopUp() {
        logger.info("Close pop up if it is visible");
        new Actions(driver).click(closePopUpsButton).build().perform();
        return this;
    }

}
