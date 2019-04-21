package user.offerta.com.API;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


import java.util.HashMap;
import java.util.Map;

import user.offerta.com.App.ApplicationController;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.Module.MainCategory;
import user.offerta.com.Utils.Constants;


public class UserAPI {

    public void Register(final String email, final String password, final String mobile, final String name, final UniversalCallBack callBack) {
        String url = Constants.REGISTER;
        Log.d("Register: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Register: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseSign responseObject = gson.fromJson(response.toString(), ResponseSign.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                params.put("c_password", password);
                params.put("name", name);
                params.put("phone", mobile);
                params.put("lang", ApplicationController.langNum + "");

                return params;
            }


        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void social_login(final String email, final String mobile, final String name, final UniversalCallBack callBack) {
        String url = Constants.SOCIAL_LOGIN;
        Log.d("social_login: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("social_login: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseSign responseObject = gson.fromJson(response.toString(), ResponseSign.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("name", name);
                params.put("phone", mobile);
                return params;
            }


        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void Login(final String phone, final String Password, final UniversalCallBack callBack) {
        String url = Constants.LOGIN;
        Log.d("Login: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Login: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseSign responseObject = gson.fromJson(response.toString(), ResponseSign.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                params.put("phone", phone);
                params.put("password", Password);

                return params;
            }

        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void MainCategories(final UniversalCallBack callBack) {
        String url = Constants.MAIN_CATEGORIES;
        Log.d("Categories: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Categories: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseMainCategory responseObject = gson.fromJson(response.toString(), ResponseMainCategory.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");


                return params;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void LastProduct(final UniversalCallBack callBack) {
        String url = Constants.LAST_PRODUCT;
        Log.d("Categories: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Categories: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseLastProducts responseObject = gson.fromJson(response.toString(), ResponseLastProducts.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");


                return params;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void Advertisements(final UniversalCallBack callBack) {
        String url = Constants.ADVERTISEMENT;
        Log.d("Categories: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Categories: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseAdvertisement responseObject = gson.fromJson(response.toString(), ResponseAdvertisement.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");


                return params;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void ProductDetails(final String product_id, final UniversalCallBack callBack) {
        String url = Constants.PRODUCT_DETAILS;
        Log.d("Categories: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Categories: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseProductDetails responseObject = gson.fromJson(response.toString(), ResponseProductDetails.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));

                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                params.put("product_id", product_id);


                return params;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void AddToCart(final String product_id, final String quantity, final String color_id, final String size_id, final UniversalCallBack callBack) {
        String url = Constants.ADD_TO_CART;
        Log.d("AddToCart: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("AddToCart: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseAddToCart responseObject = gson.fromJson(response.toString(), ResponseAddToCart.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                params.put("product_id", product_id);
                params.put("quantity", quantity);
                params.put("color_id", color_id);
                params.put("size_id", size_id);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));

                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void GetCartList(final UniversalCallBack callBack) {
        String url = Constants.CART_LIST;
        Log.d("CART_LIST: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CART_LIST: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseMainCart responseObject = gson.fromJson(response.toString(), ResponseMainCart.class);

                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));

                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void GetCountCartList(final UniversalCallBack callBack) {
        String url = Constants.CART_CONT;
        Log.d("CART_LIST: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CART_LIST: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseAddToCart responseObject = gson.fromJson(response.toString(), ResponseAddToCart.class);

                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));

                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void CategoryContent(final String category_id, final String page, final UniversalCallBack callBack) {
        String url = Constants.CATEGORY_CONTENT;
        Log.d("CategoryContent: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CategoryContent: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseCategoryContent responseObject = gson.fromJson(response.toString(), ResponseCategoryContent.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                params.put("category_id", category_id);
                params.put("page", page);
                return params;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void StaredProduct(final String category_id, final String page, final UniversalCallBack callBack) {
        String url = Constants.STARED_PRODUCT;
        Log.d("CategoryContent: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CategoryContent: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseCategoryContent responseObject = gson.fromJson(response.toString(), ResponseCategoryContent.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                params.put("category_id", category_id);
                params.put("page", page);
                return params;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void DeleteProduct(final String product_id, final UniversalCallBack callBack) {
        String url = Constants.DELETE_FROM_CART;
        Log.d("DELETE_FROM_CART: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("DELETE_FROM_CART: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                params.put("product_id", product_id);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));

                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void UpdateQuantity(final String product_id, final String quantity, final UniversalCallBack callBack) {
        String url = Constants.UPDATE_QUANTITY;
        Log.d("UPDATE_QUANTITY: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("UPDATE_QUANTITY: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                params.put("product_id", product_id);
                params.put("quantity", quantity);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));

                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void AddAddress(final String lat, final String lng, final String name, final UniversalCallBack callBack) {
        String url = Constants.ADD_ADDRESS;
        Log.d("ADD_ADDRESS: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ADD_ADDRESS: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                params.put("lat", lat);
                params.put("lng", lng);
                params.put("name", name);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));

                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void GetAddress(final UniversalCallBack callBack) {
        String url = Constants.GET_ADDRESS;
        Log.d("GET_ADDRESS: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("GET_ADDRESS: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseLocation responseObject = gson.fromJson(response.toString(), ResponseLocation.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));

                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void deleteAddress(final String address_id, int position, final UniversalCallBack callBack) {
        String url = Constants.DELETE_ADDRESS;
        Log.d("DELETE_ADDRESS: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("DELETE_ADDRESS: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));

                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                params.put("address_id", address_id);

                return params;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void MackOrder(final String lat, final String lng, final String payment, final String comment, final UniversalCallBack callBack) {
        String url = Constants.MACK_ORDER;
        Log.d("MACK_ORDER: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("MACK_ORDER: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));

                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                params.put("lat", lat);
                params.put("lng", lng);
                params.put("comment", comment);
                params.put("payment", payment);
                params.put("phone", ApplicationController.getInstance().getUser().getPhone());

                return params;
            }

        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void getPastOrders(final UniversalCallBack callBack) {
        String url = Constants.PAST_ORDERS;
        Log.d("PAST_ORDERS: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("PAST_ORDERS: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponsePastOrders responseObject = gson.fromJson(response.toString(), ResponsePastOrders.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));

                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                return params;
            }

        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void getOrderDetails(final String order_id, final UniversalCallBack callBack) {
        String url = Constants.ORDER_DETAILS;
        Log.d("ORDER_Details: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ORDER_Details: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseOrderDetails responseObject = gson.fromJson(response.toString(), ResponseOrderDetails.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));

                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                params.put("order_id", order_id);
                return params;
            }

        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void updateProfile(final String name, final String email, final String phone, final UniversalCallBack callBack) {
        String url = Constants.UPDATE_PROFILE;
        Log.d("UPDATE_PROFILE: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("UPDATE_PROFILE: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseUser responseObject = gson.fromJson(response.toString(), ResponseUser.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));

                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                params.put("name", name);
                params.put("email", email);
                params.put("phone", phone);
                return params;
            }

        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void updatePassword(final String old_password, final String new_password, final String c_new_password, final UniversalCallBack callBack) {
        String url = Constants.UPDATE_PASS;
        Log.d("UPDATE_PROFILE: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("UPDATE_PROFILE: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));

                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                params.put("old_password", old_password);
                params.put("new_password", new_password);
                params.put("c_new_password", c_new_password);
                return params;
            }

        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void getFavorite(final UniversalCallBack callBack) {
        String url = Constants.GET_FAVORITE;
        Log.d("GET_FAVORITE: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("GET_FAVORITE: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseLastProducts responseObject = gson.fromJson(response.toString(), ResponseLastProducts.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));

                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");


                return params;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void getFilterData(final UniversalCallBack callBack) {
        String url = Constants.FILTER_DATA;
        Log.d("FILTER_DATA: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("FILTER_DATA: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseFilter responseObject = gson.fromJson(response.toString(), ResponseFilter.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        });
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void getFilterProductResult(final String category_id, final String page, final String colors, final String price_to, final String price_form, final UniversalCallBack callBack) {
        String url = Constants.FILTER_PRODUCT;
        Log.d("FILTER_PRODUCT: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("FILTER_PRODUCT: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseCategoryContent responseObject = gson.fromJson(response.toString(), ResponseCategoryContent.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                params.put("category", category_id);
                params.put("page", page);
                params.put("colors", colors);
                params.put("price_to", price_to);
                params.put("price_form", price_form);

                return params;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void addFavoriteProduct(final String product_id, final UniversalCallBack callBack) {
        String url = Constants.ADD_FAVORITE;
        Log.d("ADD_FAVORITE: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ADD_FAVORITE: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                params.put("product_id", product_id);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void deleteFavoriteProduct(final String product_id, final UniversalCallBack callBack) {
        String url = Constants.REMOVE_FAVORITE;
        Log.d("REMOVE_FAVORITE: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("REMOVE_FAVORITE: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                params.put("product_id", product_id);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));

                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void setFcmToken(final String fcm_token, final UniversalCallBack callBack) {
        String url = Constants.SET_FCM_TOKEN;
        Log.d("SET_FCM_TOKEN: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("SET_FCM_TOKEN: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fcm_token", fcm_token);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));

                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void logout(final UniversalCallBack callBack) {
        String url = Constants.LOGOUT;
        Log.d("SET_FCM_TOKEN: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("SET_FCM_TOKEN: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken",""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken",""));

                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void setSearch(final String searchText, final int page, final UniversalCallBack callBack) {
        String url = Constants.SEARCH_PRODUCT;
        Log.d("SEARCH_PRODUCT: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("SEARCH_PRODUCT: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseSearchProducts responseObject = gson.fromJson(response.toString(), ResponseSearchProducts.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", searchText);
                params.put("lang", ApplicationController.langNum + "");
                params.put("page", page + "");

                return params;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    private void showMessage(VolleyError error, UniversalCallBack callBack) {
        String message = null;
        Log.d("onErrorResponse", error.toString() + "");
        String json = null;
        Log.d("error.getMessage()", error.getMessage() + "");
        if (error instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
            callBack.OnError(message);
        } else if (error instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
            callBack.OnError(message);
        } else if (error instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
            callBack.OnError(message);
        } else if (error instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
            callBack.OnError(message);
        } else if (error instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
            callBack.OnError(message);
        } else if (error instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
            callBack.OnError(message);
        } else {
            try {
                Gson gson = new Gson();
                ResponseError ErrorMsg = gson.fromJson(error.getMessage(), ResponseError.class);
                callBack.onFailure(ErrorMsg);
            } catch (JsonSyntaxException e) {
                callBack.OnError("Server Connection error try again later");
            }
        }
    }

}
