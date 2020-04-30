import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class StatusCodeTests {
    @Test
    public void okStatusCheck() {
        RestAssured
                .get("http://localhost:8080/list/1")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void rightElementReturned() {
        RestAssured
                .get("http://localhost:8080/list/1")
                .then()
                .assertThat()
                .body("id", equalTo(1));
    }

    @Test
    public void notFoundStatusCheck() {
        RestAssured
                .get("http://localhost:8080/list/10")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void notFoundErrorMessageCheck() {
        String errorMessage = RestAssured
                .get("http://localhost:8080/list/10")
                .getBody()
                .asString();
        assertEquals("Not found", errorMessage);
    }

    @Test
    public void okStatusCheck2() {
        RestAssured.baseURI = "https://localhost";
        RestAssured.port = 8080;
        String description = "write some shit";
        String requestBody = "{\n\"description\":\"" + description + "\"}";
        Response response = null;
        try {
            RestAssured
                    .given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .when()
                    .post("/list")
                    .then()
                    .assertThat()
                    .statusCode(201);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}