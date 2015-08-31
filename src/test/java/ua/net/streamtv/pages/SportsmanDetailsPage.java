package ua.net.streamtv.pages;

import com.google.inject.Inject;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Guice;
import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.element.TextInput;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ua.net.streamtv.guiceConfiguration.GuiceConfigForPageObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * Created by nskrypka on 8/20/2015.
 */
//@Guice(modules = GuiceConfigForPageObject.class)
public class SportsmanDetailsPage extends GeneralPage {

//    @Inject
    private WebDriver driver;

    public static final By ACCEPT_ALERT_BUTTON = By.xpath("//div[@class='modal-dialog']//button[@class='btn btn-success']");

    private static final By LAST_NAME_INPUT = By.xpath("//input[@placeholder='Last name']");

    @FindBy(xpath = "//input[@placeholder='Date of Birth']")
    private TextInput dateOfBirthInput;

    @FindBy(xpath = "//fg-select[@label='Region']//select[@required='required']")
    private Select regionSelect;

    @FindBy(xpath = "//fg-select[@label='FST']//select[@required='required']")
    private Select fstSelect;

    @FindBy(xpath = "//fg-select[@label='Style']//select")
    private Select styleSelect;

    @FindBy(xpath = "//fg-select[@label='Year']//select")
    private Select yearSelect;

    @FindBy(xpath = "//input[@placeholder='First name']")
    private TextInput firstNameInput;

    @FindBy(xpath = "//input[@placeholder='Middle name']")
    private TextInput middleNameInput;

    @FindBy(xpath = "//fg-select[@label='Age']//select")
    private Select ageSelect;

    private static final By ADD_BUTTON = By.cssSelector(".btn-success");
    private static final By DELETE_BUTTON = By.cssSelector(".btn-danger");

    @FindBy(css = "tab-heading.ng-scope .glyphicon-remove")
    private WebElement closeTabIcon;

    private static final String WRESTLERS_TAB = "//div[@class='close-it']/ico[@class!='ng-hide']";

    private static final By PHOTO_UPLOAD_BUTTON = By.xpath("//input[@uploader='photoUploader']");
    private static final String PHOTO_LOCATION = "//img[contains(@src,'data/photo')]";

    private static final By FILE_UPLOAD_BUTTON = By.xpath("//input[@uploader='attachUploader']");

    private static final String FILE_LOCATION = "//a[contains(@href,'data/attach')]";

    private static final String DELETE_ATTACHMENT_ICON = "//ico[@ng-click='deleteAttach($index)']";

    private static final String ATTACHMENT_ROW = "//div[@class='file-drop']/table/tbody/tr";

    @FindBy(xpath = "//fg-select[@value='wr.region2']//select")
    private Select region1Select;

    @FindBy(xpath = "//fg-select[@value='wr.fst2']//select")
    private Select fst1Select;

    public SportsmanDetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new HtmlElementDecorator(driver), this);
    }

    public void typeLastName(String lastName) {
        new WebDriverWait(driver, 3000).until(presenceOfElementLocated(LAST_NAME_INPUT));
        driver.findElement(LAST_NAME_INPUT).clear();
        driver.findElement(LAST_NAME_INPUT).sendKeys(lastName);
    }

    public void typeDateOfBirth(String dateOfBirth) {
        dateOfBirthInput.clear();
        dateOfBirthInput.sendKeys(dateOfBirth);
    }

    public void selectRegion(String region) {
        regionSelect.selectByVisibleText(region);
    }

    public void selectFst(String fst) {
        fstSelect.selectByVisibleText(fst);
    }

    public void selectStyle(String style) {
        styleSelect.selectByVisibleText(style);
    }

    public void selectYear(String year) {
        yearSelect.selectByVisibleText(year);
    }

    public void typeFirstName(String firstName) {
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
    }

    public void typeMiddleName(String middleName) {
        middleNameInput.clear();
        middleNameInput.sendKeys(middleName);
    }

    public void selectAge(String age) {
        ageSelect.selectByVisibleText(age);
    }

    public void clickAddNewWrestler() {
        new WebDriverWait(driver, 3000).until(presenceOfElementLocated(ADD_BUTTON));
        driver.findElement(ADD_BUTTON).click();
    }

    public void closeSportsmanInfoTab() {
        new WebDriverWait(driver, 3000).until(presenceOfElementLocated(By.xpath(WRESTLERS_TAB)));
        driver.findElement(By.xpath(WRESTLERS_TAB)).click();
    }

    public String getLastName() {
        return driver.findElement(LAST_NAME_INPUT).getAttribute("value");
    }

    public String getDateOfBirth() {
        return dateOfBirthInput.getText();
    }

    public String getRegion() {
        return regionSelect.getFirstSelectedOption().getText();
    }

    public String getFst() {
        return fstSelect.getFirstSelectedOption().getText();
    }

    public String getStyle() {
        return styleSelect.getFirstSelectedOption().getText();
    }

    public String getYear() {
        return yearSelect.getFirstSelectedOption().getText();
    }

    public String getFirstName() {
        return firstNameInput.getText();
    }

    public String getMiddleName() {
        return middleNameInput.getText();
    }

    public String getAge() {
        return ageSelect.getFirstSelectedOption().getText();
    }

    public void deleteSportsman() {
        new WebDriverWait(driver, 3000).until(presenceOfElementLocated(DELETE_BUTTON));
        driver.findElement(DELETE_BUTTON).click();
        driver.findElement(ACCEPT_ALERT_BUTTON).click();
    }

    public void uploadPhoto(String photoPath) {
        new WebDriverWait(driver, 3000).until(presenceOfElementLocated(PHOTO_UPLOAD_BUTTON));
        driver.findElement(PHOTO_UPLOAD_BUTTON).sendKeys(photoPath);
    }

    public String downloadPhoto() throws IOException {
        URL url = new URL(driver.findElement(By.xpath(PHOTO_LOCATION)).getAttribute("src"));
        BufferedImage bufImgOne = ImageIO.read(url);
        File photo = File.createTempFile("downloadedPhoto", ".png");
        ImageIO.write(bufImgOne, "png", photo);
        return photo.getAbsolutePath();
    }

    public void uploadFile(String fileAbsolutePath) {
        new WebDriverWait(driver, 3000).until(presenceOfElementLocated(FILE_UPLOAD_BUTTON));
        driver.findElement(FILE_UPLOAD_BUTTON).sendKeys(fileAbsolutePath);
    }

    public String downloadFile() throws IOException {
        URL url = new URL(driver.findElement(By.xpath(FILE_LOCATION)).getAttribute("href"));
        File file = File.createTempFile("downloadedFile", ".pdf");
        FileUtils.copyURLToFile(url, file);
        return file.getAbsolutePath();
    }

    public void deleteAttachment() {
        driver.findElement(By.xpath(DELETE_ATTACHMENT_ICON)).click();
    }

    public int getNumberOfAttachments() {
        return driver.findElements(By.xpath(ATTACHMENT_ROW)).size();
    }

    public void selectRegion1(String region1) {
        region1Select.selectByVisibleText(region1);
    }

    public void selectFst1(String fst1) {
        fst1Select.selectByVisibleText(fst1);
    }

    public String getRegion1() {
        return region1Select.getFirstSelectedOption().getText();
    }

    public String getFst1() {
        return fst1Select.getFirstSelectedOption().getText();
    }
}