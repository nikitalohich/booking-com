package page;

import entity.Card;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import util.DriverFactory;
import util.Waiter;

public class ConfirmReservationWithCardPage extends ConfirmReservationPage {

    private static final Logger logger = LogManager.getLogger(ConfirmReservationWithCardPage.class.getName());

    @FindBy(id = "book_credit_card")
    private WebElement cardForm;

    //payment details
    @FindBy(id = "cc_type")
    private WebElement cardTypeList;

    @FindBy(id = "cc_number")
    private WebElement cardNumberField;

    @FindBy(id = "cc_month")
    private WebElement expirationDateMonthList;

    @FindBy(id = "ccYear")
    private WebElement expirationDateYearList;

    //invalid expiration message
    @FindBy(xpath = ".//*[@id='cc-expiry-combo']/p")
    private WebElement invalidExpirationMessage;

    public ConfirmReservationWithCardPage() {
        logger.debug("Page has been created successfully");
    }

    public ConfirmReservationWithCardPage setCardData(Card card) {
        logger.info("Set card data: " + card.toString());
        Waiter.waitForElementVisible(cardForm);

        new Select(cardTypeList).selectByVisibleText(card.getCardType());
        cardNumberField.clear();
        cardNumberField.sendKeys(card.getCardNumber());
        new Actions(DriverFactory.getInstance().getDriver()).click(cardForm).build().perform();
        new Select(expirationDateMonthList).selectByValue(card.getExpirationDateMonth());
        new Select(expirationDateYearList).selectByValue(card.getExpirationDateYear());

        return this;
    }

    public boolean isInvalidExpiration() {
        Waiter.waitForElementVisible(invalidExpirationMessage);
        String message = invalidExpirationMessage.getText();
        logger.info("Warn message: " + message);
        return null != message;
    }

    @Override
    public ConfirmReservationPage setDataInAllFields(User user) {
        super.setDataInAllFields(user);
        setCardData(user.getCard());
        return this;
    }
}
