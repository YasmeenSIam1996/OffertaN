package user.offerta.com.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

import user.offerta.com.API.ResponseAddToCart;
import user.offerta.com.API.ResponseError;
import user.offerta.com.API.ResponseSearchProducts;
import user.offerta.com.API.UserAPI;
import user.offerta.com.Adspters.ProductListAdapter;
import user.offerta.com.Interfaces.OnLoadMoreListener;
import user.offerta.com.Interfaces.ProductClickListener;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.Module.Product;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;

import static android.util.Log.e;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView RecyclerSearchResult;
    private EditText edt_search;
    private FrameLayout frameLoading;
    private List<Product> productList;
    private int page = 1, LastNumPage = 1;
    private ProductListAdapter productAdapter;
    private TextView tittle;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        RecyclerSearchResult = findViewById(R.id.RecyclerSearchResult);
        edt_search = findViewById(R.id.edt_search);
        frameLoading = findViewById(R.id.frameLoading);
        productList = new Vector<>();
        tittle = findViewById(R.id.tittle);
        tittle.setText(getResources().getString(R.string.SEARCH));
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this, HomeActivity.class));
                finish();
            }
        });
        setUpAdapterLastProducts();

        edt_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Search(edt_search.getText().toString().trim(), page, false);

            }
        });

    }


    private void Search(String searchText, int page_, final boolean isLoad) {
        frameLoading.setVisibility(View.VISIBLE);
        new UserAPI().setSearch(searchText, page_, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseSearchProducts result1 = (ResponseSearchProducts) result;
                if (result1.isStatus()) {
                    if (isLoad == false) {
                        productList.clear();
                    }
                    productList.addAll(result1.getSearchProduct().getProductList());
                    productAdapter.notifyDataSetChanged();
                    LastNumPage = result1.getSearchProduct().getLast_page();
                } else {
                    Constants.showDialog(SearchActivity.this, result1.getMessage());
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(SearchActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(SearchActivity.this, message);
            }
        });
    }

    private void setUpAdapterLastProducts() {
        productAdapter = new ProductListAdapter(productList, SearchActivity.this, new OnLoadMoreListener() {
            @Override
            public void onLoadMore(int position) {
                e("onLoadMore", "yes" + page + "         " + LastNumPage);
                if (page >= LastNumPage) {
                    productAdapter.setLoaded(false);
                } else {
                    page++;
                    e("onLoadMore", "yes" + page + "         " + LastNumPage);

                    Search(edt_search.getText().toString().trim(), page, true);
                }
            }
        });
        RecyclerSearchResult.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));
        RecyclerSearchResult.setItemAnimator(new DefaultItemAnimator());
        RecyclerSearchResult.setAdapter(productAdapter);
        RecyclerSearchResult.setHasFixedSize(true);
        productAdapter.setOnItemClickListener(new ProductClickListener() {
            @Override
            public void onItemClickListener(int position, View view, Product product) {
                addToCart(product.getProduct_id() + "", "1", "0", "0");
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SearchActivity.this, HomeActivity.class));
        finish();
    }


    private void addToCart(String ProductId, String Quantity, String Color_Id, String Size_Id) {
        frameLoading.setVisibility(View.VISIBLE);

        new UserAPI().AddToCart(ProductId, Quantity, Color_Id, Size_Id, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                final ResponseAddToCart result1 = (ResponseAddToCart) result;
                if (result1.isStatus()) {
                    if (result1.getMessage().trim().equals("The product already exists in the cart")) {
                        Constants.showDialog(SearchActivity.this, result1.getMessage());
                    } else {
                        Toast.makeText(SearchActivity.this, result1.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(SearchActivity.this, ((ResponseAddToCart) result).getMessage(), Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(SearchActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(SearchActivity.this, message);
            }
        });
    }

}
