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
import page.CanceledBookingPage;
import util.DriverFactory;

import static org.testng.Assert.assertTrue;

public class SignOutTest {
    static final Logger logger = LogManager.getLogger(SignInTest.class);
    private WebDriver driver;
    private CanceledBookingPage cancelBookingPage;

    @BeforeClass
    @Parameters({"CANCEL_BOOKING_PAGE_INDEX"})
    public void setup(int pageIndex) throws UnsupportedDriverNameException, MissingFileException {
        logger.log(Level.INFO, "Try to get webdriver instance from server");
        driver = DriverFactory.getInstance().getDriver();
        logger.log(Level.INFO, "Try to get CancelBookingPage object from PageList instance");
        cancelBookingPage = (CanceledBookingPage) PageList.getInstance().getPages().get(pageIndex);
    }

    @Test(dependsOnGroups = "cancelBooking")
    public void signOutTest() {
        cancelBookingPage.yourAccountClick();
        cancelBookingPage.signOutButtonClick();
        assertTrue(cancelBookingPage.isLoggedOut());
    }

    @AfterClass
    public void teardown() {
        driver = null;
        cancelBookingPage = null;
    }
}
