import diskAPI.*;
import diskAPI.endPoints.EndPointsApi;
import diskAPI.utils.Utils;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.hamcrest.CoreMatchers.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

@Feature("asd")
public class ApiDisk {

    public static String nameRandom = "";

    @BeforeEach
    void beforeTest() {
        nameRandom = Utils.folder();
        Methods.getFolders("", Map.of())
                .statusCode(200)
                .body("_embedded.items.name", everyItem(not(equalTo(nameRandom))));
    }

    @Test
    @DisplayName("Тест номер 1")
    void firstTest() throws InterruptedException {

        //Создаем папку
        Methods.putFolders(nameRandom, Map.of())
                .statusCode(201);

        //Проверяем, что папка существует
        Methods.getFolders("", Map.of())
                .statusCode(200)
                .body("_embedded.items.name", hasItem(equalTo(nameRandom)));

        //Удаляем папку
        Methods.deleteFolders(nameRandom, Map.of());

        //Проверяем, что нет папки
        Methods.getFolders("", Map.of())
                .statusCode(200)
                .body("_embedded.items.name", everyItem(not(equalTo(nameRandom))));
    }

    @Test
    @DisplayName("Тест номер 2")
    void doubleTest() throws InterruptedException {
        String file = Utils.file();

        //Создаем папку
        Methods.putFolders(nameRandom, Map.of())
                .statusCode(201);

        //Создаем файл в папке
        Methods.postFile(nameRandom + "/" + file, EndPointsApi.urlFile, Map.of())
                .statusCode(202);

        //Удаляем файл в папке
        Methods.deleteFolders(nameRandom + "/" + file, Map.of());

        //Проверяем, что файл удален
        Methods.getFolders("", Map.of())
                .statusCode(200)
                .body("_embedded.items.name", everyItem(not(equalTo(file))));

        //Удаляем папку
        Methods.deleteFolders(nameRandom, Map.of());

        //Проверяем, что папка удалена
        Methods.getFolders("", Map.of())
                .statusCode(200)
                .body("_embedded.items.name", everyItem(not(equalTo(nameRandom))));
    }

    @Test
    @DisplayName("Тест номер 3")
    void threeTest() throws InterruptedException {
        String file1 = Utils.file();

        //Создаем папку
        Methods.putFolders(nameRandom, Map.of())
                .statusCode(201);

        //Создаем в папке файл1
        Methods.postFile(nameRandom + "/" + file1, "https://drive.google.com/", Map.of())
                .statusCode(202);

        //Перенести файл1 в корзину
        Methods.deleteFolders(nameRandom + "/" + file1, Map.of());

        //Восстановить файл1 из корзины
        Methods.restoreFolders(file1, Map.of());

        //Перенести файл1 в корзину
        Methods.deleteFolders(nameRandom + "/" + file1, Map.of());

        //Перенести папку в корзину
        Methods.deleteFolders(nameRandom, Map.of());

        //Проверить, что папка удалена
        Methods.getFolders("", Map.of())
                .statusCode(200)
                .body("_embedded.items.name", everyItem(not(equalTo(nameRandom))));
    }

    @Test
    @DisplayName("Тест номер 4")
    void fourTest() throws InterruptedException {
        String file1 = Utils.file();
        String file2 = Utils.file();

        //Создаем папку
        Methods.putFolders(nameRandom, Map.of())
                .statusCode(201);

        //Создаем в папке файл1
        Methods.postFile(nameRandom + "/" + file1, "https://drive.google.com/", Map.of())
                .statusCode(202);

        //Создаем в папке файл2
        Methods.postFile(nameRandom + "/" + file2, "https://drive.google.com/", Map.of())
                .statusCode(202);

        //Посчитать размер файлов в корзине
        int sumBeforeFull=Utils.getElementsTrashed();

        //Перенести файл1 в корзину
        Methods.deleteFolders(nameRandom + "/" + file1, Map.of());
        //Перенести файл2 в корзину
        Methods.deleteFolders(nameRandom + "/" + file2, Map.of());

        //Посчитать размер 2 файлов в корзине
        List list=
                Methods.getTrashResource("",Map.of())
                        .statusCode(200)
                        .extract().response().jsonPath()
                        .get("_embedded.items.findAll {it.name.equals(\""+file1+"\") || it.name.equals( \""+file2+"\"  )} ");

        int sumFile = Utils.sumSize(list);

        //Сравниваем, что файлов в корзине == первоначальному + созданным файлам
        assert(sumFile+sumBeforeFull==Utils.getElementsTrashed());

        //Восстановить файл1 из корзины
        Methods.restoreFolders(file1, Map.of());

        //Перенести файл1 в корзину
        Methods.deleteFolders(nameRandom + "/" + file1, Map.of());

        //Перенести папку в корзину
        Methods.deleteFolders(nameRandom, Map.of());
    }

    @Test
    @DisplayName("Тест номер 5")
    void fiveTest() throws InterruptedException {
        String file1 = Utils.file();
        String folder1 = Utils.folder();

        //Создаем папку1
        Methods.putFolders(nameRandom, Map.of())
                .statusCode(201);

        //Создаем папку2
        Methods.putFolders(nameRandom+"/"+folder1, Map.of())
                .statusCode(201);

        //Создаем файл1
        Methods.postFile(nameRandom+"/"+folder1+ "/" + file1, "https://drive.google.com/", Map.of())
                .statusCode(202);

        Methods.getFolders(nameRandom, Map.of())
                .statusCode(200)
                .body("_embedded.items.name", everyItem(not(equalTo(nameRandom))))
                .body("_embedded.items.name", everyItem((equalTo(folder1))))
                .body("_embedded.items.name", everyItem(not(equalTo(file1))));

        //Перенести папку в корзину
        Methods.deleteFolders(nameRandom, Map.of());

        //Проверяем, что папка перенесена в корзину
        Methods.getTrashResource("",Map.of())
                .statusCode(200)
                .body("_embedded.items.name", hasItem(equalTo(nameRandom)));

        //Проверяем, что папка1 перенесена в корзину
        Methods.getTrashResource(nameRandom,Map.of())
               .statusCode(200)
              .body("_embedded.items.name", hasItem(equalTo(folder1)));

        //Проверяем, что файл перенесен в корзину
        Methods.getTrashResource(nameRandom+"/"+folder1,Map.of())
                .statusCode(200)
                .body("_embedded.items.name", everyItem(equalTo(file1)));

    }

    @Test
    @DisplayName("Тест номер 6")
    void sixTest() throws InterruptedException {
        String file1 = Utils.file();
        String folder1 = Utils.folder();

        //Создаем папку
        Methods.putFolders(nameRandom, Map.of())
                .statusCode(201);

        //Создаем папку1 в папке
        Methods.putFolders(nameRandom+"/"+folder1, Map.of())
                .statusCode(201);

        //Создаем файл1 в папке1
        Methods.postFile(nameRandom+"/"+folder1+ "/" + file1, "https://drive.google.com/", Map.of())
                .statusCode(202);

        //Перенести папку в корзину
        Methods.deleteFolders(nameRandom, Map.of());

        //Очистить корзину
        Methods.deleteTrashResource("", Map.of());

        //Получить информацию об элементах в корзине
        Methods.getTrashResource("",Map.of())
                .statusCode(200)
                .body("_embedded.items.size",equalTo(0));
    }

}