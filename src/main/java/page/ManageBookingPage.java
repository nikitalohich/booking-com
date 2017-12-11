package page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import util.Waiter;

import static util.Clicker.click;

public class ManageBookingPage extends AbstractPage {

    private static final Logger logger = LogManager.getLogger(ManageBookingPage.class);

    @FindBy(css = ".mb-btn.mb-cancel.custom_track.hasSlideBox")
    private WebElement cancelBookingButton;

    @FindBy(xpath = ".//*[@id='trip_was_cancelled']")
    private WebElement cancelReasonRadioButton;

    @FindBy(xpath = ".//*[@id='cancel_sure']")
    private WebElement cancelPopUpButton;

    @FindBy(css = ".MyBookingOptionsSaveChanges")
    private WebElement okPopUpButton;

    public ManageBookingPage() {
        logger.debug("Page has been created successfully");
    }

    public ManageBookingPage setCancelReason() {
        logger.info("Set cancel reason");
        Waiter.waitForElementEnabled(cancelReasonRadioButton);
        cancelReasonRadioButton.click();
        return this;
    }

    public ManageBookingPage clickCancelPopUpButton() {
        logger.debug("Click 'cancel booking'");
        Waiter.waitForElementVisible(cancelPopUpButton);
        cancelPopUpButton.click();
        return this;
    }

    public CanceledBookingPage clickOkPopUpButton() {
        logger.debug("Click 'ok'");
        Waiter.waitForElementVisible(okPopUpButton);
        okPopUpButton.click();
        return new CanceledBookingPage();
    }

    public ManageBookingPage clickCancelBookingButton() {
        logger.debug("Click 'cancel' in pop up");
        Waiter.waitForElementVisible(cancelBookingButton);
        click(cancelBookingButton);
        return this;
    }

    public boolean isPageOpened() {
        Waiter.waitForElementVisible(cancelBookingButton);
        return cancelBookingButton.isDisplayed();
    }
}
