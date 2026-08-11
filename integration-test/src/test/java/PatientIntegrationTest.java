import com.sun.nio.sctp.PeerAddressChangeNotification;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class PatientIntegrationTest {
    @BeforeAll
    static void setUp(){
        RestAssured.baseURI="http://localhost:4004";
    }

    @Test
    public void shouldReturnPatientsWithValidToken() throws IOException, InterruptedException {
        String payLoad= """
                {
                "email":"testuser@test.com",
                "password":"password123"
                }
                """;
        new ProcessBuilder("docker", "start", "auth-service")
                .inheritIO()
                .start()
                .waitFor();
        Thread.sleep(10000);
        String token=given()
                .contentType("application/json")
                .body(payLoad)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token",notNullValue())
                .extract().jsonPath().getString("token");
        Response response =given()
                .header("Authorization","Bearer "+token)
                .when()
                .get("/api/patients")
                .then()
                .statusCode(200)
                .body("patients",notNullValue())
                .extract().response();
//        System.out.println("RAW RESPONSE: " + response.getBody().asPrettyString());
        List<String> names = response.jsonPath().getList("name");
        System.out.println(names); // [John Doe, Jane Smith]

    }
}
