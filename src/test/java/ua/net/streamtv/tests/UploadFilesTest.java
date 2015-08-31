package ua.net.streamtv.tests;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import ua.net.streamtv.entities.ApiSportsman;
import ua.net.streamtv.guiceConfiguration.GuiceTestClass;
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
//
//        Scenario: Delete attachment from profile
//        Given user login to streamtv site
//        When user delete attachment for Sidorova Nata Anna profile
//        Then this sporsman's profile do not have attachments
@Listeners(TestListener.class)
@Guice(modules = GuiceTestClass.class)
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
    public void addPhotoToProfileTest(ApiSportsman apiSportsman) throws IOException {
        LOG.info("Sportsman profile to test photo upload " + apiSportsman.toString());
        apiSteps.addSportsman(apiSportsman);

        loginPage.openSite();
        loginPage.login();
        searchPage.searchForSportsman(apiSportsman.getLastName());
        searchPage.openSportsmanDetails();

        sportsmanDetailsPage.uploadPhoto(System.getProperty("user.dir") + "/src/test/resources/files/download2.jpg");
        sportsmanDetailsPage.closeSportsmanInfoTab();

        searchPage.searchForSportsman(apiSportsman.getLastName());
        searchPage.openSportsmanDetails();
        String downloadedPhotoPath = sportsmanDetailsPage.downloadPhoto();
        boolean arePhotosEqual = FileComparasionUtils.compareImages(System.getProperty("user.dir") + "/src/test/resources/files/expectedImage.png", downloadedPhotoPath);
        assertThat("Downloaded photo is not as expected", arePhotosEqual, is(true));
    }

    @Test(dataProviderClass = ApiSportsman.class, dataProvider = "randomSportsman", priority = 1)
    public void addAttachmentToProfileTest(ApiSportsman apiSportsman) throws IOException {
        LOG.info("Sportsman profile to test attachment upload " + apiSportsman.toString());
        this.apiSportsmanGeneral = apiSportsman;
        apiSteps.addSportsman(apiSportsman);

        loginPage.openSite();
        loginPage.login();
        searchPage.searchForSportsman(apiSportsman.getLastName());
        searchPage.openSportsmanDetails();

        String fileAbsolutePath = System.getProperty("user.dir") + "/src/test/resources/files/TaskforTechnicalInterviewNIAutomationv0.2.pdf";
        sportsmanDetailsPage.uploadFile(fileAbsolutePath);
        sportsmanDetailsPage.closeSportsmanInfoTab();

        searchPage.searchForSportsman(apiSportsman.getLastName());
        searchPage.openSportsmanDetails();
        String downloadedFilePath = sportsmanDetailsPage.downloadFile();
        boolean areFilesEqual = FileComparasionUtils.comparePdfs(downloadedFilePath, fileAbsolutePath);
        assertThat("Downloaded file is not as expected", areFilesEqual, is(true));
    }

    @Test(priority = 1)
    public void deleteAttachmentFromProfileTest() {
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
    }

    @AfterSuite
    public void finish() {
        if (driver != null) {
            driver.quit();
        }
    }
}
