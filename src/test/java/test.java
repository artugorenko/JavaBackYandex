import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class test {

    @Test
    @DisplayName("Тест номер 1")
    void firstTest() throws InterruptedException, IOException {

        String nameFolderRandom = "newfolder " + Functional.randomString();

        Methods.params.clear();
        Methods.params.put("path", "/");
        Methods.getFolders(Methods.params)
                .statusCode(200)
                .body("_embedded.items.name", everyItem(not(equalTo(nameFolderRandom))));

        Methods.params.clear();
        Methods.params.put("path", "/" + nameFolderRandom);
        Methods.putFolders(Methods.params)
                .statusCode(201);

        Methods.params.clear();
        Methods.params.put("path", "/");
        Methods.getFolders(Methods.params)
                .statusCode(200)
                .body("_embedded.items.name", hasItem(equalTo(nameFolderRandom)));

        Methods.params.clear();
        Methods.params.put("path", "/" + nameFolderRandom);
        Methods.deleteFolders(Methods.params)
                .statusCode(204);

        Methods.params.clear();
        Methods.params.put("path", "/");
        Methods.getFolders(Methods.params)
                .statusCode(200)
                .body("_embedded.items.name", everyItem(not(equalTo(nameFolderRandom))));


/*
                 given()
                .baseUri("https://cloud-api.yandex.net:443")
                .header("Authorization", "AgAAAAA-mge6AADLWxsH7ocSbEhOhmdCz4x6WwY")
                .contentType(ContentType.JSON)
                .param("path", pathParam)
                .when().log().all()
                .get(EndPoints.FileAndFolder)
                .then()
                .assertThat()
                .statusCode(200)
                .body("_embedded.items.name", everyItem(not(equalTo(nameFolderRandom))));
 */
    }

}