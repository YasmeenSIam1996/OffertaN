package user.offerta.com.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;

import user.offerta.com.API.ResponseError;
import user.offerta.com.API.ResponseLocation;
import user.offerta.com.API.ResponseObject;
import user.offerta.com.API.UserAPI;
import user.offerta.com.Adspters.LocationAdapter;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.Module.LocationData;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;

public class PaymentMethodActivity extends AppCompatActivity {
    private RecyclerView PlacesRecycle;
    private LinearLayout add_new_place;
    private LocationAdapter locationAdapter;
    private List<LocationData> locationDataList;
    private Button send;
    private FrameLayout frameLoading;
    private TextView price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_payment_method);
        findViews();
        setUpLocationAdapter();
    }

    private void findViews() {
        PlacesRecycle = findViewById(R.id.PlacesRecycle);
        add_new_place = findViewById(R.id.add_new_place);
        locationDataList = new Vector<>();
        frameLoading = findViewById(R.id.frameLoading);
        send = findViewById(R.id.send);
        price=findViewById(R.id.price);
        price.setText(getIntent().getStringExtra("Price"));
        add_new_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentMethodActivity.this, MapsActivity.class));
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationAdapter.getLat() != 0) {
                    MackOrder(locationAdapter.getLat() + "", locationAdapter.getLang() + "", "1", "_");
                } else {
                    Constants.showDialog(PaymentMethodActivity.this, getResources().getString(R.string.ChoiceLocation));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetAddress();
    }

    private void GetAddress() {

        new UserAPI().GetAddress(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseLocation responseObject = (ResponseLocation) result;
                if (responseObject.isStatus()) {
                    locationDataList.clear();
                    locationDataList.addAll(responseObject.getLocationData());
                    locationAdapter.notifyDataSetChanged();
                } else {
                    Constants.showDialog(PaymentMethodActivity.this, responseObject.getMessage());
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;

                }
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(PaymentMethodActivity.this, message);
            }
        });
    }


    private void setUpLocationAdapter() {
        locationAdapter = new LocationAdapter(locationDataList, PaymentMethodActivity.this, false);
        PlacesRecycle.setLayoutManager(new LinearLayoutManager(PaymentMethodActivity.this, LinearLayoutManager.VERTICAL, false));
        PlacesRecycle.setItemAnimator(new DefaultItemAnimator());
        PlacesRecycle.setAdapter(locationAdapter);
        PlacesRecycle.setHasFixedSize(true);
    }

    private void MackOrder(final String lat, final String lng, final String payment, final String comment) {
        frameLoading.setVisibility(View.VISIBLE);

        new UserAPI().MackOrder(lat, lng, payment, comment, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject responseObject = (ResponseObject) result;
                if (responseObject.isStatus()) {
                    Intent intent = new Intent(PaymentMethodActivity.this, HomeActivity.class);
                    intent.putExtra("PaymentMethodActivity", 1);
                    startActivity(intent);
                } else {
                    frameLoading.setVisibility(View.GONE);
                    Constants.showDialog(PaymentMethodActivity.this, responseObject.getMessage());
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(PaymentMethodActivity.this, responseError.getMessage());

                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(PaymentMethodActivity.this, message);
            }
        });
    }


}
