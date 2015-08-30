package ua.net.streamtv.steps;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public ApiSteps() {
        this.createUrl = "http://streamtv.net.ua/base/php/wrestler/create.php";
        this.login = "auto";
        this.password = "test";
        this.loginUrl = "http://streamtv.net.ua/base/php/login.php";
        this.readUrl = "http://streamtv.net.ua/base/php/wrestler/read.php?id={id}";
        this.searchUrl = "http://streamtv.net.ua/base/php/wrestler/search.php?count=25&filters=%7B%7D&order=lname&search={search}&start=0";
        this.updateUrl = "http://streamtv.net.ua/base/php/wrestler/update.php";
        this.deleteUrl = "http://streamtv.net.ua/base/php/wrestler/delete.php?id={id}";
        this.sessionId = loginThroughApi();
    }

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

    public ApiSportsman readSportsman(String id) {
        Response response = given().cookie("PHPSESSID", sessionId).
                when().get(readUrl, id);
        response.then().statusCode(200);
        ApiSportsman sportsman = new Gson().fromJson(response.asString(), ApiSportsman.class);
        assertThat(response.asString(), not(containsString("Invalid query")));
        LOG.info("Read operation through API was performed for sportsman " + sportsman.toString());
        return sportsman;
    }

    public String searchForSportsman(String searchParameter) {
        String sportsmanId = "";
        Response response = given().cookie("PHPSESSID", sessionId).
                when().get(searchUrl, searchParameter);
        response.then().statusCode(200);
        assertThat(response.asString(), not(containsString("Invalid query")));
        ArrayList<ApiSportsman> rows = new Gson().fromJson(response.asString(), new TypeToken<ArrayList<ApiSportsman>>(){}.getType());
        if (rows.size() == 0) {
            sportsmanId = rows.get(0).getId();
            LOG.info(sportsmanId + " sportsman was found through API for search parameter " + searchParameter);
        }
        return sportsmanId;
    }

    public void updateSportsman(ApiSportsman sportsman) {
        String givenSportsman = new Gson().toJson(sportsman);
        LOG.info("********************************************************* " + givenSportsman);
        Response response = given().contentType(JSON).cookie("PHPSESSID", sessionId).body(givenSportsman).
                when().post(updateUrl);
        response.then().statusCode(200);
        response.prettyPrint();
        assertThat(response.asString(), not(containsString("Invalid query")));
        LOG.info(sportsman.getLastName() + " sportsman was updated successfully through API");
    }

    public void deleteSportsman(String sporsmanId) {
        Response response = given().cookie("PHPSESSID", sessionId).
                when().delete(deleteUrl, sporsmanId);
        response.then().statusCode(200);
        assertThat(response.asString(), not(containsString("Invalid query")));
        LOG.info(sporsmanId + " sportsman was deleted successfully");
    }

    public void deleteAllProfiles(String fio) {
        String sportsmanId;
        while (!"".equals(sportsmanId = searchForSportsman(fio))) {
            deleteSportsman(sportsmanId);
        }
    }
}
