package steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entity.PageContainer;
import page.BookingConfirmationPage;
import page.CanceledBookingPage;
import page.ManageBookingPage;

import static org.testng.Assert.assertTrue;

public class CancelBookingStep {

    private BookingConfirmationPage bookingConfirmationPage = (BookingConfirmationPage)PageContainer.getInstance().getPage();

    @When("^I cancel the booking$")
    public void iCancelTheBooking() {
        bookingConfirmationPage.closeCreatePasswordPopUp();
        ManageBookingPage manageBookingPage = bookingConfirmationPage.cancelBooking();
        manageBookingPage.clickCancelBookingButton();
        CanceledBookingPage canceledBookingPage = manageBookingPage.setCancelReason().clickCancelPopUpButton().clickOkPopUpButton();
        PageContainer.getInstance().updatePage(canceledBookingPage);
    }

    @Then("^Message telling me the booking was canceled is visible$")
    public void messageTellingMeTheBookingWasCanceledIsVisible() {
        assertTrue(PageContainer.getInstance().getPage().isPageOpened());
    }
}
