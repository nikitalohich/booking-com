package page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import parser.StringDataParser;
import util.DriverFactory;
import util.Waiter;

import java.util.List;
import java.util.Random;

import static util.Clicker.click;

public class HotelPage extends AbstractPage {

    static final Logger logger = LogManager.getLogger(HotelPage.class);

    private static final String COLOR = "56, 56, 56, 1";

    @FindBy(xpath = "(//div[contains(@id, 'rooms')]//table//button[@type = 'submit'])[last()]")
    private WebElement bookingButton;

    @FindBy(xpath = "//div[contains(@id, 'rooms')]//table//td[contains(@class,'room-select')]")
    private List<WebElement> redErrorColumn;

    @FindAll(@FindBy(xpath = "//div[contains(@id, 'rooms')]//table//select"))
    private List<WebElement> selectedOptionCountRoom;

    @FindBy(xpath = "//h2[@id='hp_hotel_name']")
    private WebElement hotelTitle;

    @FindBy(xpath = "//div[contains(@class, 'checkin-field')]//div[contains(@class, 'field__display')]")
    private WebElement checkInDate;

    @FindBy(xpath = "//div[contains(@class, 'checkout-field')]//div[contains(@class, 'field__display')]")
    private WebElement checkOutDate;

    private final By SUMMARY_PANEL = By.xpath("(//*[@id='booking-summary'])[last()]");
    private final By ROOM_COUNT = By.xpath("(//*[@id='booking-summary']//*[@class='rooms-count']/*)[last()]");
    private final By TOTAL_PRICE = By.xpath("(//*[@id='booking-summary']//*[contains(@class,'total-price')])[last()]");

    private String title;
    private String checkIn;
    private String checkOut;
    private String modifyCountRooms;
    private String modifyTotalPrice;

    public HotelPage() {
        logger.debug("Page has been created successfully");
    }

    @Override
    public boolean isPageOpened() {
        return "b2hotelPage".equals(getPageId());//hotelTitle.isDisplayed();
    }

    public void clickToBookingButton() {
        click(bookingButton);
    }

    public BookingDetailsPage clickForConfirmedReservation() {
        checkIn = checkInDate.getText();
        checkOut = checkOutDate.getText();
        title = hotelTitle.getText();
        clickToBookingButton();

        ChooseRoomPage chooseRoomPage = new ChooseRoomPage();

        return chooseRoomPage.isPageOpened() ? chooseRoomPage.reserveFirstAvailable() : new BookingDetailsPage();
    }

    public boolean checkRedLight() {
        try {
            Waiter.waitForListElementVisible(selectedOptionCountRoom);
            Waiter.waitForListElementVisible(redErrorColumn);
            String color = redErrorColumn.get(0).getCssValue("color");
            String colorNumber = color.substring(color.indexOf('(') + 1, color.indexOf(')'));
            return COLOR.contains(colorNumber);
        } catch (TimeoutException time) {
            return true;
        }
    }

    public void selectCountRoom() {
        int index = new Random().nextInt(selectedOptionCountRoom.size());
        Select select = new Select(selectedOptionCountRoom.get(index));
        select.selectByIndex(1);
        Waiter.waitForElementEnabled(selectedOptionCountRoom.get(index));

        parseRoomAndPrice(select.getFirstSelectedOption().getText());
    }

    private void parseRoomAndPrice(String roomsAndPrice) {
        modifyCountRooms = String.valueOf(StringDataParser.extractNumber(roomsAndPrice.substring(0, roomsAndPrice.indexOf('('))));
        modifyTotalPrice = roomsAndPrice.substring(roomsAndPrice.indexOf('(') + 1, roomsAndPrice.indexOf(')'));
    }

    public boolean checkSelectedRoomsAndPrice() {
        WebDriver driver = DriverFactory.getInstance().getDriver();
        Waiter.waitForElementPresent(SUMMARY_PANEL);

        int rooms = Integer.parseInt(driver.findElement(ROOM_COUNT).getText());
        int price = StringDataParser.extractNumber(driver.findElement(TOTAL_PRICE).getText());
        int expectedRooms = Integer.parseInt(modifyCountRooms);
        int expectedPrice = StringDataParser.extractNumber(modifyTotalPrice);

        return (rooms == expectedRooms) && (price == expectedPrice);
    }

    public String getTotalPrice() {
        return modifyTotalPrice;
    }

    public String getTitle() {
        return title;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }
}
