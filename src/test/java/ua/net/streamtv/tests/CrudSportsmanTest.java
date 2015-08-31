package ua.net.streamtv.tests;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ua.net.streamtv.entities.UiSportsman;
import ua.net.streamtv.guiceConfiguration.GuiceTestClass;
import ua.net.streamtv.pages.LoginPage;
import ua.net.streamtv.utils.TestListener;

/**
 * Created by nskrypka on 8/28/2015.
 */
@Listeners(TestListener.class)
@Guice(modules = GuiceTestClass.class)
public class CrudSportsmanTest {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Inject
    LoginPage loginPage;

//    Scenario: Add new sportsman
//    Given delete all profiles for Auto+Test+Anna sportsman
//    Given user login to streamtv site
//    When user adds new sportsman
//    | Last Name | Date of Birth | Region    | Fst    | Style | Year | First Name | Middle Name | Region1   | Fst1  | Age    |
//            | Auto      | 20-12-2011    | Poltavska | Dinamo | FS    | 2015 | Test       | Anna        | Poltavska | Kolos | Junior |
//    Then he is added successfully
//
//    Scenario: Edit sportsman
//    Given user login to streamtv site
//    When user change last name to Test for Auto Test Anna sportsman
//    Then changes for Test Anna are made successfully
//    | Last Name | Date of Birth | Region    | Fst    | Style | Year | First Name | Middle Name | Region1   | Fst1  | Age    |
//            | Test      | 20-12-2011    | Poltavska | Dinamo | FS    | 2015 | Test       | Anna        | Poltavska | Kolos | Junior |
//
//    Scenario: Delete sportsman
//    Given user login to streamtv site
//    When user delete Test Test Anna sportsman
//    Then he is deleted successfully
    @Test(dataProviderClass = UiSportsman.class, dataProvider = "randomUiSportsman")
    public void addSportsmanTest() {
        loginPage.openSite();
        loginPage.login();
    }
}
