package user.offerta.com.App;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import user.offerta.com.API.VolleySingleton;
import user.offerta.com.Module.User;


public class ApplicationController extends Application {

    private static ApplicationController mInstance;
    private static Context context;
    private Locale myLocale;
    public static int langNum = 0;


    @Override

    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context = getApplicationContext();
        mInstance = this;
        VolleySingleton.getInstance();
        Realm.init(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.
                Builder().
                deleteRealmIfMigrationNeeded().
                build();
        Realm.setDefaultConfiguration(realmConfiguration);

        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String lang = prefs.getString("Language", "en");
        changeLang(lang);

    }


    public static synchronized ApplicationController getInstance() {
        if (mInstance == null)
            return mInstance = new ApplicationController();
        else
            return mInstance;
    }


    public static Context getAppContext() {
        return ApplicationController.context;
    }


    public User getUser() {
        SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
        User user = new User();
        user.setEmail(sharedPreferences.getString("UserEmail", ""));
        user.setId(sharedPreferences.getInt("UserId", 0));
        user.setIsVerified(sharedPreferences.getInt("UserIsVerified", 0));
        user.setPhone(sharedPreferences.getString("UserPhone", ""));
        user.setName(sharedPreferences.getString("UserName", ""));


        return user;
    }

    public void loginUser(final User user) {
        SharedPreferences.Editor editor = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE).edit();
        editor.putString("UserToken", user.getToken());
        editor.putString("UserEmail", user.getEmail());
        editor.putString("UserName", user.getName());
        editor.putString("UserPhone", user.getPhone());
        editor.putInt("UserId", user.getId());
        editor.putInt("UserIsVerified", user.getIsVerified());

        editor.commit();

//        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.copyToRealm(user);
//            }
//        });

    }

    public void deleteRealm() {


        SharedPreferences.Editor editor = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE).edit();
        editor.putString("UserToken", "");
        editor.putString("UserEmail","");
        editor.putString("UserName", "");
        editor.putString("UserPhone","");
        editor.putInt("UserId", 0);
        editor.putInt("UserIsVerified", 0);
        editor.commit();
    }


    public boolean IsUserLoggedIn() {
        if (ApplicationController.getInstance().getUser().getName().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }


    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "ar");
        changeLang(language);
    }

    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        if (lang.equals("ar")) {
            langNum = 1;
        } else {
            langNum = 0;
        }
    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

}
