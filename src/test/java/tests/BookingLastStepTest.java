package tests;

import entity.Card;
import exception.MissingFileException;
import exception.UnsupportedDriverNameException;
import list.PageList;
import list.UserList;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import page.BookingConfirmationPage;
import page.ConfirmReservationPage;
import util.DriverFactory;

import static org.testng.Assert.assertTrue;

public class BookingLastStepTest {

    static final Logger logger = LogManager.getLogger(BookingLastStepTest.class);
    private WebDriver driver;
    private ConfirmReservationPage confirmReservationPage;
    private BookingConfirmationPage bookingConfirmationPage;

    @BeforeClass(description = "Reservation last step")
    @Parameters({"COUNTRY", "PHONE", "CITY", "ADDRESS", "CARD_TYPE", "CARD_NUMBER",
            "EXPIRATION_DATE_MONTH", "EXPIRATION_DATE_YEAR", "CARD_CVC", "WORKFLOW_USER_INDEX", "CONFIRM_RESERVATION_PAGE_INDEX"})
    public void setup(String country, String phone, String city, String address, String cardType, String cardNumber,
                      String expirationDateMonth, String expirationDateYear, String cvc, int userIndex, int pageIndex) {

        logger.log(Level.INFO, "BeforeClass for tests.BookingLastStepTest");

        UserList.getInstance().getUsers().get(userIndex).setCountry(country);
        UserList.getInstance().getUsers().get(userIndex).setPhone(phone);
        UserList.getInstance().getUsers().get(userIndex).setCity(city);
        UserList.getInstance().getUsers().get(userIndex).setAddress(address);
        UserList.getInstance().getUsers().get(userIndex).setCard(new Card());
        UserList.getInstance().getUsers().get(userIndex).getCard().setCardType(cardType);
        UserList.getInstance().getUsers().get(userIndex).getCard().setCardNumber(cardNumber);
        UserList.getInstance().getUsers().get(userIndex).getCard().setExpirationDateMonth(expirationDateMonth);
        UserList.getInstance().getUsers().get(userIndex).getCard().setExpirationDateYear(expirationDateYear);
        UserList.getInstance().getUsers().get(userIndex).getCard().setCvc(cvc);

        driver = DriverFactory.getInstance().getDriver();

        confirmReservationPage = (ConfirmReservationPage) PageList.getInstance().getPages().get(pageIndex);
    }

    @Test(groups = {"successfulReservation"}, dependsOnGroups = {"bookingDetails"})
    @Parameters({"WORKFLOW_USER_INDEX"})
    public void bookWithoutCard(int userIndex) {
        logger.log(Level.INFO, "tests.BookingLastStepTest");
        confirmReservationPage.setFieldStrategy();
        confirmReservationPage.setDataWithInterface(UserList.getInstance().getUsers().get(userIndex));

        bookingConfirmationPage = confirmReservationPage.clickCompleteSuccessBookingButton();

        PageList.getInstance().getPages().add(bookingConfirmationPage);
        assertTrue(bookingConfirmationPage.isPageOpened());
    }

    @AfterClass(description = "Reservation is completed")
    public void teardown() throws UnsupportedDriverNameException, MissingFileException {
        logger.log(Level.INFO, "End of BookingWithoutCard Test");

        driver = null;
        confirmReservationPage = null;
        bookingConfirmationPage = null;
    }
}
