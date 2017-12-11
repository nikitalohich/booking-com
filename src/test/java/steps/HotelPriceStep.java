package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entity.PageContainer;
import entity.UserSingleton;
import org.testng.Assert;
import page.HotelFilterPage;

public class HotelPriceStep {

    private HotelFilterPage hotelFilterPage = (HotelFilterPage) PageContainer.getInstance().getPage();

    @Given("^I am on 'Hotel Filter' page$")
    public void iAmOnHotelFilterPage() {
        Assert.assertTrue(hotelFilterPage.isPageOpened());
    }

    @When("^I mark 'Your budget' checkboxes lower than Trip budget$")
    public void iMarkYourBudgetCheckboxesLowerThan() {
        hotelFilterPage.selectFilters(UserSingleton.getInstance().getUser(), "budget");
    }

    @Then("^Hotels from search list should be match to selected checkboxes$")
    public void hotelsFromSearchListShouldBeMatchToSelectedCheckboxes() {
        Assert.assertTrue(hotelFilterPage.verifyBudgetSearchResult());
    }

}
