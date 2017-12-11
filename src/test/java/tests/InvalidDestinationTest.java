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
import page.MainPage;
import util.DriverFactory;

public class InvalidDestinationTest {
    private String ALERT_TEXT_LOCATOR = ".//*[@class='sb-searchbox__error -visible']";
    private String DESTINATION = "!!!";
    private MainPage mainPage;
    private Logger logger = LogManager.getLogger();
    private WebDriver driver;
    private String CITY_INPUT = "//div[contains(@class, 'sb-searchbox__outer')]//input[@id='ss']";

    @BeforeClass
    @Parameters({"IMPL_TIME_OUT", "LOAD_TIME_OUT", "WORKFLOW_USER_INDEX", "MAIN_PAGE_INDEX", "TRIP-DESTINATION", "TRIP-CHECKIN", "TRIP-CHECKOUT"})
    public void setUp(int implTimeOut, int loadTimeOut, int userIndex, int pageIndex, String tripDestination, String checkIn, String checkOut) throws UnsupportedDriverNameException, MissingFileException {
        UserList.getInstance().getUsers().get(userIndex).setTrip(new Trip(DESTINATION, checkIn, checkOut));
        logger.log(Level.INFO, "Try to get webdriver instance from server");
        driver = DriverFactory.getInstance().getDriver();
        logger.log(Level.INFO, "Webdriver instance successfully acquired");
        mainPage = (MainPage) PageList.getInstance().getPages().get(pageIndex);
    }


    @Test(description = "workflow", dependsOnGroups = "signintest", groups = "searchInvalid")
    @Parameters({"WORKFLOW_USER_INDEX"})
    public void invalidDestinationTest(int userId) throws UnsupportedDriverNameException, MissingFileException {
        driver.get("https://booking.com");
        logger.log(Level.INFO, "Invalid destination input attempt.");
        WebElement input = driver.findElement(By.xpath(CITY_INPUT));
        input.clear();
        input.click();
        input.sendKeys(UserList.getInstance().getUsers().get(userId).getTrip().getDestination());
        logger.log(Level.INFO, "Search button click.");
        mainPage.clickSearchButton();
        logger.log(Level.INFO, "Invalid destination input succeed.");
        WebElement divContent = driver.findElement(By.xpath(ALERT_TEXT_LOCATOR));
        String alertMessage = divContent.getText();
        logger.log(Level.INFO, "Error message tells: '" + alertMessage + "'");
        Assert.assertTrue(!(alertMessage.isEmpty()));
    }


    @AfterClass
    public void tearDown() {
        driver = null;
        mainPage = null;
        logger.log(Level.INFO, "Invalid destination test passed successfully!");
    }
}
