package edu.neag.tia.RestClient.client;

public class Utils {

    public static final String BASE_URL = "http://192.168.0.115:8082/";

    public static UserService getUserService() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

}
