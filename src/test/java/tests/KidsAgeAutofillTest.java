package tests;

import entity.User;
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
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import page.MainPage;
import util.DriverFactory;

import java.util.concurrent.TimeUnit;

public class KidsAgeAutofillTest {

    private MainPage mainPage;
    private Logger logger = LogManager.getLogger();
    private WebDriver driver;

    @BeforeClass(description = "init getPage, start browser, setup timeouts")
    @Parameters({"IMPL_TIME_OUT", "LOAD_TIME_OUT", "MAIN_PAGE_INDEX"})
    public void setUp(int implTimeOut, int loadTimeOut, int mainPageIndex) throws UnsupportedDriverNameException, MissingFileException {

        driver = DriverFactory.getInstance().getDriver();
        driver.manage().timeouts().implicitlyWait(implTimeOut, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(loadTimeOut, TimeUnit.SECONDS);
        mainPage = (MainPage) PageList.getInstance().getPages().get(mainPageIndex);
        logger.log(Level.INFO, "WD instance launched successfully, test is running..");
    }

    @Test(dependsOnGroups = "reg", groups = "kidsAge")
    @Parameters({"EXPECTED_KIDS_AGE"})
    public void ageAutofillTest(String expectedKidsAge) throws UnsupportedDriverNameException, MissingFileException {

        driver.get("https://booking.com");
        User user = UserList.getInstance().getUsers().get(0);
        mainPage.enterCity(user);
        mainPage.closeDatesForm();
        mainPage.fillKidsQuantity();
        mainPage.clickSearchButton();

        Select kidsAgeForm = new Select(driver.findElement(By.xpath("//*[@id='frm']//div[4]/select")));
        WebElement kidsAgeDropdownMenu = kidsAgeForm.getFirstSelectedOption();
        String actualKidsAge = kidsAgeDropdownMenu.getText();
        Assert.assertEquals(expectedKidsAge, actualKidsAge);
    }

    @AfterClass
    public void tearDown() {
        mainPage = null;
        driver = null;
        logger.log(Level.INFO, "Kids age autofill test passed successfully");
    }
}
