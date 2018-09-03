package com.henry.mercadopago;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import connection.GetBank;
import connection.GetPayMedio;
import connection.GetQuotas;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayFragment extends Fragment {
    Context context;
    View rootView;
    LinearLayout linearLayout;
    ProgressBar progressBar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "amount";
    private static final String ARG_PARAM2 = "id_pay_medio";
    private static final String ARG_PARAM3 = "bank";
    private static final String ARG_PARAM4 = "quotas";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;


    public PayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PayFragment newInstance(String param1, String param2, String param3, String param4) {
        PayFragment fragment = new PayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_pay, container, false);
        context = (Context) getActivity();

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        linearLayout = (LinearLayout) rootView.findViewById(R.id._linear_layout_main);
        final EditText amount = (EditText) rootView.findViewById(R.id._edit_text_amount);
        final LinearLayout result = (LinearLayout) rootView.findViewById(R.id._linear_layout_result);
        if (mParam1!=null && mParam2!=null && mParam3!=null && mParam4!=null){
            TextView result_amount = (TextView) rootView.findViewById(R.id._textViewAmount);
            TextView result_pay_medio = (TextView) rootView.findViewById(R.id._textViewPayMedio);
            TextView result_bank = (TextView) rootView.findViewById(R.id._textViewBank);
            TextView result_quotas = (TextView) rootView.findViewById(R.id._textViewQuotas);
            result_amount.setText(mParam1);
            result_pay_medio.setText(GetPayMedio.payMedio.get(Integer.parseInt(mParam2)).getName());
            //LinearLayout bank =
            if (mParam3.equals(""))
                rootView.findViewById(R.id._linear_layout_bank).setVisibility(View.GONE);
            else {
                rootView.findViewById(R.id._linear_layout_bank).setVisibility(View.VISIBLE);
                result_bank.setText(GetBank.banks.get(Integer.parseInt(mParam3)).getName());
            }
            result_quotas.setText(GetQuotas.payerCosts.get(Integer.parseInt(mParam4)).getRecommendedMessage());
            linearLayout.setVisibility(View.GONE);
            result.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
        else {
            result.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
        Button next = (Button) rootView.findViewById(R.id._button_main);
        Button make_other_pay = (Button) rootView.findViewById(R.id._button_make_other_pay);

        next.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (amount.getText().toString().equals("") || Integer.parseInt(amount.getText().toString()) == 0){
                            Toast.makeText(context, "Indique un monto mayor a cero",
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            PayMedioFragment fr = new PayMedioFragment();
                            Bundle bn = new Bundle();
                            bn.putString("amount",amount.getText().toString());
                            fr.setArguments(bn);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment,fr)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    }
                }
        );

        make_other_pay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        result.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );

        return rootView;
    }

}
