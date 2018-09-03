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
import model.PayMedio;
import model.Variables;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPayMedio {
    public static List<PayMedio> payMedio;
    public static void getPayMedio(final Context context, final GridView listView, final LinearLayout linearLayout, final ProgressBar progressBar) {
        Factory.getIntance()
                .getPayMedio(Variables.getPublic_key()).enqueue(new Callback<List<PayMedio>>() {
            @Override
            public void onResponse(Call<List<PayMedio>> call, Response<List<PayMedio>> response) {
                if (response.isSuccessful()) {
                    try {
                        payMedio = response.body();
                        listView.setAdapter(new PayMedioAdapter(context, payMedio));
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
            public void onFailure(Call<List<PayMedio>> call, Throwable t) {
                Toast.makeText(context, "Error de conexion",
                        Toast.LENGTH_LONG).show();
                linearLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
