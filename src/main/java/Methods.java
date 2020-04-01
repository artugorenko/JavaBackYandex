import io.restassured.response.ValidatableResponse;

public class Methods extends Specs {


    public static ValidatableResponse getFolders(String pathParam) {
        ValidatableResponse response =
                requestSpecification().param("path", pathParam)
                        .get(EndPoints.FileAndFolder)
                        .then()
                        .assertThat();

        return response;
    }

    public static ValidatableResponse putFolders(String pathParam) {
        ValidatableResponse response =
                Specs.requestSpecification().param("path", pathParam).put(EndPoints.FileAndFolder)
                        .then().assertThat();
        return response;
    }

    public static ValidatableResponse deleteFolders(String pathParam) {
        ValidatableResponse response =
                Specs.requestSpecification().param("path", pathParam).delete(EndPoints.FileAndFolder)
                        .then().assertThat();
        return response;
    }
}
