package guru.qa.helpers;

import guru.qa.helpers.models.SessionDetails;
import org.apache.http.HttpStatus;

import static guru.qa.configuration.ConfigurationManager.*;
import static io.restassured.RestAssured.given;

public class BrowserStack {

    public static SessionDetails getSessionDetails(String sessionId){

        return given()
                .auth().basic(
                        getBrowserStackConfig().userName(),
                        getBrowserStackConfig().accessKey()
                )
                .when()
                .get(String.format(
                        "%s/app-automate/sessions/%s.json",
                        getBrowserStackConfig().browserstackApiUrl(),
                        sessionId
                ))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getObject("automation_session", SessionDetails.class);

    }

}
