package ua.net.streamtv.tests;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.*;
import ru.yandex.qatools.allure.model.SeverityLevel;
import ua.net.streamtv.entities.ApiSportsman;
import ua.net.streamtv.guiceConfiguration.GuiceTestModule;
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
@Guice(modules = GuiceTestModule.class)
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
    @Description("Test for checking search functionality")
    @Issues({@Issue("SEARCH-1")})
    @Severity(SeverityLevel.CRITICAL)
    public void searchFunctionalityTest(ApiSportsman apiSportsman) {
        try {
            LOG.info("Sportsman for test search " + apiSportsman.toString());
            apiSteps.addSportsman(apiSportsman);

            loginPage.openSite();
            loginPage.login();

            searchPage.searchForSportsman(apiSportsman.getLastName());
            searchPage.selectRegionFilter(Integer.parseInt(apiSportsman.getRegion()) - 1);
            searchPage.selectFstFilter(Integer.parseInt(apiSportsman.getFst()) - 1);

            assertThat("Incorrect search result was shown", searchPage.getSearchResultSize(), is(1));
        } finally {
            apiSteps.deleteAllProfiles(apiSportsman.getLastName() + "+" + apiSportsman.getFirstName() + "+" + apiSportsman.getMiddleName());
        }
    }

    @AfterSuite
    public void finish() {
        if (driver != null) {
            driver.quit();
        }
    }
}
