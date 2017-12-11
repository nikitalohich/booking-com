package page;

import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.DriverFactory;
import util.Waiter;

import java.util.concurrent.TimeUnit;

public abstract class AbstractPage {
    static final Logger logger = LogManager.getLogger(AbstractPage.class);
    private By signInForm = By.cssSelector("#b2indexPage>div.modal-wrapper.user-access-menu-lightbox.user-access-menu-lightbox--user-center>div>div>div.user_access_signin_menu.form-section.form-shown-section");

    @FindBy(xpath = "//*[@id='b2indexPage']//input[12]")
    private WebElement submitRegistrationButton;

    @FindBy(css = ".bootstrapped-input.btn.btn-primary")
    private WebElement submitButton;

    @FindBy(xpath = "//*[@id='b2indexPage']//div[4]/form[1]/label[1]/input")
    private WebElement emailRegistrationField;

    @FindBy(xpath = "//*[@id='password']")
    private WebElement passwordRegistrationField;

    @FindBy(xpath = "//*[@class='user_access_email bootstrapped-input input-text input-block-level input-xlarge']")
    private WebElement emailField;

    @FindBy(xpath = "//*[@class='user_access_password bootstrapped-input input-text input-block-level input-xlarge']")
    private WebElement passwordField;

    @FindBy(xpath = "//li[@data-component-show-once-key='header_signin_prompt']//div/span")
    private WebElement signInButton;

    @FindBy(xpath = "//*[@id='current_account']/a/div/span[1]")
    private WebElement registrationButton;

    @FindBy(xpath = "//input[@value='Sign out']")
    private WebElement signOutButton;

    @FindBy(xpath = "//li[@data-command='show-profile-menu']//span[contains(@class, 'user_firstname')]")
    private WebElement yourAccountLink;

    private static final By NOTIFICATION_ICON_LOCATOR = By.xpath("//a[contains(@class,'js-uc-notifications-bell')]");
    protected WebDriver driver = DriverFactory.getInstance().getDriver();

    public AbstractPage() {
        logger.debug("Create new page");
        PageFactory.initElements(driver, this);
    }

    protected String getPageId() {
        Waiter.waitForPageLoad();
        WebElement ancestor = driver.findElement(By.xpath(".//body[starts-with(@id,'b2')]"));
        String id = ancestor.getAttribute("id");
        logger.info("This page has id: " + id);
        return id;
    }

    public boolean isElementPresent(By locator) {
        boolean isPresent = false;
        DriverFactory.getInstance().getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        logger.debug("Search for elements present by locator: " + locator.toString());
        isPresent = !driver.findElements(locator).isEmpty();
        logger.debug("Searching stopped: " + locator.toString());
        //todo drop it? wtf
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        return isPresent;
    }

    public boolean isLoggedIn() {
        logger.debug("Check for presence of notification icon");
        try {
            Waiter.waitForElementVisible(NOTIFICATION_ICON_LOCATOR);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean isLoggedOut() {
        logger.debug("Check for presence of registration button");
        try {
            Waiter.waitForElementVisible(registrationButton);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public void yourAccountClick() {
        Waiter.waitForElementEnabled(yourAccountLink);
        logger.debug("Press 'Your account'");
        yourAccountLink.click();
    }

    public void signOutButtonClick() {
        Waiter.waitForElementVisible(signOutButton);
        Waiter.waitForElementEnabled(signOutButton);
        logger.debug("Press 'Sign out' button");
        signOutButton.click();
    }

    public void clickRegisterButton() {
        Waiter.waitForElementEnabled(registrationButton);
        logger.debug("Press 'Register' button");
        registrationButton.click();
    }

    public void clickSignInButton() {
        Waiter.waitForElementEnabled(signInButton);
        logger.debug("Press 'Sign in' button");
        signInButton.click();
    }

    public void enterCredentials(User user) {
        if (this.isElementPresent(signInForm)) {
            Waiter.waitForElementVisible(emailField);
            Waiter.waitForElementVisible(passwordField);
            logger.debug("Fill email field in 'Sign in' form");
            emailField.sendKeys(user.getEmail());
            logger.debug("Fill password field in 'Sign in' form");
            passwordField.sendKeys(user.getPassword());
        }
    }

    public void enterCredentialsForRegistration(User user) {
        Waiter.waitForElementVisible(emailRegistrationField);
        Waiter.waitForElementVisible(passwordRegistrationField);
        logger.debug("Fill email field in 'Registration' form");
        emailRegistrationField.sendKeys(user.getEmail());
        logger.debug("Fill password field in 'Registration' form");
        passwordRegistrationField.sendKeys(user.getPassword());
    }

    public void clickSubmitButton() {
        logger.debug("Press 'Submit' button in 'Sign in' form");
        submitButton.click();
    }

    public void clickSubmitRegistrationButton() {
        logger.debug("Press 'Submit' button in 'Registration' form");
        submitRegistrationButton.click();
    }

    public abstract boolean isPageOpened();
}