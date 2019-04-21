package user.offerta.com.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import user.offerta.com.R;

public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_reset_password);
    }
}
