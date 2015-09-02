package ua.net.streamtv.pages;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Guice;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextInput;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ua.net.streamtv.guiceConfiguration.GuiceTestModule;

/**
 * Created by nskrypka on 8/19/2015.
 */
@Guice(modules = {GuiceTestModule.class})
public class LoginPage extends GeneralPage {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());
    private WebDriver driver;

    private static final By LOGIN_INPUT = By.cssSelector("input[placeholder=Login]");

    @FindBy(xpath = "//input[@placeholder='Password']")
    private TextInput passwordInput;

    @FindBy(xpath = "//button[text()='Login']")
    private Button loginButton;
    private String baseUrl;
    private String login;
    private String password;

    @Inject
    public LoginPage(WebDriver driver, @Named("base.url") String baseUrl, @Named("login") String login, @Named("password") String password) {
        super(driver);
        this.driver = driver;
        this.baseUrl = baseUrl;
        this.login = login;
        this.password = password;
        PageFactory.initElements(new HtmlElementDecorator(driver), this);
    }

    @Step
    @Attachment
    public byte[] openSite() {
        driver.get(baseUrl);
        driver.manage().window().maximize();
        LOG.info(baseUrl + " site is opened");
        return takeScreenshot();
    }

    @Step
    @Attachment
    public byte[] login() {
        new WebDriverWait(driver, 3000).until(ExpectedConditions.presenceOfElementLocated(LOGIN_INPUT));
        driver.findElement(LOGIN_INPUT).clear();
        driver.findElement(LOGIN_INPUT).sendKeys(login);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        loginButton.click();
        LOG.info("User is logged into site");
        return takeScreenshot();
    }
}
