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
import page.MainPage;
import util.DriverFactory;

public class SearchWithoutDatesTest {

    private MainPage mainPage;
    private HotelFilterPage hotelFilterPage;
    private Logger logger = LogManager.getLogger();
    private String DATES_MESSAGE = ".//*[@id='right']//p[contains(@class, 'dates_rec_sr__title')]";
    private WebDriver driver;

    @BeforeClass
    @Parameters({"IMPL_TIME_OUT", "LOAD_TIME_OUT", "WORKFLOW_USER_INDEX", "MAIN_PAGE_INDEX", "TRIP-DESTINATION", "TRIP-CHECKIN", "TRIP-CHECKOUT"})
    public void setUp(int implTimeOut, int loadTimeOut, int userIndex, int pageIndex, String tripDestination, String checkIn, String checkOut) throws UnsupportedDriverNameException, MissingFileException {
        //TODO required field may not appear with different cities
        UserList.getInstance().getUsers().get(userIndex).setTrip(new Trip(tripDestination, checkIn, checkOut));
        logger.log(Level.INFO, "Try to get webdriver instance from server");
        driver = DriverFactory.getInstance().getDriver();
        logger.log(Level.INFO, "Webdriver instance successfully acquired");
        mainPage = (MainPage) PageList.getInstance().getPages().get(pageIndex);
    }

    @Test(description = "workflow", dependsOnGroups = "searchInvalid", groups = "searchNoDates")
    @Parameters({"WORKFLOW_USER_INDEX"})
    public void searchValid(int userId) throws UnsupportedDriverNameException, MissingFileException {
        logger.log(Level.INFO, "Valid destination input attempt.");
        mainPage.enterCity(UserList.getInstance().getUsers().get(userId));
        mainPage.closeDatesForm();
        logger.log(Level.INFO, "Search button click.");
        hotelFilterPage = mainPage.clickSearchButton();
        logger.log(Level.INFO, "Valid destination input succeed.");
        PageList.getInstance().getPages().add(hotelFilterPage);
        WebElement divContent = driver.findElement(By.xpath(DATES_MESSAGE));
        String datesMessage = divContent.getText();
        logger.log(Level.INFO, "Dates selection message tells: '"+datesMessage+"'");
        Assert.assertTrue(!(datesMessage.isEmpty()));
    }

    @AfterClass
    public void tearDown() throws UnsupportedDriverNameException, MissingFileException {
        hotelFilterPage = null;
        logger.log(Level.INFO, "Without dates test passed successfully!");
    }
}
