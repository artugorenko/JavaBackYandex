package diskAPI.utils;

import diskAPI.endPoints.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class Specs {
    public static RequestSpecification requestSpecification() {
        RequestSpecification requestSpec = given()
                .baseUri(EndPointsApi.baseUri)
                .header("Authorization", Auth.token)
                .contentType(ContentType.JSON)
                .filter(new AllureRestAssured())
                .when();
        return requestSpec;
    }
}
