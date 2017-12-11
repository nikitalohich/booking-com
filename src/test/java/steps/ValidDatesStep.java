package steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entity.PageContainer;
import entity.UserSingleton;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import page.HotelFilterPage;
import util.DriverFactory;

public class ValidDatesStep {
    private HotelFilterPage hotelFilterPage = (HotelFilterPage) PageContainer.getInstance().getPage();
    private String CIN_HOTELPAGE = ".//div[contains(@class, 'sb-searchbox__outer')]//form[@data-component='search/searchbox/searchbox']" +
            "//div[contains(@class, 'sb-date-field__display') and contains(@data-placeholder, 'in')]";
    private Logger logger = LogManager.getLogger();
    private  boolean datesEqual;
    private  WebDriver driver = DriverFactory.getInstance().getDriver();

    @When("^I input 'check-in' into 'Check-In' field and I click on 'search button'$")
    public void iInputCheckInIntoCheckInFieldAndIClickOnSearchButton() {
        hotelFilterPage.enterDates(UserSingleton.getInstance().getUser());
        WebElement contentBeforeSearch = driver.findElement(By.xpath(CIN_HOTELPAGE));
        String expectedCheckInDate = contentBeforeSearch.getText();
        logger.log(Level.INFO, "Expected dates:"+expectedCheckInDate);
        hotelFilterPage.clickSearchButton();
        WebElement contentAfterSearch = driver.findElement(By.xpath(CIN_HOTELPAGE));
        String actualCheckInDate = contentAfterSearch.getText();
        logger.log(Level.INFO, "Actual dates:"+actualCheckInDate);
        datesEqual = expectedCheckInDate.equals(actualCheckInDate);

    }

    @Then("^'HotelFilter' page should be opened and valid date remain the same$")
    public void hotelfilterPageShouldBeOpenedAndValidDateRemainTheSame() {
        Assert.assertTrue(hotelFilterPage.isPageOpened() && datesEqual);
    }
}