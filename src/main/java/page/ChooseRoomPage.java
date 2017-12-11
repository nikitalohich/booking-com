package page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class ChooseRoomPage extends AbstractPage {

    private static final Logger logger = LogManager.getLogger(ChooseRoomPage.class);

    @FindBy(xpath = "//select[starts-with(@name, 'nr_rooms_')]")
    private WebElement firstRoomSelect;

    @FindBy(xpath = "//*[@class='pps']")
    private WebElement priceLabel;

    @FindBy(name = "book")
    private WebElement reserveRoom;

    public ChooseRoomPage() {
        logger.debug("Page has been created successfully");
    }

    public BookingDetailsPage reserveFirstAvailable() {
        logger.info("'Choose your room' page appeared");
        new Select(firstRoomSelect).selectByIndex(1);
        String newPrice = priceLabel.getText();
        reserveRoom.click();
        return isPageOpened() ? reserveFirstAvailable() : new BookingDetailsPage(newPrice);
    }

    @Override
    public boolean isPageOpened() {
        //Waiter.waitForElementEnabled(reserveRoom);
        WebElement ancestor = reserveRoom.findElement(By.xpath(".//ancestor::*[starts-with(@id,'bookStage')]"));
        String id = ancestor.getAttribute("id");
        logger.info("Loaded page was with id: " + id);
        return "bookStage1Inc".equals(id);
    }
}
