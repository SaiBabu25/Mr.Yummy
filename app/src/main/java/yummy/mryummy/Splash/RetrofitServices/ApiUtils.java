package yummy.mryummy.Splash.RetrofitServices;


/**
 * Created by acer on 10/12/2017.
 */

public class ApiUtils {
    public static final String BASE_URL = "http://openingsin.com/mryummy_admin/services/user/";

    public static MyYummyServices getyummyservice() {
        return  RetrofitClient.getClient(BASE_URL).create(MyYummyServices.class);
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

}
