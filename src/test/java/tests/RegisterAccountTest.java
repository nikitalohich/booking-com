package tests;

import entity.Trip;
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
import page.MainPage;
import util.DriverFactory;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

public class RegisterAccountTest {

    private MainPage mainPage;
    private Logger logger = LogManager.getLogger();
    private WebDriver driver;

    @BeforeClass(description = "init getPage, start browser, setup timeouts")
    @Parameters({"IMPL_TIME_OUT", "LOAD_TIME_OUT", "EMAIL", "PASSWORD", "USER_TOREGISTER_INDEX", "MAIN_PAGE_INDEX"})
    public void setUp(int implTimeOut, int loadTimeOut, String email, String password, int userToRegisterIndex, int mainPageIndex) throws UnsupportedDriverNameException, MissingFileException {

        Trip trip = new Trip();
        trip.setDestination("Warsaw");

        PageList.getInstance().getPages().add(new MainPage());
        UserList.getInstance().getUsers().add(new User());
        UserList.getInstance().getUsers().get(userToRegisterIndex).setEmail(email);
        UserList.getInstance().getUsers().get(userToRegisterIndex).setPassword(password);
        UserList.getInstance().getUsers().get(userToRegisterIndex).setTrip(trip);
        UserList.getInstance().getUsers().get(userToRegisterIndex).getTrip().setDestination("Warsaw");

        driver = DriverFactory.getInstance().getDriver();
        mainPage = (MainPage) PageList.getInstance().getPages().get(mainPageIndex); // casting because of AbstractPage type in list

        driver.manage().timeouts().implicitlyWait(implTimeOut, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(loadTimeOut, TimeUnit.SECONDS);

        logger.log(Level.INFO, "WD instance launched successfully, registration test is running...");
    }

    @Test(groups = "reg")
    @Parameters({"USER_TOREGISTER_INDEX"})
    public void registrationTest(int userToRegisterIndex) {
        driver.get("https://www.booking.com/");
        mainPage.clickRegisterButton();
        mainPage.enterCredentialsForRegistration(UserList.getInstance().getUsers().get(userToRegisterIndex));
        mainPage.clickSubmitRegistrationButton();

        assertTrue(mainPage.isLoggedIn());
    }

    @AfterClass
    public void tearDown() {
//        MainPage and driver instance is going to the next test, don't close them
//        mainPage = null;
//        driver.quit();
        logger.log(Level.INFO, "Registration test passed successfully");
    }
}
