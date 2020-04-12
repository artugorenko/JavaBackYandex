package diskAPI.utils;

import diskAPI.Methods;
import diskAPI.endPoints.TimeOut;
import io.restassured.response.ValidatableResponse;
import org.json.JSONArray;
import java.util.*;
import static diskAPI.GeneralMethods.allMethod;

public class Utils {

    public static String randomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    public static int sumSize(List list) throws InterruptedException {
        JSONArray jsonArrayTask3 = new JSONArray(list);
        int sum = 0;
        for (int i = 0; i < jsonArrayTask3.length(); i++) {
            if (jsonArrayTask3.getJSONObject(i).has("size"))
                sum += (Integer) jsonArrayTask3.getJSONObject(i).get("size");
        }
        return sum;
    }

    public static int getElementsTrashed() throws InterruptedException {
        List list =
                Methods.getTrashResource("", Map.of())
                        .statusCode(200)
                        .extract().response().jsonPath()
                        .get("_embedded.items");
        return Utils.sumSize(list);
    }

    public static String folder() {
        return "newFolder" + Utils.randomString();
    }

    public static String file() {
        return "newFile" + Utils.randomString() + ".txt";
    }

}
