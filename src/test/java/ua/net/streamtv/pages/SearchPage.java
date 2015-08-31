package ua.net.streamtv.pages;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Guice;
import ua.net.streamtv.guiceConfiguration.GuiceConfigForPageObject;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * Created by nskrypka on 8/20/2015.
 */
//@Guice(modules = GuiceConfigForPageObject.class)
public class SearchPage extends GeneralPage {

//    @Inject
    private WebDriver driver;

    private static final By NEW_SPORTSMAN_BUTTON = By.cssSelector(".form-group .btn-default");
    private static final By SEARCH_INPUT = By.cssSelector("input[ng-model=searchFor]");
    private static final By SEARCH_BUTTON = By.cssSelector("div.form-group .btn-primary");
    private static final By RESULT_ROW = By.cssSelector(".table tbody tr");
    private static final By REGION_FILTER = By.xpath("//select[@ng-model='filters.fregion']");

    @FindBy(xpath = "//select[@ng-model='filters.ffst']")
    private WebElement fstSelect;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickAddNewSportsman() {
        new WebDriverWait(driver, 3000).until(presenceOfElementLocated(NEW_SPORTSMAN_BUTTON));
        driver.findElement(NEW_SPORTSMAN_BUTTON).click();
    }

    public void searchForSportsman(String lastName) {
        new WebDriverWait(driver, 3000).until(presenceOfElementLocated(SEARCH_INPUT));
        driver.findElement(SEARCH_INPUT).clear();
        driver.findElement(SEARCH_INPUT).sendKeys(lastName);
        driver.findElement(SEARCH_BUTTON).click();
    }

    public void openSportsmanDetails() {
        new WebDriverWait(driver, 3000).until(presenceOfElementLocated(RESULT_ROW));
        driver.findElement(RESULT_ROW).click();
    }

    public int getSearchResultSize() {
        return driver.findElements(RESULT_ROW).size();
    }

    public void selectRegionFilter(String region) {
        new WebDriverWait(driver, 3000).until(presenceOfElementLocated(REGION_FILTER));
        Select regionDropdown = new Select(driver.findElement(REGION_FILTER));
        regionDropdown.selectByVisibleText(region);
    }

    public void selectFstFilter(String fst) {
        Select fstDropdown = new Select(fstSelect);
        fstDropdown.selectByVisibleText(fst);
    }
}
