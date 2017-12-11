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
import page.HotelPage;
import util.DriverFactory;

public class CheckSelectedRooms {

    static final Logger logger = LogManager.getLogger(CheckSelectedRooms.class);
    private WebDriver driver;
    private HotelPage hotelPage;

    @BeforeClass
    @Parameters({"HOTEL_PAGE_INDEX"})
    public void start(int hotelPageIdex) throws MissingFileException, UnsupportedDriverNameException {
        logger.log(Level.INFO, "Try to get webdriver instance from server");
        driver = DriverFactory.getInstance().getDriver();
        logger.log(Level.INFO, "Web driver instance success");
        hotelPage = (HotelPage)PageList.getInstance().getPages().get(hotelPageIdex);

    }

    @Test(groups = "selectRooms", dependsOnGroups = "incorrectSelect")
    public void checkInformationAboutReservation() throws UnsupportedDriverNameException, MissingFileException {
        hotelPage.selectCountRoom();
        boolean result = hotelPage.checkSelectedRoomsAndPrice();
        Assert.assertEquals(result, true);
    }

    @AfterClass
    public void kill() throws MissingFileException, UnsupportedDriverNameException{
        driver = null;
        hotelPage = null;
        logger.log(Level.INFO, "tests.CheckSelectedRooms test successfully complete!");
    }

}
