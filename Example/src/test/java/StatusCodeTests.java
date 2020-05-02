import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class StatusCodeTests {
    @Test
    public void getStatusAndIdCheck() {
        RestAssured
                .get("http://localhost:8080/list/1")
                .then()
                .assertThat()
                .statusCode(200)
                .assertThat()
                .body("id",equalTo(1));
    }

    @Test
    public void getNotFoundStatusCheck() {
        RestAssured
                .get("http://localhost:8080/list/100")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void getNotFoundErrorMessageCheck() {
        String errorMessage = RestAssured
                .get("http://localhost:8080/list/10")
                .getBody()
                .asString();
        assertEquals("Not found", errorMessage);
    }

    @Test
    public void postStatusAndBodyCheck() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        String description = "something";
        String requestBody = "{\n\"description\":\"" + description + "\"}";
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/list")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .then()
                .assertThat()
                .body("description",equalTo(description));
    }

    @Test
    public void deleteStatusCheck() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        int id = 17;
        RestAssured
                .given()
                .delete("/list/" + id)
                .then()
                .assertThat()
                .statusCode(200);
    }
}