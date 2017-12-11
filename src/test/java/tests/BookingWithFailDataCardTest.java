package tests;

import entity.Card;
import entity.User;
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
import page.ConfirmReservationPage;
import page.ConfirmReservationWithCardPage;
import page.strategy.ReservationWithoutCardImpl;
import util.DriverFactory;

import static org.testng.Assert.assertTrue;

public class BookingWithFailDataCardTest {
    static final Logger logger = LogManager.getLogger(BookingLastStepTest.class);
    ConfirmReservationPage confirmReservationPage;
    private WebDriver driver;

    @BeforeClass(description = "Reservation last step")
    @Parameters({"COUNTRY", "PHONE", "CITY", "ADDRESS", "CARD_TYPE", "CARD_NUMBER",
            "EXPIRATION_DATE_MONTH", "EXPIRATION_INVALID_DATE_YEAR", "CARD_CVC", "INVALID_CARD_USER_INDEX", "CONFIRM_RESERVATION_PAGE_INDEX"})
    public void setup(String country, String phone, String city, String address, String cardType, String cardNumber,
                      String expirationDateMonth, String expirationDateYear, String cvc, int userIndex, int pageIndex) {
        logger.log(Level.INFO, "BeforeClass for tests.BookingWithFailDataCardTest");
        driver = DriverFactory.getInstance().getDriver();
        confirmReservationPage = (ConfirmReservationPage) PageList.getInstance().getPages().get(pageIndex);

        UserList.getInstance().getUsers().add(new User()); // Create workflow user object with 1 index
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
    }

    @Test(groups = {"failReservation"}, dependsOnGroups = {"bookingDetails"})
    @Parameters({"INVALID_CARD_USER_INDEX"})
    public void bookWithoutCard(int userIndex) {
        logger.log(Level.INFO, "tests.BookingWithFailDataCardTest");

        confirmReservationPage.setFieldStrategy();
        if (confirmReservationPage.getFieldStrategy().getClass() != ReservationWithoutCardImpl.class) {
            logger.log(Level.INFO, "Card is needed for booking");
            confirmReservationPage.setDataWithInterface(UserList.getInstance().getUsers().get(userIndex)).clickCompleteFailBookingButton();
            ConfirmReservationWithCardPage confirmReservationWithCardPage = new ConfirmReservationWithCardPage();
            assertTrue(confirmReservationWithCardPage.isInvalidExpiration());
        }
        else logger.log(Level.INFO, "Card is no needed for booking, tests.BookingWithFailDataCardTest was skipped");
    }

    @AfterClass(description = "Reservation is completed")
    public void teardown() throws UnsupportedDriverNameException, MissingFileException {
        driver = null;
        confirmReservationPage = null;
    }

}
