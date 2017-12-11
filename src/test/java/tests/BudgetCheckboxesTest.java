package tests;

import entity.User;
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
import page.HotelFilterPage;
import util.DriverFactory;

public class BudgetCheckboxesTest {
    static final Logger logger = LogManager.getLogger(BudgetCheckboxesTest.class);

    private HotelFilterPage hotelFilterPage;
    private WebDriver driver;
    private User user;

    @BeforeClass(description = "Hotel filtering")
    @Parameters({"HOTEL_FILTER_PAGE_INDEX", "WORKFLOW_USER_INDEX", "TRIP-BUDGET"})
    public void setup(int hotelFilterPageIndex, int userIndex, int tripBudget) {
        UserList.getInstance().getUsers().get(userIndex).getTrip().setBudget(tripBudget);
        logger.log(Level.INFO, "Try to get web driver instance from server");
        driver = DriverFactory.getInstance().getDriver();
        hotelFilterPage = (HotelFilterPage) PageList.getInstance().getPages().get(hotelFilterPageIndex);
    }

    @Test(groups = {"hotelFiltering"}, dependsOnGroups = {"searchValid"})
    @Parameters({"WORKFLOW_USER_INDEX"})
    public void verifyCheckboxesMatching(int userIndex) throws UnsupportedDriverNameException, MissingFileException {
        hotelFilterPage.selectFilters(UserList.getInstance().getUsers().get(userIndex), "budget");
        logger.log(Level.INFO, "Filters selected");
        Assert.assertTrue(hotelFilterPage.verifyBudgetSearchResult());
    }

    @AfterClass(description = "Hotel filtering is completed")
    public void teardown() throws UnsupportedDriverNameException, MissingFileException {
        hotelFilterPage = null;
        logger.log(Level.INFO, "Checkboxes are matching to search results");
    }
}
