package connection;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.henry.mercadopago.BankFragment;
import com.henry.mercadopago.QuotasFragment;
import com.henry.mercadopago.R;

import java.util.List;

import adapter.BankAdapter;
import model.Bank;
import model.Variables;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetBank {
    public static List<Bank> banks;
    public static void getBank(final String amount, final String id_pay_medio, final String id_pay, final Context context, final GridView listView, final LinearLayout linearLayout, final ProgressBar progressBar, final BankFragment bankFragment) {
        Factory.getIntance()
                .getBank(Variables.getPublic_key(), id_pay).enqueue(new Callback<List<Bank>>() {
            @Override
            public void onResponse(Call<List<Bank>> call, Response<List<Bank>> response) {
                if (response.isSuccessful()) {
                    try {
                        banks = response.body();
                        if (banks.isEmpty()){
                            bankFragment.sendBank(amount,id_pay_medio);
                        }
                        else {
                            listView.setAdapter(new BankAdapter(context, banks));
                        }
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
            public void onFailure(Call<List<Bank>> call, Throwable t) {
                Toast.makeText(context, "Error de conexion",
                        Toast.LENGTH_LONG).show();
                linearLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
