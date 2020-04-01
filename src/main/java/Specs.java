import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class Specs {

    public static RequestSpecification requestSpecification(Map<String, String> pathParams) {
        RequestSpecification requestSpec = given()
                .baseUri(EndPoints.baseUri)
                .header("Authorization", EndPoints.token)
                .contentType(ContentType.JSON).params(pathParams)
                .when().log().all();
        return requestSpec;
    }

}
