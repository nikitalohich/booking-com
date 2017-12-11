package tests;

import exception.MissingFileException;
import exception.UnsupportedDriverNameException;
import list.PageList;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import page.BookingConfirmationPage;
import page.CanceledBookingPage;
import page.ManageBookingPage;
import util.DriverFactory;


import static org.testng.Assert.assertTrue;

public class CancelBookingTest {
    static final Logger logger = LogManager.getLogger(CancelBookingTest.class);

    BookingConfirmationPage bookingConfirmationPage;
    ManageBookingPage manageBookingPage;
    CanceledBookingPage canceledBookingPage;

    private WebDriver driver;

    @BeforeClass(description = "Cancel booking")
    @Parameters({"BOOKING_CONFIRMATION_PAGE_INDEX"})
    public void setup(int pageIndex) {
        logger.log(Level.INFO, "BeforeClass for tests.CancelBookingTest");
        driver = DriverFactory.getInstance().getDriver();
        bookingConfirmationPage = (BookingConfirmationPage) PageList.getInstance().getPages().get(pageIndex);
    }

    @Test(groups = {"cancelBooking"}, dependsOnGroups = {"successfulReservation"})
    public void bookWithoutCard() {
        logger.log(Level.INFO, "tests.CancelBookingTest");
        bookingConfirmationPage.closeCreatePasswordPopUp();

        manageBookingPage = bookingConfirmationPage.cancelBooking();
        PageList.getInstance().getPages().add(manageBookingPage);
        manageBookingPage.clickCancelBookingButton();
        canceledBookingPage = manageBookingPage.setCancelReason().clickCancelPopUpButton().clickOkPopUpButton();
        PageList.getInstance().getPages().add(canceledBookingPage);
        assertTrue(canceledBookingPage.isPageOpened());
    }

    @AfterClass(description = "Booking was canceled")
    public void teardown() throws UnsupportedDriverNameException, MissingFileException {
        driver = null;
        bookingConfirmationPage = null;
        manageBookingPage = null;
        canceledBookingPage = null;
        logger.log(Level.INFO, "End of tests.CancelBookingTest");
    }

}