package user.offerta.com.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;

import java.util.List;

import io.realm.Realm;
import user.offerta.com.API.ResponseAddToCart;
import user.offerta.com.API.ResponseError;
import user.offerta.com.API.ResponseObject;
import user.offerta.com.API.UserAPI;
import user.offerta.com.App.ApplicationController;
import user.offerta.com.Fragments.AcountFragment;
import user.offerta.com.Fragments.CatFragment;
import user.offerta.com.Fragments.FavoriteFragment;
import user.offerta.com.Fragments.HomeFragment;
import user.offerta.com.Fragments.PastOrdersFragment;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;
import user.offerta.com.Utils.CustomTypefaceSpan;

import static user.offerta.com.Utils.Constants.getLanguage;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView count, LogOut, name;
    private NavigationView Navigation_View;
    private Toolbar toolbar;
    private ImageView filter, search;
    private DrawerLayout drawer;
    private LottieAnimationView cartAnimation;
    private Dialog dialog;
    private FrameLayout frameLoading;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_home);
        check();
        intiViews();
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                AcountFragment(getIntent().getStringExtra("Data"));
            }
        });
        try {

            if (!getIntent().getStringExtra("Data").equals("")) {
                AcountFragment(getIntent().getStringExtra("Data"));
            } else {
                HomeFragment();
            }

        } catch (Exception e) {
            HomeFragment();
        }

        getCartListCount();
        cartAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
                finish();
            }
        });
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                showCustomDialog();

            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, FilterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });
        toolbar = findViewById(R.id.toolbar_);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                HomeActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);


        Menu m = Navigation_View.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), Constants.FONTS_APP);
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


    private void intiViews() {
        drawer = findViewById(R.id.drawer_layout);
        count = findViewById(R.id.num_product_cart);
        Navigation_View = findViewById(R.id.Navigation_View);
        Navigation_View.setNavigationItemSelectedListener(this);
        filter = findViewById(R.id.filter);
        search = findViewById(R.id.search);
        View headerLayout = Navigation_View.getHeaderView(0); // 0-index header
        LogOut = headerLayout.findViewById(R.id.LogOut);
        name = headerLayout.findViewById(R.id.name);
        frameLoading = findViewById(R.id.frameLoading);
        cartAnimation = findViewById(R.id.cartAnimation);
        if (!ApplicationController.getInstance().getUser().getName().trim().equals("")) {
            name.setText(getResources().getString(R.string.Welcome) + ApplicationController.getInstance().getUser().getName());
        } else {
            name.setVisibility(View.GONE);
            LogOut.setText(getResources().getString(R.string.logIn));
        }

        if (getIntent().getIntExtra("PaymentMethodActivity", 0) == 1) {
            Constants.showDialog(HomeActivity.this, getResources().getString(R.string.OrderCompleted));
        }
    }

    public void MyOrdersFragment() {
        FavoriteFragment favoriteFragment = FavoriteFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, favoriteFragment).addToBackStack("favoriteFragment").commit();

    }

    public void CatFragment() {
        CatFragment catFragment = CatFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, catFragment).addToBackStack("catFragment").commit();

    }

    public void HomeFragment() {
        HomeFragment homeFragment = HomeFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, homeFragment).commit();

    }

    public void AcountFragment(String Data) {
        AcountFragment homeFragment = AcountFragment.newInstance(Data);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, homeFragment).addToBackStack("homeFragment").commit();

    }


    public void PastOrdersFragment() {
        PastOrdersFragment pastOrdersFragment = PastOrdersFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, pastOrdersFragment).addToBackStack("pastOrdersFragment").commit();

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
                    Constants.showDialog(HomeActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(HomeActivity.this, message);
            }
        });
    }


    private void logout() {
        dialog.dismiss();
        frameLoading.setVisibility(View.VISIBLE);
        new UserAPI().logout(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject result1 = (ResponseObject) result;
                if (result1.isStatus()) {
                    Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.deleteAll();
                            ApplicationController.getInstance().deleteRealm();
                            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                            finish();

                        }
                    });
                } else {
                    Constants.showDialog(HomeActivity.this, result1.getMessage());
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(HomeActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(HomeActivity.this, message);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.home) {
            HomeFragment();
        } else if (id == R.id.cart) {
            startActivity(new Intent(getApplicationContext(), CartActivity.class));
            finish();
        } else if (id == R.id.my_order) {
            PastOrdersFragment();
        } else if (id == R.id.language) {
            ChangeLanguage();
        } else if (id == R.id.cat) {
            CatFragment();
        } else if (id == R.id.FAVORITE) {
            MyOrdersFragment();
        } else if (id == R.id.about) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void showCustomDialog() {
        dialog = new Dialog(HomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);

        Button yes = dialog.findViewById(R.id.yes);
        Button no = dialog.findViewById(R.id.no);
        ImageView icon = dialog.findViewById(R.id.icon);
        icon.setVisibility(View.GONE);
        TextView textView = dialog.findViewById(R.id.text);
        if (ApplicationController.getInstance().IsUserLoggedIn()) {
            textView.setText(getResources().getString(R.string.logOut));
        } else {
            textView.setText(getResources().getString(R.string.LogIn));
        }
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
                    logout();
                    disconnectFromFacebook();

                } else {
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                    finish();
                }

            }
        });

        dialog.show();

    }


    public void ChangeLanguage() {
        String languageToLoad = "ar"; // your language
        if (getLanguage().equals("ar")) {
            languageToLoad = "en";
            ApplicationController.getInstance().changeLang(languageToLoad);
            ApplicationController.getInstance().loadLocale();
        } else {
            languageToLoad = "ar";
            ApplicationController.getInstance().changeLang(languageToLoad);
            ApplicationController.getInstance().loadLocale();
        }
        Intent refresh = new Intent(this, HomeActivity.class);
        startActivity(refresh);
        finish();

    }


    @Override
    public void onBackPressed() {

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
//        for (int x = 0; x < fragments.size(); x++) {
        Log.e("fragments_", fragments.size() + "");
//        }


        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            HomeFragment();

            getSupportFragmentManager().popBackStackImmediate();
            doubleBackToExitPressedOnce = false;
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getResources().getText(R.string.clickExit), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }


    }


    private void check() {
        int locationMode = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.e("state_", "1");
            try {
                locationMode = Settings.Secure.getInt(HomeActivity.this.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            boolean b = (locationMode != Settings.Secure.LOCATION_MODE_OFF && locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY); //check location mode
            if (!b) {
                Log.e("state_", "4");

                Toast.makeText(HomeActivity.this, getResources().getString(R.string.turn_on), Toast.LENGTH_SHORT).show();
                showSettingAlert();
            }
        } else {
            Log.e("state_", "2");

            showSettingAlert();

        }

    }

    public void showSettingAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
        alertDialog.setTitle("GPS setting!");
        alertDialog.setMessage("GPS is not enabled, Do you want to go to settings menu? ");
        alertDialog.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                HomeActivity.this.startActivity(intent);
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
                ActivityCompat.finishAffinity((Activity) HomeActivity.this
                );
            }
        });


        alertDialog.show();
    }


    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }

}
