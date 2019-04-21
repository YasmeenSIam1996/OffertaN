package user.offerta.com.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import user.offerta.com.API.ResponseAddToCart;
import user.offerta.com.API.ResponseCategoryContent;
import user.offerta.com.API.ResponseError;
import user.offerta.com.API.ResponseProductDetails;
import user.offerta.com.API.UserAPI;
import user.offerta.com.Adspters.MainCategoryAdapter;
import user.offerta.com.Adspters.ProductAdapter;
import user.offerta.com.Adspters.ProductListAdapter;
import user.offerta.com.Interfaces.ContentClickListener;
import user.offerta.com.Interfaces.ProductClickListener;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.Module.MainCategory;
import user.offerta.com.Module.Product;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;

import static user.offerta.com.Utils.Constants.Image_URL;

public class CategoryContentActivity extends AppCompatActivity {
    private FrameLayout frameLoading;
    private RecyclerView MainCatRecyclerView, LastProductRecyclerView;
    private MainCategoryAdapter mainCategoryAdapter;
    private List<MainCategory> categories;
    private ProductListAdapter productAdapter;
    private List<Product> productList;
    private ImageView product_image, filter,search;
    private TextView tittle;
    private int Page = 1;
    private int Page_stars_product = 1;
    private TextView count;
    private LottieAnimationView cartAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_category_content);
        intiViews();
        MainCategory mainCategory = (MainCategory) getIntent().getSerializableExtra("CategoryContent");
        setUpData(mainCategory);
        getCategoryContent(mainCategory.getId() + "", Page + "");
        getStaredProduct(mainCategory.getId() + "", Page_stars_product + "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCartListCount();

    }

    private void setUpData(MainCategory mainCategory) {
        tittle.setText(mainCategory.getName());
        Picasso.with(this).load(Image_URL + mainCategory.getImage()).into(product_image);
    }

    private void getCategoryContent(String category_id, String page) {
        frameLoading.setVisibility(View.VISIBLE);
        new UserAPI().CategoryContent(category_id, page, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseCategoryContent responseCategoryContent = (ResponseCategoryContent) result;
                if (responseCategoryContent.isStatus()) {
                    if (!responseCategoryContent.getCategoryContent().isFinalCont()) {
                        categories.clear();
                        categories.addAll(responseCategoryContent.getCategoryContent().getCategories());
                        mainCategoryAdapter.notifyDataSetChanged();
                    } else {
                        Intent intent = new Intent(CategoryContentActivity.this, ProductListActivity.class);
                        intent.putExtra("ProductList", (Serializable) responseCategoryContent.getCategoryContent().getProducts());
                        startActivity(intent);
                    }

                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(CategoryContentActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(CategoryContentActivity.this, message);
            }
        });
    }

    private void intiViews() {
        categories = new Vector<>();
        productList = new Vector<>();
        MainCatRecyclerView = findViewById(R.id.MainCatRecyclerView);
        LastProductRecyclerView = findViewById(R.id.LastProductRecyclerView);
        frameLoading = findViewById(R.id.frameLoading);
        product_image = findViewById(R.id.product_image);
        tittle = findViewById(R.id.tittle);
        cartAnimation = findViewById(R.id.cartAnimation);
        setUpAdapterMainCategories();
        setUpAdapterLastProducts();
        count = findViewById(R.id.num_product_cart);
        filter = findViewById(R.id.filter);
        search=findViewById(R.id.search);
        cartAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoryContentActivity.this, CartActivity.class));
                finish();
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryContentActivity.this, FilterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryContentActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void setUpAdapterMainCategories() {
        mainCategoryAdapter = new MainCategoryAdapter(categories, CategoryContentActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(CategoryContentActivity.this, 2, GridLayoutManager.HORIZONTAL, false);
        MainCatRecyclerView.setLayoutManager(mLayoutManager);
        MainCatRecyclerView.setItemAnimator(new DefaultItemAnimator());
        MainCatRecyclerView.setAdapter(mainCategoryAdapter);
        MainCatRecyclerView.setHasFixedSize(true);
        mainCategoryAdapter.setOnItemClickListener(new ContentClickListener() {
            @Override
            public void onItemClickListener(int position, View view, MainCategory category) {
                getCategoryContent(category.getId() + "", 1 + "");
            }
        });

    }

    private void setUpAdapterLastProducts() {
        productAdapter = new ProductListAdapter(productList, CategoryContentActivity.this);
        LastProductRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        LastProductRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LastProductRecyclerView.setAdapter(productAdapter);
        LastProductRecyclerView.setHasFixedSize(true);
        productAdapter.setOnItemClickListener(new ProductClickListener() {
            @Override
            public void onItemClickListener(int position, View view, Product product) {
                addToCart(product.getProduct_id() + "", "1", "0", "0");

            }
        });
        productAdapter.setLoaded(false);
    }


    private void getStaredProduct(String category_id, String page) {
        new UserAPI().StaredProduct(category_id, page, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseCategoryContent responseCategoryContent = (ResponseCategoryContent) result;
                if (responseCategoryContent.isStatus()) {
                    if (!responseCategoryContent.getCategoryContent().isFinalCont()) {
                        productList.addAll(responseCategoryContent.getCategoryContent().getProducts());
                        productAdapter.notifyDataSetChanged();

                    }

                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(CategoryContentActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(CategoryContentActivity.this, message);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }


    private void getCartListCount() {
        new UserAPI().GetCountCartList(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseAddToCart result1 = (ResponseAddToCart) result;
                if (result1.isStatus()) {
                    count.setText(result1.getCarts().getCount() + "");
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(CategoryContentActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(CategoryContentActivity.this, message);
            }
        });
    }


    private void addToCart(String ProductId, String Quantity, String Color_Id, String Size_Id) {
        frameLoading.setVisibility(View.VISIBLE);
        new UserAPI().AddToCart(ProductId, Quantity, Color_Id, Size_Id, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                final ResponseAddToCart result1 = (ResponseAddToCart) result;
                if (result1.isStatus()) {
                    if (result1.getMessage().trim().equals("The product already exists in the cart")) {
                        Constants.showDialog(CategoryContentActivity.this, result1.getMessage());
                        count.setText(result1.getCarts().getCount() + "");
                    } else {
                        Toast.makeText(CategoryContentActivity.this, result1.getMessage(), Toast.LENGTH_LONG).show();
                        count.setVisibility(View.GONE);
                        cartAnimation.playAnimation();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                count.setText(result1.getCarts().getCount() + "");
                                count.setVisibility(View.VISIBLE);
                            }
                        }, cartAnimation.getDuration() - 1000);
                    }

                } else {
                    Toast.makeText(CategoryContentActivity.this, ((ResponseAddToCart) result).getMessage(), Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(CategoryContentActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(CategoryContentActivity.this, message);
            }
        });
    }


}
