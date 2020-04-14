package diskAPI;

import diskAPI.endPoints.TimeOut;
import diskAPI.utils.Specs;
import io.restassured.response.ValidatableResponse;
import java.util.Map;

public class GeneralMethods {

    public static ValidatableResponse allMethod(String method, String endPoint, Map<String, String> pathParams) {
        ValidatableResponse response =
                Specs.requestSpecification().queryParams(pathParams).log().all()
                        .request(method, endPoint)
                        .then().log().all()
                        .assertThat();
        return response;
    }

    public static ValidatableResponse recursiveMethod(int code, String method, String url,
                                                      Map<String, String> pathParams) throws InterruptedException {
        int time = 0;
        while (time < TimeOut.timeOut) {
            ValidatableResponse response = allMethod(method, url, pathParams);
            if (response.extract().statusCode() == code) return response;
            else {
                Thread.sleep(TimeOut.timeSleep);
                time++;
                continue;
            }
        }
        assert (time < TimeOut.timeOut) : "TimeOut";
        return null;
    }

}
