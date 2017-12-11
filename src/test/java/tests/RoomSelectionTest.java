package tests;

import exception.MissingFileException;
import exception.UnsupportedDriverNameException;
import list.PageList;
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
import page.HotelPage;
import util.DriverFactory;


public class RoomSelectionTest {

    static final Logger logger = LogManager.getLogger(RoomSelectionTest.class);
    private WebDriver driver;
    private HotelPage hotelPage;
    private BookingDetailsPage bookingDetailsPage;

    @BeforeClass()
    @Parameters({"HOTEL_PAGE_INDEX"})
    public void start(int hotelPageIdex) throws MissingFileException, UnsupportedDriverNameException {
        logger.log(Level.INFO, "Try to get webdriver instance from server");
        driver = DriverFactory.getInstance().getDriver();
        logger.log(Level.INFO, "Web driver instance success");
        hotelPage = (HotelPage)PageList.getInstance().getPages().get(hotelPageIdex);
        logger.log(Level.INFO, "Get HotelPage from PageList");
    }

    @Test(dependsOnGroups = "selectRooms" ,groups = "roomSelect")
    public void checkSelectedRoomsAndPrice () throws UnsupportedDriverNameException, MissingFileException {

        bookingDetailsPage = hotelPage.clickForConfirmedReservation();
        PageList.getInstance().getPages().add(bookingDetailsPage);
        bookingDetailsPage.closePopUp();
        String title = hotelPage.getTitle();
        String checkInDate = hotelPage.getCheckIn();
        String checkOutDate = hotelPage.getCheckOut();
        String totalPrice = hotelPage.getTotalPrice();
        boolean result = bookingDetailsPage.checkActualInformationAboutReservation(title,checkInDate,checkOutDate,totalPrice);
        Assert.assertEquals(result, true);

    }

    @AfterClass
    public void kill() throws MissingFileException, UnsupportedDriverNameException{
        driver = null;
        hotelPage = null;
        bookingDetailsPage = null;
        logger.log(Level.INFO, "tests.RoomSelectionTest test successfully complete!");
    }


}
