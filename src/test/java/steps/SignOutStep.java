package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entity.PageContainer;
import org.testng.Assert;
import page.AbstractPage;
import util.DriverFactory;

public class SignOutStep {

    private AbstractPage page = PageContainer.getInstance().getPage();

    @Given("^I am logged in$")
    public void checkUserLoggedIn() {
        Assert.assertTrue(page.isLoggedIn());
    }

    @When("^I try to sign out$")
    public void tryToSignOut()  {
        DriverFactory.getInstance().getDriver().get("https://booking.com");
        page.yourAccountClick();
        page.signOutButtonClick();
    }

    @Then("^I am not logged in anymore$")
    public void checkUserLoggedOff()  {
        Assert.assertTrue(page.isLoggedOut());
    }
}
