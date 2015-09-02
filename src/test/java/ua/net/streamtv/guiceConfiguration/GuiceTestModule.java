package ua.net.streamtv.guiceConfiguration;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ua.net.streamtv.pages.LoginPage;
import ua.net.streamtv.pages.SearchPage;
import ua.net.streamtv.pages.SportsmanDetailsPage;
import ua.net.streamtv.steps.ApiSteps;

import static java.io.File.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by nskrypka on 8/29/15.
 */
public class GuiceTestModule implements Module {

    @Override
    public void configure(Binder binder) {
        Properties props = new Properties();
        try {
            URL url = getClass().getResource("/TestProperties.properties");
            props.load(new FileReader(new File(url.toURI())));
            Names.bindProperties(binder, props);
        } catch (IOException | URISyntaxException e) {
            System.out.println("Could not load config: " + e.getMessage());
            System.exit(1);
        }
        binder.bind(WebDriver.class).toInstance(getDriver());
        binder.bind(ApiSteps.class);
        binder.bind(LoginPage.class);
        binder.bind(SportsmanDetailsPage.class);
        binder.bind(SearchPage.class);
    }

    private WebDriver getDriver() {
        String driverType = System.getProperty("driver");
        if ("chrome".equals(driverType)) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
            return new ChromeDriver();
        } else {
//            FirefoxProfile profile = new FirefoxProfile();
//            profile.setPreference("browser.download.folderList", 2);
//            profile.setPreference("browser.download.manager.showWhenStarting", false);
//            profile.setPreference("browser.download.manager.focusWhenStarting", false);
//            profile.setPreference("browser.download.useDownloadDir", true);
//            profile.setPreference("browser.helperApps.alwaysAsk.force", false);
//            profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
//            profile.setPreference("browser.download.manager.closeWhenDone", true);
//            profile.setPreference("browser.download.manager.showAlertOnComplete", false);
//            profile.setPreference("browser.download.manager.useWindow", false);
//            profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "images/jpeg,images/png,application/pdf,application/octet-stream");
//            profile.setPreference("browser.download.dir", System.getProperty("user.dir") + "/target/");

            return new FirefoxDriver();
        }
    }
}
