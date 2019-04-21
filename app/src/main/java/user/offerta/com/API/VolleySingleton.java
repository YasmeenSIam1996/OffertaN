package user.offerta.com.API;

import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import user.offerta.com.App.ApplicationController;


public class VolleySingleton {

    public static final String TAG = VolleySingleton.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static VolleySingleton mInstance;
    private ImageLoader mImageLoader;


    private VolleySingleton(){
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
    }

    public static synchronized VolleySingleton getInstance() {
        if(mInstance==null){
            mInstance=new VolleySingleton();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(ApplicationController.getInstance().getApplicationContext());


        }
        return mRequestQueue;
    }



    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setShouldCache(false);
        req.setRetryPolicy(new DefaultRetryPolicy(
                30000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        //req.setTag(TAG);
        addToRequestQueue(req,"");
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}