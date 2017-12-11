package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entity.PageContainer;
import entity.UserSingleton;
import org.openqa.selenium.WebDriverException;
import page.BookingConfirmationPage;
import page.ConfirmReservationPage;

import static org.testng.Assert.assertTrue;

public class ConfirmReservationStep {

    private ConfirmReservationPage confirmReservationPage = (ConfirmReservationPage) PageContainer.getInstance().getPage();

    @When("^I input valid data$")
    public void iEnterValidDates() {
        confirmReservationPage.setFieldStrategy();
        confirmReservationPage.setDataWithInterface(UserSingleton.getInstance().getUser());
    }

    @And("^I submit the form$")
    public void iSubmitTheForm() {
        try {
            confirmReservationPage.clickCompleteSuccessBookingButton();
        } catch (WebDriverException e) {
            confirmReservationPage.clickCompleteSuccessBookingButton();
        }
        PageContainer.getInstance().updatePage(new BookingConfirmationPage());
    }

    @Then("^Booking Confirmation page should be opened$")
    public void bookingConfirmationPageShouldBeOpened() throws Throwable {
        assertTrue(PageContainer.getInstance().getPage().isPageOpened());
    }

    @Then("^'Confirm Reservation' page should be opened$")
    public void confirmReservationPageShouldBeOpened() {
        assertTrue(confirmReservationPage.isPageOpened());
    }
}

