package page;

import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import util.Waiter;

import java.util.List;

public class MainPage extends AbstractPage {
    static final Logger logger = LogManager.getLogger(MainPage.class);

    @FindBy(xpath = "//div[contains(@class, 'sb-searchbox__outer')]//input[@id='ss']")
    private WebElement searchCityInput;

    @FindBy(xpath = "//*[@id='frm']//*[starts-with(@class,'sb-searchbox__row')]//button[@type='submit']")
    private WebElement searchButton;

    @FindBy(xpath = "//*[@class='c2-calendar-close-button-icon']")
    private WebElement closeDatesFormElement;

    @FindAll({@FindBy(xpath = ".//ul[contains(@class, 'c-autocomplete__list')]/li")})
    private List<WebElement> suggestionDestinations;

    private By SUGGESTION_DESTINATIONS_ELEMENTS_LOCATOR = By.xpath(".//ul[contains(@class, 'c-autocomplete__list')]/li");
    private final By ALERT_TEXT_LOCATOR = By.xpath(".//*[@class='sb-searchbox__error -visible']");


    public MainPage() {
        logger.debug("Page has been created successfully");
    }

    public MainPage setDestination(String destination) {
        logger.info("The destination set to " + destination);
        Waiter.waitForElementVisible(searchCityInput);
        searchCityInput.clear();
        searchCityInput.click();
        searchCityInput.sendKeys(destination);
        return this;
    }

    public MainPage enterCity(User user) {
        setDestination(user.getTrip().getDestination());
        Waiter.waitForTextPresent(SUGGESTION_DESTINATIONS_ELEMENTS_LOCATOR);
        logger.info("The first suggested destination was chosen");
        suggestionDestinations.get(0).click();
        return this;
    }

    public HotelFilterPage clickSearchButton() {
        driver.findElement(By.className("sb-searchbox__title-text")).click();
        logger.info("Searching of " + searchCityInput.getText());
        new Actions(driver).moveToElement(searchButton).click().perform();
        return new HotelFilterPage();
    }

    public MainPage fillKidsQuantity() {
        logger.debug("Fill kids quantity field.");
        new Select(driver.findElement(By.xpath("//*[@id='group_children']"))).selectByIndex(2);
        return this;
    }

    public MainPage closeDatesForm() {
        Waiter.waitForElementVisible(closeDatesFormElement);
        closeDatesFormElement.click();
        logger.debug("Dates form was closed.");
        return this;
    }

    public String getAlertMessage() {
        String alertText = driver.findElement(ALERT_TEXT_LOCATOR).getText();
        logger.info("Alert message: " + alertText);
        return alertText;
    }

    @Override
    public boolean isPageOpened() {
        return "b2indexPage".equals(getPageId());
    }
}