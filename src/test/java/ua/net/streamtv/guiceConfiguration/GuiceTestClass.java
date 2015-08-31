package ua.net.streamtv.guiceConfiguration;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ua.net.streamtv.pages.LoginPage;
import ua.net.streamtv.steps.ApiSteps;

/**
 * Created by nskrypka on 8/29/15.
 */
public class GuiceTestClass implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(ApiSteps.class).toInstance(new ApiSteps());
        binder.bind(LoginPage.class).toInstance(new LoginPage(getDriver(),"http://streamtv.net.ua/base/", "auto", "test"));
    }

    private WebDriver getDriver() {
        String driverType = System.getProperty("driver");
        if ("chrome".equals(driverType)) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
            return new ChromeDriver();
        } else {
            return new FirefoxDriver();
        }
    }
}
