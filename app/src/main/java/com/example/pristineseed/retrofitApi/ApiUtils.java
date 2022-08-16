package com.example.pristineseed.retrofitApi;

public class ApiUtils {

     public  static final String BASE_URL = "https://hytechdev.pristinefulfil.com";

    //public static  final  String BASE_URL="https://hytechlive.pristinefulfil.com";

     //https://seedlive.pristinefulfil.com";//https://hcm.pristinefulfil.com
    public static final String notificationSignalRURL= "/Notification";

    public static NetworkInterface getPristineAPIService() {
        return NetworkClient.getClient(BASE_URL).create(NetworkInterface.class);
    }
}
