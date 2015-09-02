package ua.net.streamtv.tests;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import ru.yandex.qatools.allure.annotations.*;
import ru.yandex.qatools.allure.model.SeverityLevel;
import ua.net.streamtv.entities.UiSportsman;
import ua.net.streamtv.guiceConfiguration.GuiceTestModule;
import ua.net.streamtv.pages.LoginPage;
import ua.net.streamtv.pages.SearchPage;
import ua.net.streamtv.pages.SportsmanDetailsPage;
import ua.net.streamtv.steps.ApiSteps;
import ua.net.streamtv.utils.TestListener;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by nskrypka on 8/28/2015.
 */
@Listeners(TestListener.class)
@Guice(modules = GuiceTestModule.class)
public class CrudSportsmanTest {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Inject
    LoginPage loginPage;

    @Inject
    WebDriver webDriver;

    @Inject
    ApiSteps apiSteps;

    @Inject
    SearchPage searchPage;

    @Inject
    SportsmanDetailsPage sportsmanDetailsPage;

    private UiSportsman uiSportsman;

    @Test(dataProviderClass = UiSportsman.class, dataProvider = "randomUiSportsman", priority = 0)
    @Description("Test for verifying add sportsman functionality on UI")
    @Severity(SeverityLevel.BLOCKER)
    public void addSportsmanTest(UiSportsman uiSportsman) {
        this.uiSportsman = uiSportsman;
        apiSteps.deleteAllProfiles(uiSportsman.getLastName() + "+" + uiSportsman.getFirstName() + "+" + uiSportsman.getMiddleName());

        loginPage.openSite();
        loginPage.login();

        searchPage.clickAddNewSportsman();

        enterFieldsForSportsman(uiSportsman);
        sportsmanDetailsPage.clickAddNewWrestler();
        sportsmanDetailsPage.closeSportsmanInfoTab();

        searchPage.searchForSportsman(uiSportsman.getLastName());
        searchPage.openSportsmanDetails();

        UiSportsman sportsmanActual = getUiSportsman();
        LOG.info("Added sportsman on UI : " + sportsmanActual.toString());

        assertThat("Sportsman after adding on UI is not as expected", sportsmanActual, equalTo(uiSportsman));
    }

    @Test(dataProviderClass = UiSportsman.class, dataProvider = "randomUiSportsman", priority = 0)
    @Description("Test for checking update functionality on UI")
    @Severity(SeverityLevel.BLOCKER)
    public void updateSportsmanTest(UiSportsman uiSportsmanForUpdate) {
        LOG.info("Sportsman details for update : " + uiSportsmanForUpdate.toString());
        loginPage.openSite();
        loginPage.login();

        searchPage.searchForSportsman(uiSportsman.getLastName());
        searchPage.openSportsmanDetails();
        enterFieldsForSportsman(uiSportsmanForUpdate);
        sportsmanDetailsPage.clickAddNewWrestler();
        sportsmanDetailsPage.closeSportsmanInfoTab();

        searchPage.searchForSportsman(uiSportsmanForUpdate.getLastName());
        searchPage.openSportsmanDetails();
        UiSportsman sportsmanActual = getUiSportsman();
        uiSportsman = uiSportsmanForUpdate;
        assertThat("Sportsman after update on UI is not as expected", sportsmanActual, equalTo(uiSportsmanForUpdate));

    }

    @Test(priority = 1)
    @Description("Test for checking delete functionality on UI")
    @Severity(SeverityLevel.BLOCKER)
    public void deleteSportsmanTest() {
        loginPage.openSite();
        loginPage.login();

        searchPage.searchForSportsman(uiSportsman.getLastName());
        searchPage.openSportsmanDetails();
        sportsmanDetailsPage.deleteSportsman();

        searchPage.searchForSportsman(uiSportsman.getLastName());
        assertThat("Delete sportsman on UI was not performed successfully", searchPage.getSearchResultSize(), equalTo(0));
    }

    @Step
    @Attachment
    private byte[] enterFieldsForSportsman(UiSportsman uiSportsman) {
        sportsmanDetailsPage.typeLastName(uiSportsman.getLastName());
        sportsmanDetailsPage.typeFirstName(uiSportsman.getFirstName());
        sportsmanDetailsPage.typeDateOfBirth(uiSportsman.getDateOfBirth());
        sportsmanDetailsPage.typeMiddleName(uiSportsman.getMiddleName());
        sportsmanDetailsPage.selectRegion(uiSportsman.getRegion());
        sportsmanDetailsPage.selectRegion1(uiSportsman.getRegion1());
        sportsmanDetailsPage.selectFst(uiSportsman.getFst());
        sportsmanDetailsPage.selectFst1(uiSportsman.getFst1());
        sportsmanDetailsPage.selectStyle(uiSportsman.getStyle());
        sportsmanDetailsPage.selectAge(uiSportsman.getAge());
        sportsmanDetailsPage.selectYear(uiSportsman.getYear());
        return sportsmanDetailsPage.takeScreenshot();
    }

    @Step
    private UiSportsman getUiSportsman() {
        String lastNameActual = sportsmanDetailsPage.getLastName();
        String firstNameActual = sportsmanDetailsPage.getFirstName();
        String dateOfBirthActual = sportsmanDetailsPage.getDateOfBirth();
        String middleNameActual = sportsmanDetailsPage.getMiddleName();
        String regionActual = sportsmanDetailsPage.getRegion();
        String region1Actual = sportsmanDetailsPage.getRegion1();
        String fstActual = sportsmanDetailsPage.getFst();
        String fst1Actual = sportsmanDetailsPage.getFst1();
        String styleActual = sportsmanDetailsPage.getStyle();
        String ageActual = sportsmanDetailsPage.getAge();
        String yearActual = sportsmanDetailsPage.getYear();
        return new UiSportsman(lastNameActual, dateOfBirthActual, regionActual, fstActual, yearActual, styleActual, firstNameActual, region1Actual, middleNameActual, fst1Actual, ageActual);
    }

    @AfterSuite
    public void close() {
        if(webDriver != null){
            webDriver.quit();
        }
    }
}
