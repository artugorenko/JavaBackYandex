package diskAPI;

import diskAPI.endPoints.*;
import diskAPI.utils.*;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.util.*;
import static diskAPI.GeneralMethods.allMethod;

public class Methods extends Specs {

    @Step("Получение информации о содержимом корзины")
    public static ValidatableResponse getTrashResource(String path, Map<String, String> pathParams) throws InterruptedException {
        pathParams = new HashMap<>(pathParams);
        pathParams.put("path", "/" + path);
        return allMethod("GET", EndPointsApi.trashResource, pathParams);
    }

    @Step("Очищаем корзину")
    public static ValidatableResponse deleteTrashResource(String path, Map<String, String> pathParams) throws InterruptedException {
        pathParams = new HashMap<>(pathParams);
        pathParams.put("path", "/" + path);
        return GeneralMethods.recursiveMethod(204,"DELETE", EndPointsApi.trashResource, pathParams);
    }

    @Step("Получаем информацию о папке")
    public static ValidatableResponse getFolders(String path, Map<String, String> pathParams) {
        pathParams = new HashMap<>(pathParams);
        pathParams.put("path", "/" + path);
        return allMethod("GET", EndPointsApi.FileAndFolder, pathParams);
    }

    @Step("Создаем папку")
    public static ValidatableResponse putFolders(String path, Map<String, String> pathParams) {
        pathParams = new HashMap<>(pathParams);
        pathParams.put("path", "/" + path);
        return allMethod("PUT", EndPointsApi.FileAndFolder, pathParams);
    }

    @Step("Переносим папку/файл в корзину")
    public static ValidatableResponse deleteFolders(String path, Map<String, String> pathParams) throws InterruptedException {
        pathParams = new HashMap<>(pathParams);
        pathParams.put("path", "/" + path);
        return GeneralMethods.recursiveMethod(404,"DELETE", EndPointsApi.FileAndFolder, pathParams);
    }

    @Step("Создаем файл")
    public static ValidatableResponse postFile(String path, String url, Map<String, String> pathParams) {
        pathParams = new HashMap<>(pathParams);
        pathParams.put("path", "/" + path);
        pathParams.put("url", url);
        return allMethod("POST", EndPointsApi.createFile, pathParams);
    }

    @Step("Восстанавливаем папку/файл из корзины")
    public static ValidatableResponse restoreFolders(String path, Map<String, String> pathParams) throws InterruptedException {
        pathParams = new HashMap<>(pathParams);
        pathParams.put("path", "/" + path);
        return GeneralMethods.recursiveMethod(404,"PUT", EndPointsApi.restoreFile, pathParams);
    }

}
