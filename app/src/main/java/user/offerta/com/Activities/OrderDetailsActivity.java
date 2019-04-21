package user.offerta.com.Activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;

import user.offerta.com.API.ResponseError;
import user.offerta.com.API.ResponseOrderDetails;
import user.offerta.com.API.UserAPI;
import user.offerta.com.Adspters.ProductDetailsItemAdapter;
import user.offerta.com.Fragments.RateFragment;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.Module.ProductItem;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;

public class OrderDetailsActivity extends AppCompatActivity {

    private FrameLayout frameLoading;
    private RecyclerView productList;
    private List<ProductItem> productItems;
    private ProductDetailsItemAdapter productAdapter;
    private TextView price_val, Delivery_price_val, Total_val, tittle, address, date, Coupon, cash;
    private ImageView back;
    private FragmentTransaction fragmentTransaction;
    private View view2, view3;
    private ImageView point2, point3, point1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_order_details);
        intiViews();
        setUpAdapterLastProducts();
        int orderNum = getIntent().getIntExtra("orderId", 0);
        getOrderDetails(orderNum + "");
        tittle.setText(getResources().getString(R.string.ORDER) + " " + orderNum);
    }

    private void intiViews() {
        frameLoading = findViewById(R.id.frameLoading);
        productList = findViewById(R.id.productList);
        productItems = new Vector<>();
        price_val = findViewById(R.id.price_val);
        Delivery_price_val = findViewById(R.id.Delivery_price_val);
        Total_val = findViewById(R.id.Total_val);
        tittle = findViewById(R.id.tittle);
        back = findViewById(R.id.back);
        address = findViewById(R.id.address);
        date = findViewById(R.id.date);
        Coupon = findViewById(R.id.Coupon);
        cash = findViewById(R.id.cash);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        point2 = findViewById(R.id.point2);
        point3 = findViewById(R.id.point3);
        point1 = findViewById(R.id.point1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getOrderDetails(String orderId) {

        frameLoading.setVisibility(View.VISIBLE);

        new UserAPI().getOrderDetails(orderId, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseOrderDetails responseObject = (ResponseOrderDetails) result;
                if (responseObject.isStatus()) {
                    productItems.addAll(responseObject.getOrderItem().getOrder().getProduct());
                    productAdapter.notifyDataSetChanged();
                    price_val.setText(responseObject.getOrderItem().getOrder().getPrice() + "");
                    Delivery_price_val.setText(responseObject.getOrderItem().getShipping() + "");
                    Total_val.setText(responseObject.getOrderItem().getOrder().getTotal_price() + "");

                    address.setText(responseObject.getOrderItem().getOrder().getDestination() + "");
                    date.setText(responseObject.getOrderItem().getOrder().getDate() + "");
                    Coupon.setText("0.0");
                    if (responseObject.getOrderItem().getOrder().getPayment() == 0) {
                        cash.setText(getResources().getString(R.string.CASH) + "");
                    }

                    if (responseObject.getOrderItem().getOrder().getStatus() == 3) {
                        RateFragment(productItems);
                        view3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                        point1.setBackgroundResource(R.drawable.point_un_select);
                        point2.setBackgroundResource(R.drawable.point_un_select);
                        point3.setBackgroundResource(R.drawable.point_select);

                    } else if (responseObject.getOrderItem().getOrder().getStatus() == 2) {
                        view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        point1.setBackgroundResource(R.drawable.point_un_select);
                        point2.setBackgroundResource(R.drawable.point_select);

                    }
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(OrderDetailsActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(OrderDetailsActivity.this, message);
            }
        });
    }

    private void setUpAdapterLastProducts() {
        productAdapter = new ProductDetailsItemAdapter(productItems, OrderDetailsActivity.this);
        productList.setLayoutManager(new LinearLayoutManager(OrderDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
        productList.setItemAnimator(new DefaultItemAnimator());
        productList.setAdapter(productAdapter);
        productList.setHasFixedSize(true);
    }

    public void RateFragment(List<ProductItem> products) {
        RateFragment rateFragment = RateFragment.newInstance(products);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, rateFragment).commit();
    }

}
