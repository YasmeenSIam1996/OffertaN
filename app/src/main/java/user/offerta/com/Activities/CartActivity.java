package user.offerta.com.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;

import user.offerta.com.API.ResponseError;
import user.offerta.com.API.ResponseMainCart;
import user.offerta.com.API.ResponseObject;
import user.offerta.com.API.UserAPI;
import user.offerta.com.Adspters.CartAdapter;
import user.offerta.com.App.ApplicationController;
import user.offerta.com.Interfaces.CartClickListener;
import user.offerta.com.Interfaces.CartDeleteClickListener;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.Module.Product;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;

public class CartActivity extends AppCompatActivity {

    private FrameLayout frameLoading;
    private CartAdapter cartAdapter;
    private RecyclerView cart_list;
    private List<Product> products;
    private Button Continue, pay;
    private TextView tittle, shipping_val, Total_val, FinalTotalSize_value, tax_val;
    private ImageView back;
    private Dialog dialog;
    private String PriceFinaly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_cart);
        intiViews();
        setUpAdapterCartList();
        getCartList();
    }


    private void setUpAdapterCartList() {
        cartAdapter = new CartAdapter(products, CartActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        cart_list.setLayoutManager(mLayoutManager);
        cart_list.setItemAnimator(new DefaultItemAnimator());
        cart_list.setAdapter(cartAdapter);
        cart_list.setHasFixedSize(true);
        cartAdapter.setOnItemClickListener(new CartClickListener() {
            @Override
            public void onItemClickListener(int position, View view, Product product, View numText, int num) {
                UpdateQuantity(product, num + "", (TextView) numText, view);
            }
        });
        cartAdapter.setOnItemClickListener(new CartDeleteClickListener() {
            @Override
            public void onItemClickListener(int position, View view, Product product) {
                showCustomDialog(product);
            }
        });
    }

    private void intiViews() {
        frameLoading = findViewById(R.id.frameLoading);
        cart_list = findViewById(R.id.cart_list);
        products = new Vector<>();
        Continue = findViewById(R.id.Continue);
        pay = findViewById(R.id.pay);
        tittle = findViewById(R.id.tittle);
        back = findViewById(R.id.back);
        shipping_val = findViewById(R.id.shipping_val);
        Total_val = findViewById(R.id.Total_val);
        FinalTotalSize_value = findViewById(R.id.FinalTotalSize_value);
        tax_val = findViewById(R.id.tax_val);
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, HomeActivity.class));
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (products.size() == 0) {
                    Constants.showDialog(CartActivity.this, getResources().getString(R.string.NoPay));
                } else if (ApplicationController.getInstance().getUser().getPhone().trim().equals("") || ApplicationController.getInstance().getUser().getEmail().trim().equals("")) {
                    showCustomDialog();
                } else {
                    Intent intent = new Intent(CartActivity.this, PaymentMethodActivity.class);
                    intent.putExtra("Price", PriceFinaly);
                    startActivity(intent);
                }
            }
        });
        tittle.setText(getResources().getText(R.string.CART));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, HomeActivity.class));
                finish();
            }
        });
    }

    private void getCartList() {
        frameLoading.setVisibility(View.VISIBLE);
        new UserAPI().GetCartList(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseMainCart result1 = (ResponseMainCart) result;
                if (result1.isStatus()) {
                    if (result1.getMainCart().getProducts().size() == 0) {
                        Constants.showDialog(CartActivity.this, result1.getMessage());
                    }
                    products.clear();
                    products.addAll(result1.getMainCart().getProducts());
                    cartAdapter.notifyDataSetChanged();
                    shipping_val.setText(result1.getMainCart().getShipping() + "");
                    Total_val.setText(result1.getMainCart().getTotal_price() + "");
                    FinalTotalSize_value.setText(result1.getMainCart().getGrant_totally() + "");
                    tax_val.setText(result1.getMainCart().getTax() + "");
                    PriceFinaly = result1.getMainCart().getGrant_totally() + "";
                } else {
                    Constants.showDialog(CartActivity.this, ((ResponseMainCart) result).getMessage());
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(CartActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(CartActivity.this, message);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CartActivity.this, HomeActivity.class));
        finish();
    }

    private void UpdateQuantity(final Product product, final String count, final TextView textView, final View view) {
        frameLoading.setVisibility(View.VISIBLE);
        view.setEnabled(false);
        new UserAPI().UpdateQuantity(product.getProduct_id() + "", count, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject responseObject = (ResponseObject) result;
                if (responseObject.isStatus()) {
                    textView.setText(count + "");
                    getCartList();
                } else {
                    Constants.showDialog(CartActivity.this, responseObject.getMessage());
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(CartActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);
                view.setEnabled(true);

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(CartActivity.this, message);
            }
        });
    }


    public void showCustomDialog() {
        dialog = new Dialog(CartActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);

        Button yes = dialog.findViewById(R.id.yes);
        Button no = dialog.findViewById(R.id.no);
        ImageView icon = dialog.findViewById(R.id.icon);
        icon.setVisibility(View.GONE);
        TextView textView = dialog.findViewById(R.id.text);
        textView.setText(getResources().getString(R.string.CopleteData));
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ApplicationController.getInstance().IsUserLoggedIn()) {
                    Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                    intent.putExtra("Data", "CompleteProfile");
                    startActivity(intent);
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                    finish();
                }

            }
        });

        dialog.show();

    }


    private void deleteProduct(final Product product) {
        dialog.dismiss();
        new UserAPI().DeleteProduct(product.getProduct_id() + "", new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject responseObject = (ResponseObject) result;
                if (responseObject.isStatus()) {
                    products.remove(product);
                    cartAdapter.notifyDataSetChanged();
                    getCartList();
                } else {
                    Constants.showDialog((Activity) CartActivity.this, responseObject.getMessage());

                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog((Activity) CartActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog((Activity) CartActivity.this, message);
            }
        });
    }


    public void showCustomDialog(final Product product) {
        dialog = new Dialog(CartActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog);

        Button yes = dialog.findViewById(R.id.yes);
        Button no = dialog.findViewById(R.id.no);
        TextView textView = dialog.findViewById(R.id.text);
        textView.setText(getResources().getString(R.string.delete_item));
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct(product);
            }
        });

        dialog.show();

    }

}
