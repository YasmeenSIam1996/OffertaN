package user.offerta.com.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import user.offerta.com.API.ResponseError;
import user.offerta.com.API.ResponseObject;
import user.offerta.com.API.ResponseSign;
import user.offerta.com.API.UserAPI;
import user.offerta.com.App.ApplicationController;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;

public class SignInActivity extends AppCompatActivity {
    private EditText etMobileNo, etPassword;
    private TextView tvSkip, tvForget, tvNewUser;
    private Button btnSign;
    private ImageButton btnImgGoogle, btnImgFB;
    private CallbackManager callbackManager;
    private LoginButton login_button;
    private FrameLayout frameLoading;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        intiFacebookGoogle();
        setContentView(R.layout.activity_sign_in);
        intiViews();
        setAcrionsViews();


        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("user.offerta.com", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

    }

    private void intiFacebookGoogle() {
        callbackManager = CallbackManager.Factory.create();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }


    private void setAcrionsViews() {
        btnImgFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_button.performClick();

            }
        });
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Mobile = etMobileNo.getText().toString().trim();
                String Email = etPassword.getText().toString().trim();
                if (Mobile.equals("")) {
                    Constants.showDialog(SignInActivity.this, getResources().getString(R.string.MobileNull));
                } else if (Email.equals("")) {
                    Constants.showDialog(SignInActivity.this, getResources().getString(R.string.PassNull));
                } else {
                    hideKeyboard(SignInActivity.this);
                    LogIn(Mobile, Email);
                }
            }
        });
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                String token = loginResult.getAccessToken().getToken();
                Log.e("token", "token: " + token);

                //---------------------------------------

                final GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e("namefbLogin: ", object.toString());

                                String name = "";
                                try {
                                    name = object.getString("name");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                social_login("", name, "");


                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                Toast.makeText(SignInActivity.this, "fb login", Toast.LENGTH_SHORT).show();
                Log.e("fbLogin: ", "onCancel: cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(SignInActivity.this, "fb login", Toast.LENGTH_SHORT).show();
                Log.e("fbLogin: ", "onError: " + error.toString());
            }
        });
        btnImgGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 5);
            }
        });
        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go(SignUpActivity.class);

            }
        });
        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go(ResetPassword.class);

            }
        });
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go(HomeActivity.class);
                finish();
            }
        });
    }

    private void intiViews() {
        etMobileNo = findViewById(R.id.etMobileNo);
        etPassword = findViewById(R.id.etPassword);
        tvSkip = findViewById(R.id.tvSkip);
        tvForget = findViewById(R.id.tvForget);
        tvNewUser = findViewById(R.id.tvNewUser);
        btnSign = findViewById(R.id.btnSign);
        login_button = findViewById(R.id.login_button);

        login_button.setReadPermissions("public_profile");

        btnImgGoogle = findViewById(R.id.btnImgGoogle);
        btnImgFB = findViewById(R.id.btnImgFB);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        frameLoading = findViewById(R.id.frameLoading);
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void go(Class activity) {
        startActivity(new Intent(SignInActivity.this, activity));
    }


    private void LogIn(String phone, String pass) {
        frameLoading.setVisibility(View.VISIBLE);
        new UserAPI().Login(phone, pass, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseSign responseSign = (ResponseSign) result;
                if (responseSign.isStatus()) {
                    ApplicationController.getInstance().loginUser(responseSign.getUser());
                    setFcmToken(FirebaseInstanceId.getInstance().getToken());
                } else {
                    Constants.showDialog(SignInActivity.this, ((ResponseSign) result).getMessage());
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(SignInActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(SignInActivity.this, message);
            }
        });
    }


    private void setFcmToken(String fcm_token) {
        new UserAPI().setFcmToken(fcm_token, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject responseSign = (ResponseObject) result;
                if (responseSign.isStatus()) {
                    go(HomeActivity.class);
                    finish();
                } else {
                    Constants.showDialog(SignInActivity.this, ((ResponseObject) result).getMessage());
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(SignInActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(SignInActivity.this, message);
            }
        });
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            social_login("", account.getDisplayName(), account.getEmail());

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("signInResult", "signInResult:failed code=" + e.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 5) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        }
    }

    private void social_login(String phone, String name, String email) {
        frameLoading.setVisibility(View.VISIBLE);
        new UserAPI().social_login(email, phone, name, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseSign responseSign = (ResponseSign) result;
                if (responseSign.isStatus()) {
                    setFcmToken(FirebaseInstanceId.getInstance().getToken());
                    ApplicationController.getInstance().loginUser(responseSign.getUser());
                } else {
                    Constants.showDialog(SignInActivity.this, ((ResponseSign) result).getMessage());
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(SignInActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(SignInActivity.this, message);
            }
        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
