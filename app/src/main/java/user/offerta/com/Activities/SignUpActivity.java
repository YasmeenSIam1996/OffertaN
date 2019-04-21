package user.offerta.com.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import user.offerta.com.API.ResponseError;
import user.offerta.com.API.ResponseObject;
import user.offerta.com.API.ResponseSign;
import user.offerta.com.API.UserAPI;
import user.offerta.com.App.ApplicationController;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;

import static user.offerta.com.Activities.SignInActivity.hideKeyboard;

public class SignUpActivity extends AppCompatActivity {
    private EditText etMobileNo, etPassword, etName, etEmail;
    private TextView tvSkip, tvForget, tvNewUser;
    private Button btnSign;
    private FrameLayout frameLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_sign_up);
        intiViews();
        setAcrionsViews();
    }


    private void setAcrionsViews() {
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Mobile = etMobileNo.getText().toString().trim();
                String Password = etPassword.getText().toString().trim();
                String Name = etName.getText().toString().trim();
                String Email = etEmail.getText().toString().trim();
                if (Name.equals("")) {
                    Constants.showDialog(SignUpActivity.this, getResources().getString(R.string.NameNull));
                } else if (Email.equals("")) {
                    Constants.showDialog(SignUpActivity.this, getResources().getString(R.string.EmailNull));
                } else if (!isValidEmail(Email)) {
                    Constants.showDialog(SignUpActivity.this, getResources().getString(R.string.EmailValid));
                } else if (Mobile.equals("")) {
                    Constants.showDialog(SignUpActivity.this, getResources().getString(R.string.MobileNull));
                } else if (Password.equals("")) {
                    Constants.showDialog(SignUpActivity.this, getResources().getString(R.string.PassNull));
                } else {
                    hideKeyboard(SignUpActivity.this);

                    SignUp(Mobile, Password, Name, Email);
                }
            }
        });

        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go(SignInActivity.class);

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
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        frameLoading = findViewById(R.id.frameLoading);
    }

    private void go(Class activity) {
        startActivity(new Intent(SignUpActivity.this, activity));
    }


    private void SignUp(String phone, String pass, String name, String email) {
        frameLoading.setVisibility(View.VISIBLE);
        new UserAPI().Register(email, pass, phone, name, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseSign responseSign = (ResponseSign) result;
                if (responseSign.isStatus()) {
                    ApplicationController.getInstance().loginUser(responseSign.getUser());
                    setFcmToken(FirebaseInstanceId.getInstance().getToken());
                } else {
                    Constants.showDialog(SignUpActivity.this, ((ResponseSign) result).getMessage());
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(SignUpActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(SignUpActivity.this, message);
            }
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
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
                    Constants.showDialog(SignUpActivity.this, ((ResponseObject) result).getMessage());
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(SignUpActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(SignUpActivity.this, message);
            }
        });
    }

}
