package page;

import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import page.strategy.*;
import util.Waiter;

import static util.Clicker.click;

public class ConfirmReservationPage extends AbstractPage {

    protected static final int FIRST_ELEMENT = 0;

    private static final Logger logger = LogManager.getLogger(ConfirmReservationPage.class.getName());

    @FindBy(id = "cc1")
    private WebElement countryList;

    @FindBy(id = "phone")
    private WebElement phoneField;

    @FindBy(css = ".submit_holder_button.bp-overview-buttons-submit")
    private WebElement completeBookingButton;

    @FindBy(xpath = ".//div[@class='title bp_legacy_form_box__title--block']")
    private WebElement paymentDetailsBlock;

    private final By DETAILS_FORM = By.id("bookStage3Inc");

    // Interface for strategy
    protected FieldStrategy fieldStrategy;

    public ConfirmReservationPage() {
        logger.debug("Page has been created successfully");
    }

    public void setFieldStrategy() {
        if (!isCardNeeded()) {
            fieldStrategy = new ReservationWithoutCardImpl();
            logger.info("Reservation without card.");
        } else if (isAddressNeeded()) {
            fieldStrategy = new ReservationWithCardWithoutCvcImpl();
            logger.info("Reservation with card with address (without cvc).");
        } else if (this.isCvcNeeded()) {
            fieldStrategy = new ReservationWithCardCvcImpl();
            logger.info("Reservation with card with cvc.");
        } else {
            fieldStrategy = new ReservationWithCardImpl();
            logger.info("Reservation with card with neither cvc nor address.");
        }
    }

    public FieldStrategy getFieldStrategy() {
        return fieldStrategy;
    }

    private boolean isAddressNeeded() {
        boolean isAddressNeeded = isElementPresent(By.id("address1"));
        logger.debug("Address is needed: " + isAddressNeeded);
        return isAddressNeeded;
    }

    public ConfirmReservationPage setDataWithInterface(User user) {
        return fieldStrategy.fillAllField(this, user);
    }

    public boolean isCvcNeeded() {
        boolean isCvcNeeded = isElementPresent(By.id("cc_cvc"));
        logger.debug("Cvc is needed: " + isCvcNeeded);
        return isCvcNeeded;
    }

    public boolean isCardNeeded() {
        boolean isCardNeeded = isElementPresent(By.xpath(".//div[@class='title bp_legacy_form_box__title--block']"));
        logger.debug("Card is needed: " + isCardNeeded);
        return isCardNeeded;
    }

    public ConfirmReservationPage selectCountry(User user) {
        Waiter.waitForElementEnabled(completeBookingButton);
        new Select(countryList).selectByVisibleText(user.getCountry());
        return this;
    }

    public ConfirmReservationPage setPhone(User user) {
        phoneField.clear();
        phoneField.sendKeys(user.getPhone());
        return this;
    }

    public BookingConfirmationPage clickCompleteSuccessBookingButton() {
        logger.debug("Click booking button (all data is correct)");
        Waiter.waitForElementEnabled(completeBookingButton);
        click(completeBookingButton);
        return new BookingConfirmationPage();
    }

    public ConfirmReservationPage clickCompleteFailBookingButton() {
        logger.debug("Click booking button (with incorrect data)");
        Waiter.waitForElementEnabled(completeBookingButton);
        click(completeBookingButton);
        return this;
    }

    public boolean isPageOpened() {
        logger.debug("Check whether page opened?");
        return driver.findElement(DETAILS_FORM).isDisplayed();
    }

    public ConfirmReservationPage setDataInAllFields(User user) {
        logger.info("Set data: country and phone");
        this.selectCountry(user).setPhone(user);
        return this;
    }
}

