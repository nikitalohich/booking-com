package page;

import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class ConfirmReservationWithCardAndCvcPage extends ConfirmReservationWithCardPage {
    private static final Logger logger = LogManager.getLogger(ConfirmReservationWithCardWithoutCvcPage.class.getName());

    @FindBy(xpath = ".//*[@id='cc_cvc']")
    private WebElement cvcField;

    public ConfirmReservationWithCardAndCvcPage() {
        logger.debug("Page has been created successfully");
    }

    public ConfirmReservationPage setCvc(User user) {
        cvcField.clear();
        cvcField.sendKeys(user.getCard().getCvc());
        return this;
    }

    @Override
    public ConfirmReservationPage setDataInAllFields(User user) {
        super.setDataInAllFields(user);
        logger.info("Set data: cvc");
        this.setCvc(user);
        return this;
    }

}
