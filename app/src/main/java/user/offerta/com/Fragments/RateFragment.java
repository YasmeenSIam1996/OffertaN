package user.offerta.com.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Vector;

import user.offerta.com.Adspters.ProductRateAdapter;
import user.offerta.com.Module.ProductItem;
import user.offerta.com.R;


public class RateFragment extends Fragment {
    private RecyclerView RateRecycler;
    static ProductRateAdapter productAdapter;
    static List<ProductItem> productItems;
    static List<ProductItem> productItems2;

    public static RateFragment newInstance(List<ProductItem> productItem) {
        RateFragment fragment = new RateFragment();
        productItems2 = productItem;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rate, container, false);
        RateRecycler = view.findViewById(R.id.RateRecycler);
        productItems = new Vector<>();
        productItems.addAll(productItems2);
        setUpAdapterLastProducts();

        return view;
    }

    private void setUpAdapterLastProducts() {
        productAdapter = new ProductRateAdapter(productItems, getActivity());
        RateRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RateRecycler.setItemAnimator(new DefaultItemAnimator());
        RateRecycler.setAdapter(productAdapter);
        RateRecycler.setHasFixedSize(true);
    }


}
