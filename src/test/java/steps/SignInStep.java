package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import entity.PageContainer;
import entity.User;
import entity.UserSingleton;
import list.UserList;
import page.AbstractPage;

import static org.testng.Assert.assertTrue;

public class SignInStep {
    @When("^I sign in as \"([^\"]*)\"$")
    public void iSignInAs(String user) {
        AbstractPage page = PageContainer.getInstance().getPage();
        User actualUser = UserSingleton.getInstance().getUser();

        page.clickSignInButton();
        if (actualUser.getName().equals(user))
            page.enterCredentials(actualUser);
        else
            page.enterCredentials(UserList.getInstance().getUserByName(user));
        page.clickSubmitButton();
    }

    @Given("^I am on 'main' page$")
    public void iAmOnMainPage() {
        assertTrue(PageContainer.getInstance().getPage().isPageOpened());
    }
}