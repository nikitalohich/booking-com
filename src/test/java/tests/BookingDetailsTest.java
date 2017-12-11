package tests;

import exception.MissingFileException;
import exception.UnsupportedDriverNameException;
import list.PageList;
import list.UserList;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import page.BookingDetailsPage;
import page.ConfirmReservationPage;
import parser.StringDataParser;
import util.DriverFactory;

import java.util.Date;

public class BookingDetailsTest {
    private WebDriver driver;
    private BookingDetailsPage bookingDetailsPage;
    static final Logger logger = LogManager.getLogger(BookingDetailsTest.class);

    @BeforeClass
    @Parameters({"BOOKING_DETAILS_PAGE_INDEX"})
    public void setupDriver(int bookingDetailsPageIndex) throws UnsupportedDriverNameException, MissingFileException {
        UserList.getInstance().getUsers().get(0).setName("Ivan");
        UserList.getInstance().getUsers().get(0).setLastName("Ivanov");
        UserList.getInstance().getUsers().get(0).setTitle("Mr");
        driver = DriverFactory.getInstance().getDriver();
        bookingDetailsPage = (BookingDetailsPage) PageList.getInstance().getPages().get(bookingDetailsPageIndex);
    }

    @AfterClass
    public void destruct() throws MissingFileException, UnsupportedDriverNameException {
        driver = null;
    }

    @Test(dependsOnGroups = "roomSelect", groups = "DetailsTesting")
    public void isFillingRequired() {
        Assert.assertTrue(bookingDetailsPage.isPageOpened());
        bookingDetailsPage.cleanAllFields();
        Assert.assertTrue(bookingDetailsPage.isEmptyFormsAreRequiredToFilling());
        logger.log(Level.INFO, "Fields filling is required");
    }

    @Test(dependsOnGroups = "roomSelect", groups = "DetailsTesting")
    public void isGuestNameFieldPresent() {
        Assert.assertTrue(bookingDetailsPage.isFillingGuestNameAvailable("Petr Petrov"));
        logger.log(Level.INFO, "Filling guest name is available");
    }

    @Test(dependsOnGroups = "roomSelect", groups = "DetailsTesting")
    public void isMailRegexValidForNotEmail() {
        Assert.assertTrue(bookingDetailsPage.isMailRegexValid("Not Valid Email"));
        logger.log(Level.INFO, "Regex for not valid email is valid");
    }

    @Test(dependsOnGroups = "roomSelect", groups = "DetailsTesting")
    public void isMailRegexValidForWrongEmail() {
        Assert.assertTrue(bookingDetailsPage.isMailRegexValid("maybenotvalid@test.test.test"));
        logger.log(Level.INFO, "Regex for perhaps wrong emails is valid");
    }

    @Test(dependsOnGroups = "roomSelect", groups = "DetailsTesting")
    public void isBookingWarningMessagePresents() {
        Assert.assertTrue(bookingDetailsPage.cleanAllFields().submitInvalidDetails().isBookingWarningMessagePresents());
        logger.log(Level.INFO, "Booking warning message is presents");
    }


    @Test(dependsOnGroups = "roomSelect", groups = "DetailsTesting")
    public void changeToPastDateIsUnavailable() {
        bookingDetailsPage.openChangingDatesSection();
        String pastDate = bookingDetailsPage.getCheckInDateFromChangesDate();
        bookingDetailsPage.changeDates(new Date().getDate() - 1);
        String dateAfterChanging = bookingDetailsPage.getCheckInDateFromChangesDate();
        Assert.assertEquals(pastDate, dateAfterChanging);
        bookingDetailsPage.submitChangeDates();

    }

    @Test(alwaysRun = true, dependsOnGroups = "roomSelect", groups = "DetailsTesting", dependsOnMethods = "changeToPastDateIsUnavailable")
    public void NextMonthPriceIsLower() {
        Integer beforePrice = StringDataParser.extractNumber(bookingDetailsPage.getTotalPrice());
        bookingDetailsPage.openChangingDatesSection().selectNextMonth().submitChangeDates();
        Integer afterPrice = StringDataParser.extractNumber(bookingDetailsPage.getTotalPrice());
        Assert.assertTrue(beforePrice >= afterPrice);
        logger.log(Level.INFO, String.format("Current month hotel price = %d, Next month hotel price = %d", beforePrice, afterPrice ));
    }

    @Test(alwaysRun = true, groups = "bookingDetails", dependsOnGroups = "DetailsTesting")
    public void EnterValidDetailsAndConfirm() {
        ConfirmReservationPage confirmReservationpage = bookingDetailsPage.
                enterBookingCredentials(UserList.getInstance().getUsers().get(0)).submitValidDetails();
        PageList.getInstance().getPages().add(confirmReservationpage);
    }


}
