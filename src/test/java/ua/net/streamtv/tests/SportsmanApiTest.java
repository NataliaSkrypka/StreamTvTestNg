package ua.net.streamtv.tests;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import ru.yandex.qatools.allure.annotations.Issue;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.model.SeverityLevel;
import ua.net.streamtv.entities.ApiSportsman;
import ua.net.streamtv.guiceConfiguration.GuiceTestModule;
import ua.net.streamtv.steps.ApiSteps;
import ua.net.streamtv.utils.TestListener;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by nskrypka on 8/28/2015.
 */
@Guice(modules = {GuiceTestModule.class})
@Listeners({TestListener.class})
public class SportsmanApiTest {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Inject
    ApiSteps apiSteps;

    @Inject
    WebDriver driver;

    private String sportsmanId;

    @Test(dataProviderClass = ApiSportsman.class, dataProvider = "randomSportsman", description = "Test case for testing ability to add sportdman through API", priority = 1)
    @Severity(SeverityLevel.BLOCKER)
    public void addSportsmanThroughApiTest(ApiSportsman sportsman) {
        LOG.info("Sportsman for test " + sportsman.toString());

        sportsmanId = apiSteps.addSportsman(sportsman);
        ApiSportsman actualSportsman = apiSteps.readSportsman(sportsmanId);
        actualSportsman.setId(null);
        assertThat("Sportsman after adding through API is not as expected", actualSportsman, equalTo(sportsman));
    }

    @Test(dataProviderClass = ApiSportsman.class, dataProvider = "randomSportsman", description = "Test case for testing ability to update sportsman through API",priority = 2)
    @Severity(SeverityLevel.BLOCKER)
    public void updateSportsmanThroughApiTest(ApiSportsman sportsman) {
        ApiSportsman sportsmanAfterAdd = apiSteps.readSportsman(sportsmanId);
        sportsmanAfterAdd.setLastName(sportsman.getLastName());
        sportsmanAfterAdd.setId(sportsmanId);
        sportsmanAfterAdd.setFirstName(sportsman.getFirstName());
        sportsmanAfterAdd.setMiddleName(sportsman.getMiddleName());
        LOG.info("Sportsman for update operation " + sportsmanAfterAdd.toString());

        apiSteps.updateSportsman(sportsmanAfterAdd);

        ApiSportsman actualSportsman = apiSteps.readSportsman(sportsmanId);
        assertThat("Sportsman after update through API is not as expected", actualSportsman, equalTo(sportsmanAfterAdd));
    }

    @Test(description = "Test case for testing ability to delete sportsman through API",priority = 3)
    @Severity(SeverityLevel.BLOCKER)
    public void deleteSportsmanThroughAPITest() {
        ApiSportsman sportsmanBeforeDelete = apiSteps.readSportsman(sportsmanId);
        apiSteps.deleteSportsman(sportsmanId);

        String actualSportsmanId = apiSteps.searchForSportsman(sportsmanBeforeDelete.getLastName());

        assertThat("Sportsman was not deleted through API", actualSportsmanId, equalTo(""));
    }

    @AfterSuite
    public void finish(){
        if(driver != null){
            driver.quit();
        }
    }
}
