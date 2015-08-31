package ua.net.streamtv.guiceConfiguration;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ua.net.streamtv.steps.ApiSteps;

/**
 * Created by nskrypka on 8/31/2015.
 */
public class GuiceConfigForPageObject implements Module {
    @Override
    public void configure(Binder binder) {
        String driverType = System.getProperty("driver");
        if ("chrome".equals(driverType)){
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
            binder.bind(WebDriver.class).toInstance(new ChromeDriver());
        } else {
            binder.bind(WebDriver.class).toInstance(new FirefoxDriver());
        }
    }
}
