package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import entity.PageContainer;

import static org.testng.Assert.assertTrue;

public class CommonStep {
    @Given("^I am logged off$")
    public void iAmLoggedOff() {
        assertTrue(PageContainer.getInstance().getPage().isLoggedOut());
    }

    @Then("^I am signed in and notifications are visible$")
    public void iAmSignedInAndMandatoryElementsAreVisible() {
        assertTrue(PageContainer.getInstance().getPage().isLoggedIn());
    }

    @Then("^I am signed in$")
    public void iAmSignedIn() {
        assertTrue(PageContainer.getInstance().getPage().isLoggedIn());
        PageContainer.getInstance().getPage().yourAccountClick();
        PageContainer.getInstance().getPage().signOutButtonClick();
    }
}
