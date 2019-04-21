package user.offerta.com.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Locale;

import user.offerta.com.API.ResponseError;
import user.offerta.com.API.ResponseObject;
import user.offerta.com.API.UserAPI;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.Module.Product;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private Marker MyMarker;
    private double latitude;
    private double longitude;
    private TextView tittle, address;
    private ImageView back;
    private float MapZoom = 14.0f;
    private FrameLayout frameLoading;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        findViews();

    }

    private void findViews() {
        tittle = findViewById(R.id.tittle);
        back = findViewById(R.id.back);
        address = findViewById(R.id.address);
        tittle.setText(getResources().getString(R.string.add_place));
        frameLoading = findViewById(R.id.frameLoading);
        save = findViewById(R.id.save);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAddress(latitude + "", longitude + "",address.getText().toString());
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MapsActivity.this, R.raw.style_json));

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        googleMap.clear();
        int locationMode = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.e("state_", "1");
            try {
                locationMode = Settings.Secure.getInt(MapsActivity.this.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            boolean b = (locationMode != Settings.Secure.LOCATION_MODE_OFF && locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY); //check location mode
            if (b) {
                Log.e("state_", "3");

                getCurrentLocation();

            } else {
                Log.e("state_", "4");
//                Toast.makeText(MapsActivity.this, getResources().getString(R.string.turn_on), Toast.LENGTH_SHORT).show();
                showSettingAlert();
            }
        } else {
            Log.e("state_", "2");

            showSettingAlert();

        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                moveMap(latitude, longitude);
                setAddress(latitude, longitude);
            }
        });


        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                mMap.clear();
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
                moveMap(latitude, longitude);
                setAddress(latitude, longitude);
            }

            @Override
            public void onError(Status status) {

            }
        });

    }


    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission();
            return;
        }
        Log.e("state_", "5");

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener((Activity) MapsActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            longitude = location.getLongitude();
                            latitude = location.getLatitude();
                            moveMap(latitude, longitude);

                        }
                    }

                });
    }

    private void moveMap(final double latitude, final double longitude) {
        setAddress(latitude, longitude);
        Log.e("latitude", latitude + "");
        Log.e("longitude", longitude + "");

        if (MyMarker != null) {
            MyMarker.remove();
        }
        LatLng latLng = new LatLng(latitude, longitude);
        MyMarker = mMap.addMarker(new MarkerOptions().position(latLng).draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.my_location)).title("My Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude, latLng.longitude), MapZoom));


        mMap.getUiSettings().setZoomControlsEnabled(false);

    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    public void showSettingAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MapsActivity.this);
        alertDialog.setTitle("GPS setting!");
        alertDialog.setMessage("GPS is not enabled, Do you want to go to settings menu? ");
        alertDialog.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                MapsActivity.this.startActivity(intent);
                finish();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // if from activity
                ActivityCompat.finishAffinity((Activity) MapsActivity.this
                );
            }
        });


        alertDialog.show();
    }

    private void setAddress(double latitude, double longitude) {
        try {
            Geocoder geo = new Geocoder(MapsActivity.this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (addresses.isEmpty()) {
                address.setText("Waiting for Location");
            } else {
                if (addresses.size() > 0) {
                    address.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }
    }


    private void AddAddress(final String lat, final String lng, final String name) {
        frameLoading.setVisibility(View.VISIBLE);

        new UserAPI().AddAddress(lat, lng,name, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject responseObject = (ResponseObject) result;
                if (responseObject.isStatus()) {
                    finish();
                } else {
                    frameLoading.setVisibility(View.GONE);
                    Constants.showDialog(MapsActivity.this, responseObject.getMessage());
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(MapsActivity.this, responseError.getMessage());

                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(MapsActivity.this, message);
            }
        });
    }


}
