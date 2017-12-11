package page;

import entity.User;
import entity.UserSingleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import parser.StringDataParser;
import util.Waiter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static parser.StringDataParser.extractNumber;
import static util.Clicker.click;

public class HotelFilterPage extends AbstractPage {
    static final Logger logger = LogManager.getLogger(HotelFilterPage.class);

    @FindBy(xpath = "//div[@id='filter_out_of_stock']/div[@class='filteroptions']")
    private WebElement availablePropertiesCheckbox;

    @FindAll(@FindBy(xpath = ".//*[@id='filter_class']//*[contains(@class,'css-checkbox')]"))
    private List<WebElement> availableStarRatingCheckboxes;

    @FindAll(@FindBy(xpath = ".//*[@id='filter_price']//*[contains(@class,'css-checkbox')]"))
    private List<WebElement> availableYourBudgetCheckboxes;

    @FindAll(@FindBy(xpath = "//div[not(contains(@class, \"sr_item--highlighted\")) and contains(@class,\"sr_item_default\") and not (preceding-sibling::div[contains(@class, \"fe_banner\")])]"))
    private List<WebElement> featuredHotels;

    @FindAll(@FindBy(xpath = "//strong[contains(@class,'price')]//b"))
    private List<WebElement> hotelPrice;

    @FindBy(xpath = "(.//*[@class='sr-cta-button-row']/*)[1]")
    private WebElement hotelButton;

    @FindBy(xpath = "//div[contains(@class, 'sb-searchbox__outer')]//div[contains(@class, 'sb-dates__col --checkin-field')]//div[contains(@class, 'sb-date-field b-datepicker')]//div[contains(@class, 'sb-searchbox__input sb-date-field__field')]")
    private WebElement searchCheckInDropDown;

    @FindBy(xpath = "//div[contains(@class, 'sb-searchbox__outer')]//table[1]//td[contains(@class,'c2-day-s-today')]")
    private WebElement searchCheckInInput;

    @FindBy(xpath = "//div[contains(@class, 'sb-searchbox__outer')]//form[@data-component='search/searchbox/searchbox']//button[contains(@class, 'sb-searchbox__button')]")
    private WebElement searchButton;

    private final By DATES_MESSAGE = By.xpath(".//*[@id='right']//p[contains(@class, 'dates_rec_sr__title')]");
    private final By CHECK_IN_CALENDAR_DATE = By.xpath("//div[contains(@class, 'sb-searchbox__outer')]//table[1]//td[contains(@class,'c2-day-s-today')]");

    private int maxPriceAvailable;

    public HotelFilterPage() {
        logger.debug("Page has been created successfully");
    }

    public boolean isPageOpened() {
        return "b2searchresultsPage".equals(getPageId());
    }

    public HotelPage chooseHotel() {
        Waiter.waitForElementEnabled(hotelButton);
        click(hotelButton);
        //go to other page
        new WebDriverWait(driver, 10).until(ExpectedConditions.numberOfWindowsToBe(2));
        driver.close();
        ArrayList<String> windows = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windows.get(0));
        return new HotelPage();
    }

    public HotelFilterPage showAvailable() {
        checkboxClick(HotelFilterName.AVAILABILITY, 1);
        return this;
    }

    public HotelFilterPage selectFilters(User user, String filterName) {
        if (filterName.equals("stars")) {
            List<String> stars = user.getTrip().getHotelStarRating();
            for (String star : stars)
                checkboxClick(HotelFilterName.STAR_RATING, Integer.valueOf(star));
        } else if (filterName.equals("budget")) {
            int budget = user.getTrip().getBudget();
            ArrayList<String> budgetOptions = findAvailableBudgetCheckboxes();
            long count = budgetOptions.stream().map(StringDataParser::extractNumber).filter(x -> x < budget).count();
            IntStream.rangeClosed(0, (int) count - 1).forEach(i -> checkboxClick(HotelFilterName.BUDGET, i));
            maxPriceAvailable = extractNumber(budgetOptions.get((int) count - 1), 1);
        }

        driver.navigate().refresh();
        Waiter.waitForPageLoad();
        return this;
    }

    private HotelFilterPage checkboxClick(HotelFilterName name, int index) {
        WebElement checkbox;
        switch (name) {
            case STAR_RATING:
                checkbox = availableStarRatingCheckboxes.stream()
                        .filter(we -> we.getText().trim().startsWith(String.valueOf(index)))
                        .findFirst().get();
                logger.debug("Star option [" + index + "] text '" + checkbox.getText() + "' selected: " + checkbox.isSelected());
                break;
            case BUDGET:
                checkbox = availableYourBudgetCheckboxes.get(index);
                logger.debug("Budget option [" + index + "] text '" + checkbox.getText() + "' selected: " + checkbox.isSelected());
                break;
            case AVAILABILITY:
                checkbox = availablePropertiesCheckbox;
                break;
            default:
                throw new IllegalArgumentException("Unknown filter: " + name);
        }
        click(checkbox);
        return this;
    }

    private String[] findFeaturedHotelsStars() {
        return featuredHotels.stream().map(we -> we.getAttribute("data-class")).toArray(String[]::new);
    }

    private Integer[] findFeaturedHotelsPrices() {
        return hotelPrice.stream().map(we -> extractNumber(we.getText())).toArray(Integer[]::new);
    }

    //todo simplify
    private ArrayList<String> findAvailableBudgetCheckboxes() {
        ArrayList<String> budgets = availableYourBudgetCheckboxes.stream()
                .map(element -> element.getText().replaceAll("[A-z]", "").trim())
                .collect(Collectors.toCollection(ArrayList::new));
        logger.info(budgets.size() + " budget options were found: " + budgets.toString());
        return budgets;
    }

    public HotelFilterPage enterDates(User user) {
        Waiter.waitForElementEnabled(searchCheckInDropDown);
        logger.debug("Click check-in dropdown");
        searchCheckInDropDown.click();
        Waiter.waitForElementVisible(searchCheckInInput);
        logger.debug("Click check-in date on calendar");
        click(searchCheckInInput);
        return this;

    }

    public HotelFilterPage clickSearchButton() {
        Waiter.waitForElementInvisible(CHECK_IN_CALENDAR_DATE);
        searchButton.click();
        logger.debug("Press search button");
        return new HotelFilterPage();
    }

    public String getChooseDateMessage() {
        Waiter.waitForElementVisible(DATES_MESSAGE);
        return driver.findElement(DATES_MESSAGE).getText();
    }

    public boolean verifyStarRatingSearchResult() {
        List<String> stars = UserSingleton.getInstance().getUser().getTrip().getHotelStarRating();
        String regex = "(" + String.join("|", stars) + ")";
        logger.debug("Verify star rating results with regex: " + regex);
        return Arrays.stream(findFeaturedHotelsStars()).allMatch(str -> str.matches(regex));
    }

    public boolean verifyBudgetSearchResult() {
        Integer[] hotelPrices = findFeaturedHotelsPrices();
        return Arrays.stream(hotelPrices).filter(x -> x != null).allMatch(x -> x <= maxPriceAvailable);
    }
}
