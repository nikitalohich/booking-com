package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entity.PageContainer;
import entity.UserSingleton;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import page.HotelFilterPage;
import page.MainPage;

public class SearchWithoutDatesStep {
    private MainPage mainPage = (MainPage) PageContainer.getInstance().getPage();
    private Logger logger = LogManager.getLogger();

    @Given("^I am on 'Main' page$")
    public void onMainPage() {
        Assert.assertTrue(mainPage.isPageOpened());
    }

    @When("^I input 'destination' into 'Destination' field and I click on 'search button'$")
    public void iInputDestinationIntoDestinationFieldAndIClickOnSearchButton() {
        logger.log(Level.INFO, "Valid destination input.");
        mainPage.setDestination(UserSingleton.getInstance().getUser().getTrip().getDestination());
        HotelFilterPage hotelFilterPage = mainPage.clickSearchButton();

        //todo drop this crutch
        int counter = 1;
        while (mainPage.isPageOpened()) {
            mainPage.clickSearchButton();
            counter++;
            if (counter > 10) throw new IllegalStateException("Cannot click on Search button");
        }
        logger.debug("Search button was clicked at its " + counter + " attempt.");

        PageContainer.getInstance().updatePage(hotelFilterPage);
    }

    @Then("^'HotelFilter' page should be opened and 'choose dates message' is visible$")
    public void hotelfilterPageShouldBeOpenedAndChooseDatesMessageIsVisible() {
        HotelFilterPage page = (HotelFilterPage) PageContainer.getInstance().getPage();
        Assert.assertTrue(page.isPageOpened() && !page.getChooseDateMessage().isEmpty());
    }
}
