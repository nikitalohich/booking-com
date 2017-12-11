package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entity.PageContainer;
import org.openqa.selenium.By;
import org.testng.Assert;
import page.MainPage;
import testutil.Screenshoter;

public class SearchDestinationStep {

    private MainPage mainPage = (MainPage) PageContainer.getInstance().getPage();

    @Given("^I am on the main page$")
    public void checkMainPage() {
        Assert.assertTrue(mainPage.isPageOpened());
    }

    @When("^I try to choose invalid destination$")
    public void chooseInvalidDestination() {
        mainPage.setDestination("!!!").clickSearchButton();
    }

    @Then("^Page should have message consistent with pattern: (.*)$")
    public void checkErrorMessage(String pattern) {
        Screenshoter.takeWithHighlight(By.xpath(".//*[@class='sb-searchbox__error -visible']"));
        Assert.assertTrue(mainPage.getAlertMessage().trim().matches(pattern));
    }
}
