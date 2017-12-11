package tests;

import entity.Trip;
import exception.MissingFileException;
import exception.UnsupportedDriverNameException;
import list.PageList;
import list.UserList;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import page.HotelFilterPage;
import util.DriverFactory;

public class ValidDatesTest {

    private HotelFilterPage hotelFilterPage;
    private Logger logger = LogManager.getLogger();
    private String CIN_HOTELPAGE = ".//div[contains(@class, 'sb-searchbox__outer')]//form[@data-component='search/searchbox/searchbox']//div[contains(@class, 'sb-date-field__display') and contains(@data-placeholder, 'in')]";
    private WebDriver driver;

    @BeforeClass
    @Parameters({"IMPL_TIME_OUT", "LOAD_TIME_OUT", "WORKFLOW_USER_INDEX", "HOTEL_FILTER_PAGE_INDEX", "TRIP-DESTINATION", "TRIP-CHECKIN", "TRIP-CHECKOUT"})
    public void setUp(int implTimeOut, int loadTimeOut, int userIndex, int pageIndex, String tripDestination, String checkIn, String checkOut) throws UnsupportedDriverNameException, MissingFileException {
        UserList.getInstance().getUsers().get(userIndex).setTrip(new Trip(tripDestination, checkIn, checkOut));
        logger.log(Level.INFO, "Try to get webdriver instance from server");
        driver = DriverFactory.getInstance().getDriver();
        logger.log(Level.INFO, "Webdriver instance successfully acquired");
        hotelFilterPage = (HotelFilterPage) PageList.getInstance().getPages().get(pageIndex);
    }

    @Test(description = "workflow", dependsOnGroups = "searchInvalid", groups = "searchValid")
    @Parameters({"WORKFLOW_USER_INDEX"})
    public void searchValid(int userId) throws UnsupportedDriverNameException, MissingFileException {
        logger.log(Level.INFO, "Valid destination & date input attempt.");
        hotelFilterPage.enterDates(UserList.getInstance().getUsers().get(userId));
        WebElement contentBeforeSearch = driver.findElement(By.xpath(CIN_HOTELPAGE));
        String expectedCheckInDate = contentBeforeSearch.getText();
        logger.log(Level.INFO, "Expected dates:"+expectedCheckInDate);
        logger.log(Level.INFO, "Search button click.");
        hotelFilterPage.clickSearchButton();
        WebElement contentAfterSearch = driver.findElement(By.xpath(CIN_HOTELPAGE));
        String actualCheckInDate = contentAfterSearch.getText();
        logger.log(Level.INFO, "Actual dates:"+actualCheckInDate);
        logger.log(Level.INFO, "Valid destination & date input succeed.");
         Assert.assertEquals(expectedCheckInDate, actualCheckInDate);
    }

    @AfterClass
    public void tearDown() throws UnsupportedDriverNameException, MissingFileException {
        hotelFilterPage = null;
        logger.log(Level.INFO, "Valid dates test passed successfully!");
    }
}