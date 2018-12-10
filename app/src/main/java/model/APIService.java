package model;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    //Obtener lista de medios de pagos
    @GET("payment_methods")
    Observable<List<PayMedio>> getPayMedio(@Query("public_key") String key);

    //Obtener lista de bancos
    @GET("payment_methods/card_issuers")
    Observable<List<Bank>> getBank(@Query("public_key") String key,@Query("payment_method_id") String card);

    //Obtener lista de bancos
    @GET("payment_methods/installments")
    Observable<List<Quota>> getCuotas(@Query("public_key") String key,@Query("amount") String amount
            ,@Query("payment_method_id") String payment_method_id,@Query("issuer.id") String issuer_id);
}
