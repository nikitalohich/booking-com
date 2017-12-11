package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import entity.Card;
import entity.PageContainer;
import entity.UserSingleton;
import org.openqa.selenium.By;
import page.ConfirmReservationPage;
import page.ConfirmReservationWithCardPage;
import page.strategy.ReservationWithoutCardImpl;
import testutil.Screenshoter;

import static org.testng.Assert.assertTrue;

public class BookingWithExpiredCardStep {

    private ConfirmReservationPage confirmPage = (ConfirmReservationPage) PageContainer.getInstance().getPage();
    private ConfirmReservationWithCardPage confirmCardPage;

    @Given("^I am on the confirm reservation page$")
    public void checkConfirmReservationPage()  {
        assertTrue(confirmPage.isPageOpened());
    }

    @Given("^I try to book a room with the card expired (.*)/(.*)$")
    public void tryBookWithExpiredCard(String month, String year) {
        Card actualCard = UserSingleton.getInstance().getUser().getCard();
        Card expiredCard = new Card(actualCard.getCardType(), actualCard.getCardNumber(), month, year, actualCard.getCvc());
        confirmPage.setFieldStrategy();
        if (!(confirmPage.getFieldStrategy() instanceof ReservationWithoutCardImpl))
            confirmCardPage = new ConfirmReservationWithCardPage().setCardData(expiredCard);
    }

    @Then("^Page should contain the expiration date notification$")
    public void checkExpirationNotification() {
        if (!(confirmPage.getFieldStrategy() instanceof ReservationWithoutCardImpl)) {
            Screenshoter.takeWithHighlight(By.className("pay-form--wrapper"));
            assertTrue(confirmCardPage.isInvalidExpiration());
        }
    }
}
