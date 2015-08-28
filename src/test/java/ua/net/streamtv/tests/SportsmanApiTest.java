package ua.net.streamtv.tests;

import com.jayway.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;

/**
 * Created by nskrypka on 8/28/2015.
 */
public class SportsmanApiTest {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Test
    public void addSportsmanThroughApiTest() throws JSONException {
        LOG.info("Start test Add sprotsman through API");
        login();
        JSONObject jsonObject = new JSONObject(sportsman);
        Response response = given().contentType(JSON).cookie("PHPSESSID", sessionId).body(jsonObject.toString()).
                when().post(createUrl);
        response.then().statusCode(200);
        JSONObject jsonObject1 = new JSONObject(response.asString());
        String sportsmanId = jsonObject1.get("id").toString();

    }

    private String login() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "auto");
        credentials.put("password", "test");
        JSONObject jsonObject = new JSONObject(credentials);
        Response response = given().contentType(JSON).body(jsonObject.toString()).
                when().post("http://streamtv.net.ua/base/php/login.php");
        response.then().statusCode(200);
        LOG.info("User was logged in successfully through API");
        String sessionId = response.cookie("PHPSESSID");
        return sessionId;
    }
}
