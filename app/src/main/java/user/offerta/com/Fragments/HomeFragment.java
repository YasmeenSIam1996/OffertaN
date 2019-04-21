package user.offerta.com.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import user.offerta.com.API.ResponseAdvertisement;
import user.offerta.com.API.ResponseCategoryContent;
import user.offerta.com.API.ResponseError;
import user.offerta.com.API.ResponseLastProducts;
import user.offerta.com.API.ResponseMainCategory;
import user.offerta.com.API.UserAPI;
import user.offerta.com.Activities.CategoryContentActivity;
import user.offerta.com.Activities.ProductListActivity;
import user.offerta.com.Adspters.MainCategoryAdapter;
import user.offerta.com.Adspters.ProductAdapter;
import user.offerta.com.Adspters.ViewPagerAdapterImage;
import user.offerta.com.Interfaces.ContentClickListener;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.Module.Advertisement;
import user.offerta.com.Module.MainCategory;
import user.offerta.com.Module.Product;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;
import user.offerta.com.Utils.ZoomOutPageTransformer;


public class HomeFragment extends Fragment {

    private RecyclerView MainCatRecyclerView, LastProductRecyclerView;
    private MainCategoryAdapter mainCategoryAdapter;
    private List<MainCategory> categories;
    private Context Activity = getActivity();
    private FrameLayout frameLoading;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private List<Advertisement> Advertisements;
    private ViewPagerAdapterImage viewPagerAdapterImage;
    private ViewPager viewPager;
    private TextView tittle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        intiViews(view);
        actionViews();
        return view;
    }

    private void actionViews() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
//                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
//                tittle.startAnimation(animation);
                tittle.setText(Advertisements.get(i).getName());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    private void intiViews(View view) {
        categories = new Vector<>();
        productList = new Vector<>();
        Advertisements = new Vector<>();
        MainCatRecyclerView = view.findViewById(R.id.MainCatRecyclerView);
        LastProductRecyclerView = view.findViewById(R.id.LastProductRecyclerView);
        frameLoading = view.findViewById(R.id.frameLoading);
        viewPager = view.findViewById(R.id.viewPager);
        tittle = view.findViewById(R.id.tittle);
        setUpAdapterMainCategories();
        setUpAdapterLastProducts();
        setUpAdapterAdvertisement();
        frameLoading.setVisibility(View.VISIBLE);
        getMainCategories();
        getProducts();
        getAdvertisements();
    }

    private void setUpAdapterMainCategories() {
        mainCategoryAdapter = new MainCategoryAdapter(categories, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        MainCatRecyclerView.setLayoutManager(mLayoutManager);
        MainCatRecyclerView.setItemAnimator(new DefaultItemAnimator());
        MainCatRecyclerView.setAdapter(mainCategoryAdapter);
        MainCatRecyclerView.setHasFixedSize(true);
        mainCategoryAdapter.setOnItemClickListener(new ContentClickListener() {
            @Override
            public void onItemClickListener(int position, View view, MainCategory category) {
                getCategoryContent(category.getId() + "", "1",category);
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
                        intent.putExtra("ProductList",(Serializable) responseCategoryContent.getCategoryContent().getProducts());
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

    private void setUpAdapterLastProducts() {
        productAdapter = new ProductAdapter(productList, getActivity());
        LastProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        LastProductRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LastProductRecyclerView.setAdapter(productAdapter);
        LastProductRecyclerView.setHasFixedSize(true);

    }

    private void setUpAdapterAdvertisement() {
        viewPagerAdapterImage = new ViewPagerAdapterImage(getActivity(), Advertisements);
        viewPager.setAdapter(viewPagerAdapterImage);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

    }


    private void getMainCategories() {
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
                    Constants.showDialog((android.app.Activity) Activity, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog((android.app.Activity) Activity, message);
            }
        });
    }


    private void getProducts() {
        new UserAPI().LastProduct(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseLastProducts responseLastProducts = (ResponseLastProducts) result;
                if (responseLastProducts.isStatus()) {
                    productList.clear();
                    productList.addAll(responseLastProducts.getLastProducts());
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog((android.app.Activity) Activity, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(getActivity(), message);
            }
        });
    }


    private void getAdvertisements() {
        new UserAPI().Advertisements(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseAdvertisement advertisement = (ResponseAdvertisement) result;
                if (advertisement.isStatus()) {
                    Advertisements.clear();
                    Advertisements.addAll(advertisement.getAdvertisements());
                    viewPagerAdapterImage.notifyDataSetChanged();
                    setupAutoPager(advertisement.getAdvertisements().size());
                }
                tittle.setText(Advertisements.get(0).getName());

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog((android.app.Activity) Activity, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(getActivity(), message);
            }
        });
    }

    private int currentPage = 0;
    private Timer timer;

    private void setupAutoPager(final int imagesSize) {
        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {

                viewPager.setCurrentItem(currentPage, true);
                if (currentPage == imagesSize) {
                    currentPage = 0;
                } else {
                    ++currentPage;
                }
            }
        };


        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 500, 2000);
    }
}
