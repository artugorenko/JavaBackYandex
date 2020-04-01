import io.restassured.response.ValidatableResponse;

import java.util.*;

public class Methods extends Specs {

    public static Map<String, String> params = new HashMap<String, String>();

    public static ValidatableResponse allMethod(String method, String endPoint, Map<String, String> pathParams) {
        ValidatableResponse response =
                requestSpecification(pathParams)
                        .request(method, endPoint)
                        .then()
                        .assertThat()
                        .log().all();
        return response;
    }

    public static ValidatableResponse getFolders(Map<String, String> pathParams) {
        return allMethod("GET", EndPoints.FileAndFolder, params);

    }

    public static ValidatableResponse putFolders(Map<String, String> pathParams) {
        return allMethod("PUT", EndPoints.FileAndFolder, pathParams);
    }

    public static ValidatableResponse deleteFolders(Map<String, String> pathParams) {
        return allMethod("DELETE", EndPoints.FileAndFolder, pathParams);
    }
}
