package ua.net.streamtv.pages;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Guice;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;
import ua.net.streamtv.guiceConfiguration.GuiceTestModule;

/**
 * Created by nskrypka on 8/20/2015.
 */
@Guice(modules = GuiceTestModule.class)
public class SearchPage extends GeneralPage {

    private WebDriver driver;

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    private static final By NEW_SPORTSMAN_BUTTON = By.cssSelector(".form-group .btn-default");
    private static final By SEARCH_INPUT = By.cssSelector("input[ng-model=searchFor]");
    private static final By SEARCH_BUTTON = By.cssSelector("div.form-group .btn-primary");
    private static final By RESULT_ROW = By.cssSelector(".table tbody tr");
    private static final By REGION_FILTER = By.xpath("//select[@ng-model='filters.fregion']");

    @FindBy(xpath = "//select[@ng-model='filters.ffst']")
    private WebElement fstSelect;

    @Inject
    public SearchPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step
    public void clickAddNewSportsman() {
        waitForElementPresence(NEW_SPORTSMAN_BUTTON, 3).click();
    }

    @Step
    @Attachment
    public byte[] searchForSportsman(String lastName) {
        WebElement searchInput = waitForElementPresence(SEARCH_INPUT, 3);
        searchInput.clear();
        searchInput.sendKeys(lastName);
        driver.findElement(SEARCH_BUTTON).click();
        LOG.info("Search action was performed for " + lastName);
        return takeScreenshot();
    }

    @Step
    @Attachment
    public byte[] openSportsmanDetails() {
        waitForElementPresence(RESULT_ROW, 3).click();
        LOG.info("Sportsman details page is opened");
        return takeScreenshot();
    }

    @Step
    public int getSearchResultSize() {
        return driver.findElements(RESULT_ROW).size();
    }

    @Step
    @Attachment
    public byte[] selectRegionFilter(int region) {
        Select regionDropdown = new Select(waitForElementPresence(REGION_FILTER, 3));
        regionDropdown.selectByIndex(region);
        LOG.info(region + " region was selected");
        LOG.info(regionDropdown.getFirstSelectedOption().getText() + " region was selected");
        return takeScreenshot();
    }

    @Step
    @Attachment
    public byte[] selectFstFilter(int fst) {
        Select fstDropdown = new Select(fstSelect);
        fstDropdown.selectByIndex(fst);
        LOG.info(fst + " fst was selected");
        LOG.info(fstDropdown.getFirstSelectedOption().getText() + " FST was selected");
        return takeScreenshot();
    }
}
