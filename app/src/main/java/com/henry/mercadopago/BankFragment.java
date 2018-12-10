package com.henry.mercadopago;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import adapter.BankAdapter;
import connection.Factory;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.APIService;
import model.Bank;
import model.Variables;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BankFragment extends Fragment {
    Context context;
    View rootView;
    LinearLayout linearLayout;
    ProgressBar progressBar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "amount";
    private static final String ARG_PARAM2 = "id_pay_medio";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public BankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BankFragment newInstance(String param1,String param2) {
        BankFragment fragment = new BankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_bank, container, false);

        context = (Context) getActivity();

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        linearLayout = (LinearLayout) rootView.findViewById(R.id._linear_layout_bank);
        GridView gridView = (GridView) rootView.findViewById(R.id._gridViewBank);
        //GetBank.getBank(mParam1,mParam2,GetPayMedio.payMedio.get(Integer.parseInt(mParam2)).getId(),context,gridView,linearLayout,progressBar, this);

        getList(gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                QuotasFragment fr = new QuotasFragment();
                Bundle bn = new Bundle();
                bn.putString("amount",mParam1);
                bn.putString("id_pay_medio",mParam2);
                bn.putString("bank", String.valueOf(position));
                fr.setArguments(bn);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment,fr)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }

    public void sendBank(){
        QuotasFragment fr = new QuotasFragment();
        Bundle bn = new Bundle();
        bn.putString("amount",mParam1);
        bn.putString("id_pay_medio",mParam2);
        bn.putString("bank","");
        fr.setArguments(bn);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment,fr)
                .addToBackStack(null)
                .commit();
    }

    public void getList(final GridView gridView){
        APIService service = Factory.getClient().create(APIService.class);

        service.getBank(Variables.getPublic_key(),Variables.getPayMedios().get(Integer.parseInt(mParam2)).getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Bank>>() {

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
                        if (Variables.getBanks().isEmpty()){
                            sendBank();
                        }
                        else {
                            linearLayout.setVisibility(View.VISIBLE);
                            gridView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Bank> banksResult) {
                        Variables.setBanks(banksResult);
                        gridView.setAdapter(new BankAdapter(context, banksResult));
                    }
                });
    }

}
