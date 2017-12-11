package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entity.PageContainer;
import entity.User;
import entity.UserSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import page.BookingDetailsPage;
import page.ConfirmReservationPage;
import parser.StringDataParser;
import util.DriverFactory;

import java.time.LocalDateTime;
import java.util.Date;

public class BookingDetailsStep {

    private BookingDetailsPage bookingDetailsPage = (BookingDetailsPage) PageContainer.getInstance().getPage();

    @Given("^I am on 'Booking Details' page$")
    public void iAmOnBookingDetailsPage() {
    }

    @When("^I input \"([^\"]*)\" into 'name' field$")
    public void iInputIntoNameField(String name) {
        bookingDetailsPage.setName(name);
    }

    @When("^I input \"([^\"]*)\" into 'last name' field$")
    public void iInputIntoLastNameField(String lastName) {
        bookingDetailsPage.setLastName(lastName);
    }

    @When("^I click on 'submit details button'$")
    public void iClickToSubmitDetailsButton() {
        bookingDetailsPage.submitInvalidDetails();
    }

    @Then("^'warning message about wrong details' is visible$")
    public void theWarningMessageAboutWrongDetailsIsPresents() {
        Assert.assertTrue(bookingDetailsPage.isBookingWarningMessagePresents());
    }

    @When("^I clean all fields$")
    public void iCleanAllFields() {
        bookingDetailsPage.cleanAllFields();
    }

    @Then("^3 and more fields are required to filling$")
    public void ThreeAndMoreFieldsAreRequiredToFilling() {
        Assert.assertTrue(bookingDetailsPage.isEmptyFormsAreRequiredToFilling());
    }


    @When("^I input \"([^\"]*)\" into 'email' field$")
    public void iInputIntoEmailField(String email) {
        bookingDetailsPage.setEmail(email);
    }


    @Then("^'warning message about wrong email' is visible$")
    public void theWarningMessageAboutWrongEmailIsPresents() {
        Assert.assertTrue(bookingDetailsPage.isMailRegexValid(bookingDetailsPage.getMailValue()));
    }


    @When("^I input \"([^\"]*)\" into 'guest name' field$")
    public void iInputIntoGuestNameField(String name) {
        bookingDetailsPage.isFillingGuestNameAvailable(name);
    }

    @When("^I click on 'changes dates' link$")
    public void iClickOnChangesDatesLink() {
        bookingDetailsPage.openChangingDatesSection();
    }

    //TODO: private fields init
    private String pastDate;
    private String dateAfterChanging;
    @And("^I change check-in date to past$")
    public void iChangeCheckInDateToPast() {
        pastDate = bookingDetailsPage.getCheckInDateFromChangesDate();
        bookingDetailsPage.changeDates(new Date().getDate() - 1);
        dateAfterChanging = bookingDetailsPage.getCheckInDateFromChangesDate();
    }

    @Then("^Changing date to past is not available$")
    public void changingDateToPastIsNotAvailable() {
        Assert.assertEquals(LocalDateTime.now().getDayOfMonth(), Integer.parseInt(dateAfterChanging));
    }

    @Given("^'Changing dates pop-up' is visible$")
    public void changingDatesPopUpIsVisible()  {
        Assert.assertTrue(bookingDetailsPage.isChangingDatesSectionVisible());
    }

    //TODO: private fields init
    private Integer priceBefore;
    @When("^I change booking check-in from current month to next month$")
    public void iChangeBookingCheckInCurrentMonthToNextMonth() {
        priceBefore = StringDataParser.extractNumber(bookingDetailsPage.getTotalPrice());
        bookingDetailsPage.selectNextMonth();
    }

    @And("^I click on 'change dates button'$")
    public void iClickOnChangeDatesButton() {
        bookingDetailsPage.submitChangeDates();
    }

    @Then("^Price after changing date to next month is equals or less then current price$")
    public void priceAfterChangingDateToNextMonthIsEqualsOrLessThenCurrentPrice() {
        WebDriver driver = DriverFactory.getInstance().getDriver();
        Integer priceAfter = StringDataParser.extractNumber(bookingDetailsPage.getTotalPrice());
        Assert.assertTrue((priceAfter <= priceBefore)
                || bookingDetailsPage.isHotelWasChanged()
                || bookingDetailsPage.isPriceWasChangedOnRoomSelectionPage()
                || !driver.findElements(By.xpath(".//*[contains(text(), 'has changed the reservation price')]")).isEmpty());
    }

    @When("^Submit valid details$")
    public void submittingValidDetails() {
        User user = UserSingleton.getInstance().getUser();
        bookingDetailsPage.cleanAllFields()
                .setTitle(user.getTitle())
                .setName(user.getName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail())
                .setEmailConfirmation(user.getEmail())
                .submitValidDetails();
        PageContainer.getInstance().updatePage(new ConfirmReservationPage());
    }


    @Then("^'guest email field' is visible$")
    public void elementVisible() {
        Assert.assertTrue(bookingDetailsPage.isGuestEmailFieldVisible());
    }

}
