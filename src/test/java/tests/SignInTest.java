package tests;

import entity.User;
import exception.MissingFileException;
import exception.UnsupportedDriverNameException;
import list.PageList;
import list.UserList;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import page.MainPage;
import util.DriverFactory;

import static org.testng.Assert.assertTrue;

public class SignInTest {
    static final Logger logger = LogManager.getLogger(SignInTest.class);
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeClass(description = "Start browser")
    @Parameters({"EMAIL", "PASSWORD", "WORKFLOW_USER_INDEX", "MAIN_PAGE_INDEX"})
    public void setup(String email, String password, int userIndex, int mainPageIndex)
            throws UnsupportedDriverNameException, MissingFileException {
        mainPage = (MainPage)PageList.getInstance().getPages().get(mainPageIndex);
        mainPage.yourAccountClick();
        mainPage.signOutButtonClick();
        UserList.getInstance().getUsers().add(new User()); // Create workflow user object with 0 index
        UserList.getInstance().getUsers().get(userIndex).setEmail(email);
        UserList.getInstance().getUsers().get(userIndex).setPassword(password);
        logger.log(Level.INFO, "Try to get webdriver instance from server");
        driver = DriverFactory.getInstance().getDriver();
        logger.log(Level.INFO, "Web driver instance successfully acquired");
    }

    @Test(groups={"signintest"}, dependsOnGroups = "kidsAge")
    @Parameters({"WORKFLOW_USER_INDEX"})
    public void signInTest(int userIndex) {
        driver.get("https://booking.com");
        mainPage.clickSignInButton();
        mainPage.enterCredentials(UserList.getInstance().getUsers().get(userIndex));
        mainPage.clickSubmitButton();
        assertTrue(mainPage.isLoggedIn());
    }

    @AfterClass(description = "Sign in complete")
    public void teardown() throws UnsupportedDriverNameException, MissingFileException {
        driver = null;
        logger.log(Level.INFO, "Sign in test complete!");
    }

    @AfterSuite
    public void afterSuite() throws UnsupportedDriverNameException, MissingFileException {
        DriverFactory.getInstance().getDriver().navigate().refresh();
        try {
            DriverFactory.getInstance().getDriver().switchTo().alert().accept();
            logger.log(Level.INFO, "Quit WebDriver with alert");
        } catch (NoAlertPresentException ex) {
            logger.log(Level.INFO, "Quit WebDriver without alert");
        }
        DriverFactory.getInstance().getDriver().quit();
    }
}