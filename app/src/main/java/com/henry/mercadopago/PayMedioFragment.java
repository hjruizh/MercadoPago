package com.henry.mercadopago;

import android.app.MediaRouteButton;
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

import adapter.PayMedioAdapter;
import connection.Factory;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.APIService;
import model.PayMedio;
import model.Variables;

public class PayMedioFragment extends Fragment {

    Context context;
    View rootView;
    LinearLayout linearLayout;
    ProgressBar progressBar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "amount";

    // TODO: Rename and change types of parameters
    private String mParam1;
    public PayMedioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment PayMedioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PayMedioFragment newInstance(String param1) {
        PayMedioFragment fragment = new PayMedioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_pay_medio, container, false);
        context = (Context) getActivity();

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        linearLayout = (LinearLayout) rootView.findViewById(R.id._linear_layout_pay_medio);
        final GridView gridView = (GridView) rootView.findViewById(R.id._gridViewPayMedio);
        getList(gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                BankFragment fr = new BankFragment();
                Bundle bn = new Bundle();
                bn.putString("amount",mParam1);
                bn.putString("id_pay_medio", String.valueOf(position));
                fr.setArguments(bn);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment,fr)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }

    public void getList(final GridView gridView){
        APIService service = Factory.getClient().create(APIService.class);

        service.getPayMedio(Variables.getPublic_key())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PayMedio>>() {

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
                    public void onNext(List<PayMedio> payMediosResult) {
                        Variables.setPayMedios(payMediosResult);
                        gridView.setAdapter(new PayMedioAdapter(context, payMediosResult));
                    }
                });
    }
}
