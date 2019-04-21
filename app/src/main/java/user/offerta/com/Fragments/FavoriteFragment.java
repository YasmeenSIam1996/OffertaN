package user.offerta.com.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;
import java.util.Vector;

import user.offerta.com.API.ResponseError;
import user.offerta.com.API.ResponseLastProducts;
import user.offerta.com.API.UserAPI;
import user.offerta.com.Adspters.FavoriteAdapter;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.Module.Product;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;


public class FavoriteFragment extends Fragment {
    private RecyclerView RecycleFavorite;
    private FavoriteAdapter favoriteAdapter;
    private List<Product> productItems;
    private FrameLayout frameLoading;

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        intiView(view);
        setUpAdapterProducts();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFavorite();

    }

    private void intiView(View view) {
        RecycleFavorite = view.findViewById(R.id.RecycleFavorite);
        frameLoading = view.findViewById(R.id.frameLoading);
        productItems = new Vector<>();

    }


    private void setUpAdapterProducts() {
        favoriteAdapter = new FavoriteAdapter(productItems, getActivity());
        RecycleFavorite.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        RecycleFavorite.setItemAnimator(new DefaultItemAnimator());
        RecycleFavorite.setAdapter(favoriteAdapter);
        RecycleFavorite.setHasFixedSize(true);
    }


    private void getFavorite() {

        frameLoading.setVisibility(View.VISIBLE);

        new UserAPI().getFavorite(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseLastProducts responseObject = (ResponseLastProducts) result;
                if (responseObject.isStatus()) {
                    productItems.clear();
                    productItems.addAll(responseObject.getLastProducts());
                    favoriteAdapter.notifyDataSetChanged();
                    if (responseObject.getLastProducts().size() == 0) {
                        Constants.showDialog(getActivity(), "Your favorite products empty");
                    }
                } else {
                    Constants.showDialog(getActivity(), responseObject.getMessage());
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(getActivity(), responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(getActivity(), message);
            }
        });
    }


}
