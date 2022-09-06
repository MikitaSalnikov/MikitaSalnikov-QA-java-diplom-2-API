package api.client;

import io.restassured.RestAssured;
import io.restassured.response.Response;


import java.io.File;

import static io.restassured.RestAssured.given;

public class RequestsUtil {

    String testSrcPath = "src/test/resources/";

    public Response postReq(String jsonBody, String url) {
        File json = new File(testSrcPath + jsonBody);
        Response post =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .post(url);
        return post;
    }
    public Response postReq(String accessToken, String jsonBody, String url) {
        File json = new File(testSrcPath + jsonBody);
        Response post =
                given()
                        .header("Content-type", "application/json")
                        .header("Authorization", accessToken)
                        .and()
                        .body(json)
                        .when()
                        .post(url);
        return post;
    }
    public Response getReq(String accessToken, String url) {
        Response get =
                given()
                        .header("Content-type", "application/json")
                        .header("Authorization", accessToken)
                        .when()
                        .get(url);
        return get;
    }
    public Response deleteReq(String accessToken, String url) {
        Response delete =
                given()
                        .header("Content-type", "application/json")
                        .header("Authorization", accessToken)
                        .when()
                        .delete(url);
        return delete;
    }
    public Response patchReq(String accessToken, String jsonBody, String url) {
        File json = new File(testSrcPath + jsonBody);
        Response patch =
                given()
                        .header("Content-type", "application/json")
                        .header("Authorization", accessToken)
                        .and()
                        .body(json)
                        .when()
                        .patch(url);
        return patch;
    }
}
