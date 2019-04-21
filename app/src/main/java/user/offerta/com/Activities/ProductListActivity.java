package user.offerta.com.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;
import java.util.Vector;

import user.offerta.com.API.ResponseAddToCart;
import user.offerta.com.API.ResponseError;
import user.offerta.com.API.ResponseProductDetails;
import user.offerta.com.API.UserAPI;
import user.offerta.com.Adspters.ProductListAdapter;
import user.offerta.com.Interfaces.ProductClickListener;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.Module.Product;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;

public class ProductListActivity extends AppCompatActivity {
    private RecyclerView ProductRecyclerView;
    private ProductListAdapter productAdapter;
    private TextView count;
    private LottieAnimationView cartAnimation;
    private ImageView filter, search;
    private FrameLayout frameLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_product_list);
        intiViews();
        List<Product> productList = (List<Product>) getIntent().getSerializableExtra("ProductList");
        productList.add(productList.get(0));
        setUpAdapterLastProducts(productList);
        Log.e("LastProducts", ((List<Product>) getIntent().getSerializableExtra("ProductList")).size() + "");
        productAdapter.notifyDataSetChanged();
        getCartListCount();
    }

    private void intiViews() {
        ProductRecyclerView = findViewById(R.id.ProductRecyclerView);
        count = findViewById(R.id.num_product_cart);
        cartAnimation = findViewById(R.id.cartAnimation);
        filter = findViewById(R.id.filter);
        search = findViewById(R.id.search);
        frameLoading = findViewById(R.id.frameLoading);

        cartAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductListActivity.this, CartActivity.class));
                finish();
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductListActivity.this, FilterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductListActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void setUpAdapterLastProducts(List<Product> productList) {
        productAdapter = new ProductListAdapter(productList, ProductListActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        ProductRecyclerView.setLayoutManager(mLayoutManager);
        ProductRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ProductRecyclerView.setAdapter(productAdapter);
        ProductRecyclerView.setHasFixedSize(true);
        productAdapter.setOnItemClickListener(new ProductClickListener() {
            @Override
            public void onItemClickListener(int position, View view, Product product) {
                addToCart(product.getProduct_id() + "", "1", "0", "0");

            }
        });
    }

    private void getCartListCount() {
        frameLoading.setVisibility(View.VISIBLE);
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
                    Constants.showDialog(ProductListActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(ProductListActivity.this, message);
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
                        Constants.showDialog(ProductListActivity.this, result1.getMessage());
                        count.setText(result1.getCarts().getCount() + "");
                    } else {
                        Toast.makeText(ProductListActivity.this, result1.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(ProductListActivity.this, ((ResponseAddToCart) result).getMessage(), Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(ProductListActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(ProductListActivity.this, message);
            }
        });
    }

}
