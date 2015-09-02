package ua.net.streamtv.tests;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.model.SeverityLevel;
import ua.net.streamtv.entities.ApiSportsman;
import ua.net.streamtv.guiceConfiguration.GuiceTestModule;
import ua.net.streamtv.pages.LoginPage;
import ua.net.streamtv.pages.SearchPage;
import ua.net.streamtv.pages.SportsmanDetailsPage;
import ua.net.streamtv.steps.ApiSteps;
import ua.net.streamtv.utils.FileComparasionUtils;
import ua.net.streamtv.utils.TestListener;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by nskrypka on 8/31/15.
 */
@Listeners({TestListener.class})
@Guice(modules = GuiceTestModule.class)
public class UploadFilesTest {
    @Inject
    WebDriver driver;

    @Inject
    ApiSteps apiSteps;

    @Inject
    LoginPage loginPage;

    @Inject
    SearchPage searchPage;

    @Inject
    SportsmanDetailsPage sportsmanDetailsPage;

    private Logger LOG = LoggerFactory.getLogger(this.getClass());
    private ApiSportsman apiSportsmanGeneral;

    @Test(dataProviderClass = ApiSportsman.class, dataProvider = "randomSportsman", priority = 0)
    @Description("Test for verifying upload photo functionality")
    @Severity(SeverityLevel.CRITICAL)
    public void addPhotoToProfileTest(ApiSportsman apiSportsman) throws IOException {
        try {
            LOG.info("Sportsman profile to test photo upload " + apiSportsman.toString());
            apiSteps.addSportsman(apiSportsman);

            loginPage.openSite();
            loginPage.login();
            searchPage.searchForSportsman(apiSportsman.getLastName());
            searchPage.openSportsmanDetails();

            sportsmanDetailsPage.uploadPhoto(System.getProperty("user.dir") + "\\src\\test\\resources\\files\\download2.jpg");
            sportsmanDetailsPage.closeSportsmanInfoTab();

            searchPage.searchForSportsman(apiSportsman.getLastName());
            searchPage.openSportsmanDetails();
            String downloadedPhotoPath = sportsmanDetailsPage.downloadPhoto();
            boolean arePhotosEqual = FileComparasionUtils.compareImages(System.getProperty("user.dir") + "\\src\\test\\resources\\files\\expectedImage.png", downloadedPhotoPath);
            assertThat("Downloaded photo is not as expected", arePhotosEqual, is(true));
        } finally {
            apiSteps.deleteAllProfiles(apiSportsman.getLastName() + "+" + apiSportsman.getFirstName() + "+" + apiSportsman.getMiddleName());
        }
    }

    @Test(dataProviderClass = ApiSportsman.class, dataProvider = "randomSportsman", priority = 1)
    @Description("Test for verifying upload attachment functionality")
    @Severity(SeverityLevel.NORMAL)
    public void addAttachmentToProfileTest(ApiSportsman apiSportsman) throws IOException {
        LOG.info("Sportsman profile to test attachment upload " + apiSportsman.toString());
        this.apiSportsmanGeneral = apiSportsman;
        apiSteps.addSportsman(apiSportsman);

        loginPage.openSite();
        loginPage.login();
        searchPage.searchForSportsman(apiSportsman.getLastName());
        searchPage.openSportsmanDetails();

        String fileAbsolutePath = System.getProperty("user.dir") + "\\src\\test\\resources\\files\\TaskforTechnicalInterviewNIAutomationv0.2.pdf";
        sportsmanDetailsPage.uploadFile(fileAbsolutePath);
        sportsmanDetailsPage.closeSportsmanInfoTab();

        searchPage.searchForSportsman(apiSportsman.getLastName());
        searchPage.openSportsmanDetails();
        String downloadedFilePath = sportsmanDetailsPage.downloadFile();
        boolean areFilesEqual = FileComparasionUtils.comparePdfs(downloadedFilePath, fileAbsolutePath);
        assertThat("Downloaded file is not as expected", areFilesEqual, is(true));
    }

    @Test(priority = 1)
    @Description("Test for verifying delete attachment for profile functionality")
    @Severity(SeverityLevel.NORMAL)
    public void deleteAttachmentFromProfileTest() {
        try {
            loginPage.openSite();
            loginPage.login();
            searchPage.searchForSportsman(apiSportsmanGeneral.getLastName());
            searchPage.openSportsmanDetails();

            sportsmanDetailsPage.deleteAttachment();
            sportsmanDetailsPage.closeSportsmanInfoTab();

            searchPage.searchForSportsman(apiSportsmanGeneral.getLastName());
            searchPage.openSportsmanDetails();
            int numberOfAttachmentsActual = sportsmanDetailsPage.getNumberOfAttachments();
            assertThat("Number of attachments is not as expected", numberOfAttachmentsActual, is(0));
        } finally {
            apiSteps.deleteAllProfiles(apiSportsmanGeneral.getLastName() + "+" + apiSportsmanGeneral.getFirstName() + "+" + apiSportsmanGeneral.getMiddleName());
        }
    }

    @AfterSuite
    public void finish() {
        if (driver != null) {
            driver.quit();
        }
    }
}
