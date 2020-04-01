import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class Specs {


    public static RequestSpecification requestSpecification() {
        RequestSpecification requestSpec = given()
                .baseUri("https://cloud-api.yandex.net:443")
                .header("Authorization", "AgAAAAA-mge6AADLWxsH7ocSbEhOhmdCz4x6WwY")
                .contentType(ContentType.JSON)
                .when().log().all();
        return requestSpec;
    }

    public static Response url(RequestSpecification s, String params) {
        Response response = get(params);
        return response;
    }
}
