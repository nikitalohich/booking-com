package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import entity.PageContainer;
import entity.UserSingleton;

/**
 * Created by Nikita_Lohich on 9/25/2017
 */
public class RegistrationStep {

    @When("^I click to the registration button$")
    public void iClickToTheRegistrationButton()  {
        PageContainer.getInstance().getPage().clickRegisterButton();
    }

    @And("^I input user credentials and click submit button$")
    public void iInputCredentialsAndClickSubmitButton()  {
        PageContainer.getInstance().getPage().enterCredentialsForRegistration(UserSingleton.getInstance().getUser());
        PageContainer.getInstance().getPage().clickSubmitRegistrationButton();

    }
}
