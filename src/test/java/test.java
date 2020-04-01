import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class test {

    @Test
    @DisplayName("Тест номер 1")
    void firstTest() throws InterruptedException, IOException {

        String nameFolderRandom = "newfolder " + Functional.randomString();

        Methods.getFolders("/")
                .statusCode(200)
                .body("_embedded.items.name", everyItem(not(equalTo(nameFolderRandom))));

        Methods.putFolders("/" + nameFolderRandom)
                .statusCode(201);

        Methods.getFolders("/")
                .statusCode(200)
                .body("_embedded.items.name", hasItem(equalTo(nameFolderRandom)));

        Methods.deleteFolders("/" + nameFolderRandom)
                .statusCode(204);

        Methods.getFolders("/")
                .statusCode(200)
                .body("_embedded.items.name", everyItem(not(equalTo(nameFolderRandom))));

    }

}