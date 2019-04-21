package user.offerta.com.Utils;

import android.app.Activity;

import com.tapadoo.alerter.Alerter;


import java.util.Locale;

import user.offerta.com.R;

/**
 * Created by Ahmed hani on 11/5/2017.
 */

public class Constants {

    public static final String FONTS_APP = "sf_pro_text_semibold.otf";

    public static final String BASE_URL = "http://134.209.0.107/api/";
    public static final String Image_URL = "http://134.209.0.107/public/uploads/";
    public static final String LOGIN = BASE_URL + "login";
    public static final String MAIN_CATEGORIES = BASE_URL + "getMainCategory";
    public static final String LAST_PRODUCT = BASE_URL + "lastProduct";
    public static final String ADVERTISEMENT = BASE_URL + "getAdvertisement";
    public static final String REGISTER = BASE_URL + "register";
    public static final String PRODUCT_DETAILS = BASE_URL + "getProductDetails";
    public static final String ADD_TO_CART = BASE_URL + "addProductToCart";
    public static final String CART_LIST = BASE_URL + "cart_list";
    public static final String CART_CONT = BASE_URL + "cartCount";
    public static final String CATEGORY_CONTENT = BASE_URL + "getCategoryContent";
    public static final String STARED_PRODUCT = BASE_URL + "staredProduct";
    public static final String DELETE_FROM_CART = BASE_URL + "deleteFromCart";
    public static final String UPDATE_QUANTITY = BASE_URL + "updateQuantity";
    public static final String ADD_ADDRESS = BASE_URL + "addAddress";
    public static final String GET_ADDRESS = BASE_URL + "getAddresses";
    public static final String MACK_ORDER = BASE_URL + "makeOrder";
    public static final String PAST_ORDERS = BASE_URL + "order_list";
    public static final String ORDER_DETAILS = BASE_URL + "order_details";
    public static final String UPDATE_PROFILE = BASE_URL + "updateProfile";
    public static final String UPDATE_PASS = BASE_URL + "changePassword";
    public static final String GET_FAVORITE = BASE_URL + "getFavorite";
    public static final String FILTER_DATA = BASE_URL + "filterData";
    public static final String FILTER_PRODUCT = BASE_URL + "filterProduct";
    public static final String ADD_FAVORITE = BASE_URL + "addFavorite";
    public static final String REMOVE_FAVORITE = BASE_URL + "deleteFavorite";
    public static final String DELETE_ADDRESS = BASE_URL + "deleteAddresses";
    public static final String SET_FCM_TOKEN = BASE_URL + "setFcmToken";
    public static final String LOGOUT = BASE_URL + "logout";
    public static final String SEARCH_PRODUCT = BASE_URL + "searchProduct";
    public static final String SOCIAL_LOGIN = BASE_URL + "social_login";

    public static String getLanguage() {
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("en")) {
            return "en";
        } else {
            return "ar";
        }
    }

    public static void showDialog(Activity context, String message) {
        Alerter.create(context)
                .setText(message)
                .hideIcon()
                .setBackgroundColorRes(R.color.colorPrimary)
                .show();
    }


}
