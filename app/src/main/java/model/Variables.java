package model;

import java.util.List;

public class Variables {
    private static String url = "https://api.mercadopago.com/v1/";
    private static String public_key = "444a9ef5-8a6b-429f-abdf-587639155d88";
    private static List<Bank> banks;
    private static List<PayMedio> payMedios;
    private static List<Quota> quotas;


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

    public static List<Bank> getBanks() {
        return banks;
    }

    public static void setBanks(List<Bank> banks) {
        Variables.banks = banks;
    }

    public static List<PayMedio> getPayMedios() {
        return payMedios;
    }

    public static void setPayMedios(List<PayMedio> payMedios) {
        Variables.payMedios = payMedios;
    }

    public static List<Quota> getQuotas() {
        return quotas;
    }

    public static void setQuotas(List<Quota> quotas) {
        Variables.quotas = quotas;
    }
}
