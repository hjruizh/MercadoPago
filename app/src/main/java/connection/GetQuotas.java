package connection;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import adapter.PayMedioAdapter;
import adapter.QuotaAdapter;
import model.PayMedio;
import model.PayerCost;
import model.Quota;
import model.Variables;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetQuotas {
    public static List<PayerCost> payerCosts;
    public static void getQuotas(String amount, String payment_method_id, String issuer_id, final Context context, final ListView listView, final LinearLayout linearLayout, final ProgressBar progressBar) {
        Factory.getIntance()
                .getCuotas(Variables.getPublic_key(),amount,payment_method_id,issuer_id).enqueue(new Callback<List<Quota>>() {
            @Override
            public void onResponse(Call<List<Quota>> call, Response<List<Quota>> response) {
                if (response.isSuccessful()) {
                    try {
                        payerCosts = response.body().get(0).getPayerCosts();
                        listView.setAdapter(new QuotaAdapter(context, payerCosts));
                    } catch (Exception x) {
                        Toast.makeText(context, "Error al asignar adapter " + x.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "Error al obtener los datos",
                            Toast.LENGTH_LONG).show();
                }
                linearLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Quota>> call, Throwable t) {
                Toast.makeText(context, "Error de conexion",
                        Toast.LENGTH_LONG).show();
                linearLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
