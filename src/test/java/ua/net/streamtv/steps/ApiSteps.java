package ua.net.streamtv.steps;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.jayway.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.allure.annotations.Step;
import ua.net.streamtv.entities.ApiSportsman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by nskrypka on 8/29/2015.
 */
public class ApiSteps {
    private String createUrl;
    private String login;
    private String password;
    private String loginUrl;
    private String readUrl;
    private String searchUrl;
    private String updateUrl;
    private String deleteUrl;
    private String sessionId;
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Inject
    public ApiSteps(@Named("api.create.url") String createUrl, @Named("api.login.url") String loginUrl, @Named("api.read.url") String readUrl, @Named("api.search.url") String searchUrl, @Named("api.update.url") String updateUrl, @Named("api.delete.url") String deleteUrl, @Named("login") String login, @Named("password") String password) {
        this.createUrl = createUrl;
        this.login = login;
        this.password = password;
        this.loginUrl = loginUrl;
        this.readUrl = readUrl;
        this.searchUrl = searchUrl;
        this.updateUrl = updateUrl;
        this.deleteUrl = deleteUrl;
        this.sessionId = loginThroughApi();
    }

    @Step
    public String addSportsman(ApiSportsman sportsman) {
        String givenSportsman = new Gson().toJson(sportsman);
        Response response = given().contentType(JSON).cookie("PHPSESSID", sessionId).body(givenSportsman).
                when().post(createUrl);
        response.then().statusCode(200);
        assertThat(response.asString(), not(containsString("Invalid query")));
        JsonObject result = new Gson().fromJson(response.asString(), JsonObject.class);
        LOG.info("Sportsman was added successfully");
        return result.get("id").getAsString();
    }

    public String loginThroughApi() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", login);
        credentials.put("password", password);
        String jsonObject = new Gson().toJson(credentials);
        Response response = given().contentType(JSON).body(jsonObject).
                when().post(loginUrl);
        response.then().statusCode(200);
        assertThat(response.asString(), not(containsString("false")));
        return response.cookie("PHPSESSID");
    }

    @Step
    public ApiSportsman readSportsman(String id) {
        Response response = given().cookie("PHPSESSID", sessionId).
                when().get(readUrl, id);
        response.then().statusCode(200);
        ApiSportsman sportsman = new Gson().fromJson(response.asString(), ApiSportsman.class);
        assertThat(response.asString(), not(containsString("Invalid query")));
        LOG.info("Read operation through API was performed for sportsman " + sportsman.toString());
        return sportsman;
    }

    @Step
    public String searchForSportsman(String searchParameter) {
        String sportsmanId = "";
        Response response = given().cookie("PHPSESSID", sessionId).
                when().get(searchUrl, searchParameter);
        response.then().statusCode(200);
        String responseString = response.asString();
        assertThat(responseString, not(containsString("Invalid query")));
        if (!responseString.contains("{\"total\":\"0\"")) {
            sportsmanId = response.then().extract().body().jsonPath().get("rows.id_wrestler").toString().replace("[","").replace("]","");
            LOG.info(sportsmanId + " sportsman was found through API for search parameter " + searchParameter);
        }
        return sportsmanId;
    }

    @Step
    public void updateSportsman(ApiSportsman sportsman) {
        String givenSportsman = new Gson().toJson(sportsman);
        Response response = given().contentType(JSON).cookie("PHPSESSID", sessionId).body(givenSportsman).
                when().post(updateUrl);
        response.then().statusCode(200);
        assertThat(response.asString(), not(containsString("Invalid query")));
        LOG.info(sportsman.getLastName() + " sportsman was updated successfully through API");
    }

    @Step
    public void deleteSportsman(String sporsmanId) {
        Response response = given().cookie("PHPSESSID", sessionId).
                when().delete(deleteUrl, sporsmanId);
        response.then().statusCode(200);
        assertThat(response.asString(), not(containsString("Invalid query")));
        LOG.info(sporsmanId + " sportsman was deleted successfully");
    }

    @Step
    public void deleteAllProfiles(String fio) {
        String sportsmanId;
        while (!"".equals(sportsmanId = searchForSportsman(fio))) {
            deleteSportsman(sportsmanId);
        }
    }
}
