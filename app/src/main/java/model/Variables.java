package model;

public class Variables {
    private static String url = "https://api.mercadopago.com/v1/";
    private static String public_key = "444a9ef5-8a6b-429f-abdf-587639155d88";

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        Variables.url = url;
    }

    public static String getPublic_key() {
        return public_key;
    }

    public static void setPublic_key(String public_key) {
        Variables.public_key = public_key;
    }
}
