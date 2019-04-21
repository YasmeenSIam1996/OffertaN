package user.offerta.com.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import user.offerta.com.API.ResponseCategoryContent;
import user.offerta.com.API.ResponseError;
import user.offerta.com.API.ResponseMainCategory;
import user.offerta.com.API.UserAPI;
import user.offerta.com.Activities.CategoryContentActivity;
import user.offerta.com.Activities.ProductListActivity;
import user.offerta.com.Adspters.MainCategoryAdapter;
import user.offerta.com.Interfaces.ContentClickListener;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.Module.MainCategory;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;


public class CatFragment extends Fragment {

    private RecyclerView CatRecycler;
    private FrameLayout frameLoading;
    private MainCategoryAdapter mainCategoryAdapter;
    private List<MainCategory> categories;


    public static CatFragment newInstance() {
        CatFragment fragment = new CatFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cat, container, false);
        CatRecycler = view.findViewById(R.id.CatRecycler);
        frameLoading = view.findViewById(R.id.frameLoading);
        categories = new Vector<>();

        setUpAdapterMainCategories();
        getMainCategories();
        return view;
    }


    private void setUpAdapterMainCategories() {
        mainCategoryAdapter = new MainCategoryAdapter(categories, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 4);
        CatRecycler.setLayoutManager(mLayoutManager);
        CatRecycler.setItemAnimator(new DefaultItemAnimator());
        CatRecycler.setAdapter(mainCategoryAdapter);
        CatRecycler.setHasFixedSize(true);
        mainCategoryAdapter.setOnItemClickListener(new ContentClickListener() {
            @Override
            public void onItemClickListener(int position, View view, MainCategory category) {
                getCategoryContent(category.getId() + "", "1", category);
            }
        });

    }

    private void getCategoryContent(String category_id, String page, final MainCategory category) {
        frameLoading.setVisibility(View.VISIBLE);
        new UserAPI().CategoryContent(category_id, page, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseCategoryContent responseCategoryContent = (ResponseCategoryContent) result;
                if (responseCategoryContent.isStatus()) {
                    if (!responseCategoryContent.getCategoryContent().isFinalCont()) {
                        Intent intent = new Intent(getActivity(), CategoryContentActivity.class);
                        intent.putExtra("CategoryContent", category);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        Intent intent = new Intent(getActivity(), ProductListActivity.class);
                        intent.putExtra("ProductList", (Serializable) responseCategoryContent.getCategoryContent().getProducts());
                        startActivity(intent);
                    }

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


    private void getMainCategories() {
        frameLoading.setVisibility(View.VISIBLE);
        new UserAPI().MainCategories(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseMainCategory responseCart = (ResponseMainCategory) result;
                if (responseCart.isStatus()) {
                    categories.clear();
                    categories.addAll(responseCart.getMainCategories());
                    mainCategoryAdapter.notifyDataSetChanged();
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
