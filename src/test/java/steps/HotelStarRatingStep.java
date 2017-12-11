package steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entity.PageContainer;
import entity.UserSingleton;
import org.testng.Assert;
import page.HotelFilterPage;

public class HotelStarRatingStep {

    private HotelFilterPage hotelFilterPage = (HotelFilterPage) PageContainer.getInstance().getPage();

    @When("^I mark 'Star rating' checkboxes fit to Trip star rating$")
    public void iMarkStarRatingCheckboxesFitTo() {
        hotelFilterPage.selectFilters(UserSingleton.getInstance().getUser(), "stars");
    }

    @Then("^Hotels from search list match to selected checkboxes$")
    public void hotelsFromSearchListMatchToSelectedCheckboxes() {
        Assert.assertTrue(hotelFilterPage.verifyStarRatingSearchResult());
        PageContainer.getInstance().updatePage(hotelFilterPage.showAvailable().chooseHotel());
    }

}
