package diskAPI.endPoints;

public class EndPointsApi {

    public static final String FileAndFolder = "/v1/disk/resources";
    public static final String baseUri = "https://cloud-api.yandex.net:443";
    public static final String createFile = FileAndFolder+"/upload";
    public static final String urlFile = "https://drive.google.com/";
    public static final String trashResource = "/v1/disk/trash/resources";
    public static final String restoreFile = trashResource+"/restore";

}
