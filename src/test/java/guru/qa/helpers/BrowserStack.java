package guru.qa.helpers;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.net.MalformedURLException;
import java.net.URL;

import static io.restassured.RestAssured.given;

public class BrowserStack {

    public static URL getVideoUrl(String sessionId){
        Response response = given()
                .auth().basic("bsuser_0ICnjh", "WyBWBXh51LtBL12SNRrq")
                .when()
                .get(String.format("https://api.browserstack.com/app-automate/sessions/%s.json",
                        sessionId
                        ))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        try {
            URL url = new URL(response.jsonPath().getString("automation_session.video_url"));
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
