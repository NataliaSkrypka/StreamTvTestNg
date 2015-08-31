package ua.net.streamtv.tests;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ua.net.streamtv.entities.ApiSportsman;
import ua.net.streamtv.guiceConfiguration.GuiceTestClass;
import ua.net.streamtv.pages.LoginPage;
import ua.net.streamtv.pages.SearchPage;
import ua.net.streamtv.steps.ApiSteps;
import ua.net.streamtv.utils.TestListener;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by nskrypka on 8/31/15.
 */
@Listeners(TestListener.class)
@Guice(modules = GuiceTestClass.class)
public class SearchSportsmanTest {

    @Inject
    WebDriver driver;

    @Inject
    ApiSteps apiSteps;

    @Inject
    LoginPage loginPage;

    @Inject
    SearchPage searchPage;

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Test(dataProviderClass = ApiSportsman.class, dataProvider = "randomSportsman")
    public void searchFunctionalityTest(ApiSportsman apiSportsman) {
        LOG.info("Sportsman for test search " + apiSportsman.toString());
        apiSteps.addSportsman(apiSportsman);

        loginPage.openSite();
        loginPage.login();

        searchPage.selectRegionFilter(Integer.parseInt(apiSportsman.getRegion()) - 1);
        searchPage.selectFstFilter(Integer.parseInt(apiSportsman.getFst()) - 1);
        searchPage.searchForSportsman(apiSportsman.getLastName());

        assertThat("Incorrect search result was shown", searchPage.getSearchResultSize(), is(1));
    }

    @AfterSuite
    public void finish() {
        if (driver != null) {
            driver.quit();
        }
    }
}
