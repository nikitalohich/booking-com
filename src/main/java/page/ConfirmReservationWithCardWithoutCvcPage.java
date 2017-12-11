package page;

import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import util.Waiter;

public class ConfirmReservationWithCardWithoutCvcPage extends ConfirmReservationWithCardPage {
    private static final Logger logger = LogManager.getLogger(ConfirmReservationWithCardWithoutCvcPage.class.getName());

    @FindBy(xpath = ".//*[@id='address1']")
    private WebElement addressField;

    @FindBy(xpath = ".//*[@id='city']")
    private WebElement cityField;

    public ConfirmReservationWithCardWithoutCvcPage() {
        logger.debug("Page has been created successfully");
    }

    public ConfirmReservationWithCardWithoutCvcPage setAddress(User user) {
        addressField.clear();
        Waiter.waitForElementVisible(addressField);
        addressField.sendKeys(user.getAddress());
        return this;
    }

    public ConfirmReservationWithCardWithoutCvcPage setCity(User user) {
        cityField.clear();
        Waiter.waitForElementVisible(cityField);
        cityField.sendKeys(user.getCity());
        return this;
    }

    @Override
    public ConfirmReservationPage setDataInAllFields(User user) {
        super.setDataInAllFields(user);
        logger.info("Set data: address and city");
        this.setAddress(user).setCity(user);
        return this;
    }
}
