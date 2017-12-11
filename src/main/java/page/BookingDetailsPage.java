package page;

import entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import util.Highlighter;
import util.Waiter;

import java.util.List;

import static util.Clicker.click;

public class BookingDetailsPage extends AbstractPage {
    static final Logger logger = LogManager.getLogger(BookingDetailsPage.class);

    @FindBy(id = "booker_title")
    private WebElement bookerTitleDropdown;

    @FindBy(id = "firstname")
    private WebElement firstNameField;

    @FindBy(id = "lastname")
    private WebElement lastNameField;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "email_confirm")
    private WebElement emailConfirmField;

    @FindBy(xpath = ".//button[contains(@class, 'submit_holder_button')]")
    private WebElement confirmationButton;

    @FindBy(className = "bp_change_dates_link")
    private WebElement changeDatesLink;

    @FindBy(className = "bp_change_dates_lightbox")
    private WebElement changingDatesSection;

    @FindBy(className = "bp_change_dates_checkin_monthday_select")
    private WebElement checkInMonthdayDropdown;

    @FindBy(className = "bp_change_dates_checkin_yearmonth_select")
    private WebElement checkInYearmonthDropdown;

    @FindBy(className = "bp_change_dates_checkout_monthday_select")
    private WebElement checkOutMonthdayDropdown;

    @FindBy(xpath = ".//button[contains(@class, 'b-button_primary')]")
    private WebElement changeDatesSubmitButton;

    @FindBy(xpath = ".//input[contains(@class, 'guest-name-input')]")
    private WebElement guestNameField;

    @FindBy(xpath = ".//div[contains(@class, 'accessible-warning')]")
    private WebElement invalidDetailsWarningSection;

    @FindAll({@FindBy(xpath = ".//*[contains(@class, 'bp_form__field--error')]")})
    private List<WebElement> requiredFieldsWithError;

    @FindBy(xpath = ".//input[contains(@class, 'guest-email-input')]")
    private WebElement guestEmailField;

    @FindBy(css = ".modal-mask-closeBtn")
    private WebElement popUpClose;

    private final By PARENT_WARNING_FIELDS_CLASS_LOCATOR = By.xpath("parent::*[(contains(@class, 'bp_form__field--error')) or (contains(@class, 'bp_form__field--warning'))]");

    @FindBy(xpath = "//div[@class='bp_hotel_name_title']")
    private WebElement hotelTitle;

    @FindAll(@FindBy(xpath = "//ul[@class='bp_sidebar_content_block__ul']/li[1]/div[@class='bp_sidebar_content_block__li_content']"))
    private List<WebElement> checkInDate;

    @FindBy(xpath = "//ul[@class='bp_sidebar_content_block__ul']/li[2]/div[@class='bp_sidebar_content_block__li_content']")
    private WebElement checkOutDate;

    @FindBy(xpath = "//*[@class='topCurrencyClar']")
    private WebElement totalPrice;

    @FindAll({@FindBy(xpath = "//div[contains(@class,'modal-wrapper bp_leaving_users_light_box')]")})
    private List<WebElement> popUpWindows;

    @FindBy(xpath = "//div[contains(@class,'modal-wrapper bp_leaving_users_light_box')]//button")
    private WebElement popUpWindowClose;

    private String priceFromChooseRoomPage;
    private boolean isHotelWasChanged = false;

    public BookingDetailsPage() {
        logger.debug("Page has been created successfully");
    }

    public BookingDetailsPage(String priceFromChooseRoomPage) {
        this.priceFromChooseRoomPage = priceFromChooseRoomPage;
    }

    public BookingDetailsPage enterBookingCredentials(User user) {
        Waiter.waitForElementEnabled(firstNameField);
        cleanAllFields();
        setTitle(user.getTitle());
        setName(user.getName());
        setLastName(user.getLastName());
        emailField.sendKeys(user.getEmail());
        return this;
    }

    public BookingDetailsPage setTitle(String title) {
        Highlighter.highlight(bookerTitleDropdown);
        Waiter.waitForElementEnabled(bookerTitleDropdown);
        Select select = new Select(bookerTitleDropdown);
        try {
            if (title == null) select.getFirstSelectedOption().click();
            else select.selectByVisibleText(title + ".");
        } catch (WebDriverException e) {
            popUpClose.click();
            if (title == null) select.getFirstSelectedOption().click();
            else select.selectByVisibleText(title + ".");
        }
        Highlighter.unhighlight(bookerTitleDropdown);
        return this;
    }

    public BookingDetailsPage setName(String name) {
        Highlighter.highlight(firstNameField);
        firstNameField.sendKeys(name);
        Highlighter.unhighlight(firstNameField);
        return this;
    }

    public BookingDetailsPage setLastName(String lastName) {
        Highlighter.highlight(lastNameField);
        lastNameField.sendKeys(lastName);
        Highlighter.highlight(lastNameField);
        return this;
    }

    public BookingDetailsPage cleanAllFields() {
        setTitle(null);
        firstNameField.click();
        firstNameField.clear();
        lastNameField.click();
        lastNameField.clear();
        emailField.click();
        emailField.clear();
        firstNameField.click();
        return this;
    }

    /**
     * @return number of error parent node elements of fields;
     * booking.com details page must contains 3 or 4 (4th element is confirmation email field which
     * is can be invisible)
     */
    public boolean isEmptyFormsAreRequiredToFilling() {
        cleanAllFields();
        Highlighter.highlight(requiredFieldsWithError);
        int size = requiredFieldsWithError.size();
        Highlighter.unhighlight(requiredFieldsWithError);
        logger.log(Level.INFO, "Required to filling fields quantity: " + size + ". Expected size is three or more.");
        return size >= 3;
    }

    public BookingDetailsPage setEmail(String email) {
        emailField.click();
        emailField.clear();
        emailField.sendKeys(email);
        return this;
    }

    public BookingDetailsPage setEmailConfirmation(String email) {
        try {
            emailConfirmField.clear();
            emailConfirmField.sendKeys(email);
            logger.log(Level.INFO, "Email confirmations filed has been successfully filled");
        } catch (WebDriverException e) {
            logger.log(Level.INFO, "Email confirmations filed doesn't exist");
        }
        return this;
    }

    /**
     * @param email - invalid email only to check validation of field regex
     * @return true if find error section with error message.
     * <p>
     * invalid email can return two statement of errors, so locator uses in
     * this method check two kind of sections which contains ERROR or WARNING class in
     * parent node of email field
     */
    public boolean isMailRegexValid(String email) {
        setEmail(email);
        firstNameField.click();
        WebElement errorParentSection = emailField.findElement(PARENT_WARNING_FIELDS_CLASS_LOCATOR);
        Highlighter.highlight(errorParentSection);
        logger.log(Level.INFO, String.format("\n[FIELDS ERROR MESSAGE]\n %s", errorParentSection.getText()).trim());
        Highlighter.unhighlight(errorParentSection);
        return true;
    }

    public String getMailValue() {
        return emailField.getAttribute("value");
    }

    /**
     * @param name - name of Guest (any symbols, no pattern)
     * @return result of finding and filling guest's field
     */
    public boolean isFillingGuestNameAvailable(String name) {
        boolean res = guestNameField.isDisplayed();
        if (res) {
            Highlighter.highlight(guestNameField);
            guestNameField.sendKeys(name);
            Highlighter.unhighlight(guestNameField);
        }
        click(firstNameField);
        return res;
    }

    public boolean isGuestEmailFieldVisible() {
        return guestEmailField.isDisplayed();
    }

    @Override
    public boolean isPageOpened() {
        Waiter.waitForPageLoad();
        return confirmationButton.isDisplayed();
    }


    /**
     * method submitting invalid details to check error message.
     */
    //todo
    public BookingDetailsPage submitInvalidDetails() {
        click(confirmationButton);
        Waiter.waitForPageLoad();
        return this;
    }


    /**
     * method submitting valid details and pass to next page
     *
     * @return new Page Page object
     */
    public ConfirmReservationPage submitValidDetails() {
        //todo drop this crutch...
        int counter = 0;
        do {
            click(confirmationButton);
            counter++;
            if (counter > 10) throw new IllegalStateException("Cannot click on Confirm button");
        } while (isPageOpened());
        logger.debug("Confirm button was clicked at its " + counter + " attempt.");

        Waiter.waitForPageLoad();
        return new ConfirmReservationPage();
    }

    public boolean isBookingWarningMessagePresents() {
        Waiter.waitForElementVisible(invalidDetailsWarningSection);
        logger.log(Level.INFO, invalidDetailsWarningSection.getText());
        return invalidDetailsWarningSection.getText().length() > 0;
    }

    public BookingDetailsPage openChangingDatesSection() {
        click(changeDatesLink);
        return this;
    }

    public boolean isChangingDatesSectionVisible() {
        return changingDatesSection.isDisplayed();
    }

    /**
     * this method should be used after openChangingDatesSection because
     * changing dates available in pop-up window
     *
     * @param checkInDate check-in month day to change
     */
    public BookingDetailsPage changeDates(int checkInDate) {
        Waiter.waitForElementVisible(checkInMonthdayDropdown);
        Select select = new Select(checkInMonthdayDropdown);
        select.selectByValue(String.valueOf(checkInDate));
        return this;
    }

    /**
     * this method should be used after openChangingDatesSection because
     * changing dates available in pop-up window. This method select
     * next month from current month
     */
    public BookingDetailsPage selectNextMonth() {
        Select select = new Select(checkInYearmonthDropdown);
        select.selectByIndex(1);
        return this;
    }

    public String getCheckInDateFromChangesDate() {
        return checkInMonthdayDropdown.getAttribute("value");
    }

    public BookingDetailsPage submitChangeDates() {
        changeDatesSubmitButton.click();

        //todo simplify this
        try {
            ChooseRoomPage chooseRoomPage = new ChooseRoomPage();
            if (chooseRoomPage.isPageOpened()) {
                chooseRoomPage.reserveFirstAvailable();
                logger.info("Date changing caused to Choose Room page appearing.");
            }
        } catch (NoSuchElementException ex) {
            logger.info("Property was sold out. We are choosing another one.");
            // Try to avoid situation when the property was sold out
            HotelPage hotelPage = new HotelFilterPage().chooseHotel();
            hotelPage.selectCountRoom();
            hotelPage.clickForConfirmedReservation();
            isHotelWasChanged = true;
        }

        Waiter.waitForPageLoad();
        return this;
    }

    public String getTotalPrice() {
        return totalPrice.getText();
    }

    private String formatForLogger(String condition, String title, String checkIn, String checkOut, String price) {
        return String.format("%s title: %s check-in: %s check-out: %s  price: %s", condition, title, checkIn, checkOut, price);
    }

    public boolean checkActualInformationAboutReservation(String titleCome, String checkInDateCome, String checkOutDateCome, String totalPriceCome) {
        String title = hotelTitle.getText();
        String checkIn = checkInDate.get(0).getText();
        String checkOut = checkOutDate.getText();
        String price = totalPrice.getText();
        String expectedPrice = priceFromChooseRoomPage != null ? priceFromChooseRoomPage : totalPriceCome;

        logger.info(formatForLogger("Actual", title, checkIn, checkOut, price));
        logger.info(formatForLogger("Expected", titleCome, checkInDateCome, checkOutDateCome, expectedPrice));

        return title.equals(titleCome)
                && checkIn.contains(checkInDateCome)
                && checkOut.contains(checkOutDateCome)
                && price.contains(expectedPrice);
    }

    public boolean isPopUpWindowDisplayed() {
        return (popUpWindows.size() != 0) && popUpWindows.get(0).isDisplayed();
    }

    public BookingDetailsPage closePopUp() {
        if (isPopUpWindowDisplayed())
            popUpWindowClose.click();
        return this;
    }

    public boolean isPriceWasChangedOnRoomSelectionPage() {
        return priceFromChooseRoomPage != null;
    }

    public boolean isHotelWasChanged() {
        return isHotelWasChanged;
    }
}
