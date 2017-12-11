package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entity.PageContainer;
import org.testng.Assert;
import page.HotelPage;

public class CheckSelectionStep {
    private HotelPage hotelPage = (HotelPage) PageContainer.getInstance().getPage();

    @Given("^I am on hotel page$")
    public void onHotelPage() {
        Assert.assertTrue(hotelPage.isPageOpened());
    }

    @When("^I choose for booking some room$")
    public void chooseRoomForBooking() {
        hotelPage.selectCountRoom();
    }

    @Then("^Show information about booking, which corresponds to the information about selected room$")
    public void checkDisplayedInformationAboutSelectedRoom() {
        boolean result = hotelPage.checkSelectedRoomsAndPrice();
        Assert.assertEquals(result, true);
    }
}
