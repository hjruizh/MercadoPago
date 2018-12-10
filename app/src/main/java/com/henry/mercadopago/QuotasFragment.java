package com.henry.mercadopago;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import adapter.QuotaAdapter;
import connection.Factory;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.APIService;
import model.Quota;
import model.Variables;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuotasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuotasFragment extends Fragment {
    Context context;
    View rootView;
    LinearLayout linearLayout;
    ProgressBar progressBar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "amount";
    private static final String ARG_PARAM2 = "id_pay_medio";
    private static final String ARG_PARAM3 = "bank";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;


    public QuotasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuotasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuotasFragment newInstance(String param1, String param2, String param3) {
        QuotasFragment fragment = new QuotasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_quotas, container, false);

        context = (Context) getActivity();

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        linearLayout = (LinearLayout) rootView.findViewById(R.id._linear_layout_quotas);

        ListView quotas = (ListView) rootView.findViewById(R.id._listViewQuotas);

        getList(quotas);

        quotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                PayFragment fr = new PayFragment();
                Bundle bn = new Bundle();
                bn.putString("amount",mParam1);
                bn.putString("id_pay_medio",mParam2);
                bn.putString("bank",mParam3);
                bn.putString("quotas",String.valueOf(position));
                fr.setArguments(bn);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment,fr)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }

    public void getList(final ListView gridView){
        APIService service = Factory.getClient().create(APIService.class);
        String issuer_id = "";
        if (!mParam3.equals(""))
            issuer_id = Variables.getBanks().get(Integer.parseInt(mParam3)).getId();
        service.getCuotas(Variables.getPublic_key(),mParam1,Variables.getPayMedios().get(Integer.parseInt(mParam2)).getId()
                ,issuer_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Quota>>() {

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                        linearLayout.setVisibility(View.VISIBLE);
                        gridView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onComplete() {
                        linearLayout.setVisibility(View.VISIBLE);
                        gridView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Quota> quotasResult) {
                        Variables.setQuotas(quotasResult);
                        gridView.setAdapter(new QuotaAdapter(context, quotasResult.get(0).getPayerCosts()));
                    }
                });
    }

}
