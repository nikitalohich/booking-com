package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entity.PageContainer;
import org.testng.Assert;
import page.BookingDetailsPage;
import page.HotelPage;

public class CheckBookingInformationStep {

    private HotelPage hotelPage = (HotelPage) PageContainer.getInstance().getPage();
    private BookingDetailsPage bookingDetailsPage;

    @And("^I choose some hotel room$")
    public void iChooseSomeRoom() {
        String price = hotelPage.getTotalPrice();
        Assert.assertTrue(!price.isEmpty());
    }

    @When("^I click on 'Submit reservation button'$")
    public void iClickSubmitReservationButton() {
        bookingDetailsPage = hotelPage.clickForConfirmedReservation();
        PageContainer.getInstance().updatePage(bookingDetailsPage);
    }

    @Then("^I am on booking details page with actual information about reservation$")
    public void checkActualInformationAboutBooking() {
        String title = hotelPage.getTitle();
        String checkInDate = hotelPage.getCheckIn();
        String checkOutDate = hotelPage.getCheckOut();
        String totalPrice = hotelPage.getTotalPrice();
        boolean result = bookingDetailsPage.checkActualInformationAboutReservation(title, checkInDate, checkOutDate, totalPrice);
        Assert.assertEquals(result, true);
    }

}
