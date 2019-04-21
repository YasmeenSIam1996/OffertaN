package user.offerta.com.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.pixelcan.inkpageindicator.InkPageIndicator;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import user.offerta.com.API.ResponseAddToCart;
import user.offerta.com.API.ResponseError;
import user.offerta.com.API.ResponseFilter;
import user.offerta.com.API.ResponseObject;
import user.offerta.com.API.ResponseProductDetails;
import user.offerta.com.API.UserAPI;
import user.offerta.com.Adspters.ColorAdapter;
import user.offerta.com.Adspters.ListPopupWindowAdapter;
import user.offerta.com.Adspters.ViewPagerStringImage;
import user.offerta.com.Interfaces.ObjectClickListener;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.Module.ColorPicker;
import user.offerta.com.Module.ProductSize;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;
import user.offerta.com.Utils.ZoomOutPageTransformer;


public class DetailsActivity extends AppCompatActivity implements DiscreteScrollView.OnItemChangedListener {
    private DiscreteScrollView DepartmentPicker;
    private ColorAdapter colorAdapter;
    private FrameLayout frameLoading;
    private ViewPager Pager_product_image;
    private ViewPagerStringImage viewPagerAdapterImage;
    private List<String> product_images;
    private InkPageIndicator dots_indicator;
    private SimpleRatingBar points;
    private TextView num, price, details, product_name, product_details, count, size, num_product_cart;
    private RelativeLayout picker;
    private List<ProductSize> sampleData;
    private ImageView plus, minus;
    private RelativeLayout AddToCart, LayoutPicker;
    private int SizeId = 0, ColorId = 0;
    private LottieAnimationView cartAnimation;
    private String ProductId;
    private ImageView filter, search;
    private FrameLayout likeFrame;
    private LikeButton Like_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_details);
        intiViews();
        getCartListCount();
        ProductId = getIntent().getStringExtra("ProductId");
        getDetails(ProductId);
//        getDetails("9");
        setUpAdapterAdvertisement();
    }


    private void intiViews() {
        frameLoading = findViewById(R.id.frameLoading);
        Pager_product_image = findViewById(R.id.Pager_product_image);
        product_images = new Vector<>();
        dots_indicator = findViewById(R.id.dots_indicator);
        points = findViewById(R.id.points);
        num = findViewById(R.id.num);
        product_details = findViewById(R.id.product_details);
        product_name = findViewById(R.id.product_name);
        price = findViewById(R.id.price);
        details = findViewById(R.id.details);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        picker = findViewById(R.id.picker);
        count = findViewById(R.id.countText);
        size = findViewById(R.id.size);
        AddToCart = findViewById(R.id.AddToCart);
        LayoutPicker = findViewById(R.id.picker);
        cartAnimation = findViewById(R.id.cartAnimation);
        num_product_cart = findViewById(R.id.num_product_cart);
        likeFrame = findViewById(R.id.likeFrame);
        sampleData = new Vector<>();
        filter = findViewById(R.id.filter);
        search = findViewById(R.id.search);
        DepartmentPicker = findViewById(R.id.ColorPicker);
        Like_Button = findViewById(R.id.Like_Button);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, FilterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListPopupWindow(view);
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.valueOf(count.getText().toString()) + 1;
                count.setText(num + "");
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.valueOf(count.getText().toString());
                if (num > 1) {
                    count.setText((num - 1) + "");
                } else {
                    Constants.showDialog(DetailsActivity.this, "Quantity equal one");
                }
            }
        });

        points.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DepartmentPicker.getVisibility() == View.VISIBLE && SizeId == 0) {
                    Constants.showDialog(DetailsActivity.this, getResources().getString(R.string.ChoiceSize));
                } else if (DepartmentPicker.getVisibility() == View.VISIBLE && ColorId == 0) {
                    Constants.showDialog(DetailsActivity.this, getResources().getString(R.string.ChoiceColor));
                } else {
                    addToCart(ProductId, count.getText().toString(), ColorId + "", SizeId + "");
                }
            }
        });
        cartAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this, CartActivity.class));
                finish();
            }
        });


        Like_Button.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addFavoriteProduct(ProductId);
                likeFrame.setBackground(getResources().getDrawable(R.drawable.like_back_shape));
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                removeFavoriteProduct(ProductId);
                likeFrame.setBackground(getResources().getDrawable(R.drawable.un_like_back_shape));

            }
        });

    }

    private void setUpAdapterAdvertisement() {
        viewPagerAdapterImage = new ViewPagerStringImage(DetailsActivity.this, product_images);
        Pager_product_image.setAdapter(viewPagerAdapterImage);
        Pager_product_image.setPageTransformer(true, new ZoomOutPageTransformer());
        dots_indicator.setViewPager(Pager_product_image);
    }

    private void intiTap(final List<ColorPicker> colorPickers) {
        colorAdapter = new ColorAdapter(colorPickers);
        DepartmentPicker.setSlideOnFling(true);
        DepartmentPicker.setAdapter(colorAdapter);
        DepartmentPicker.addOnItemChangedListener(DetailsActivity.this);
        DepartmentPicker.scrollToPosition(1);
        DepartmentPicker.setItemTransitionTimeMillis(144);
        DepartmentPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        colorAdapter.setOnItemClickListener(new ObjectClickListener() {
            @Override
            public void onItemClickListener(int position, View view, ColorPicker colorPicker) {
                ColorId = colorPickers.get(0).getColor_id();

            }
        });
        try {
            ColorId = colorPickers.get(0).getColor_id();
        } catch (Exception e) {

        }

    }

    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

    }

    private void getDetails(String ProductId) {
        frameLoading.setVisibility(View.VISIBLE);
        new UserAPI().ProductDetails(ProductId, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseProductDetails productDetails = (ResponseProductDetails) result;
                if (productDetails.isStatus()) {
                    product_name.setText(productDetails.getDataProduct().getProduct().getName());
                    product_images.addAll(productDetails.getDataProduct().getProduct().getImage());
                    setupAutoPager(productDetails.getDataProduct().getProduct().getImage().size());
                    viewPagerAdapterImage.notifyDataSetChanged();
                    if (productDetails.getDataProduct().getProduct().getColorPicker().size() != 0) {
                        intiTap(productDetails.getDataProduct().getProduct().getColorPicker());

                    } else {
                        DepartmentPicker.setVisibility(View.GONE);
                        ColorId = 0;
                    }
                    product_details.setText(productDetails.getDataProduct().getProduct().getDescription());
                    points.setRating(productDetails.getDataProduct().getProduct().getRate());
                    num.setText("(" + productDetails.getDataProduct().getProduct().getCount_rate() + ")");
                    price.setText(productDetails.getDataProduct().getProduct().getPrice() + "");
                    details.setText(productDetails.getDataProduct().getProduct().getDescription());
                    if (productDetails.getDataProduct().getProduct().getProductSizes().size() != 0) {
                        sampleData.addAll(productDetails.getDataProduct().getProduct().getProductSizes());
                    } else {
                        LayoutPicker.setVisibility(View.GONE);
                        SizeId = 0;
                    }
                    Log.e("getIs_favorite", ((ResponseProductDetails) result).getDataProduct().getProduct().getIs_favorite() + "");
                    if (((ResponseProductDetails) result).getDataProduct().getProduct().getIs_favorite() == 0) {

                        likeFrame.setBackground(getResources().getDrawable(R.drawable.un_like_back_shape));
                        Like_Button.setLiked(false);
                    } else {
                        likeFrame.setBackground(getResources().getDrawable(R.drawable.like_back_shape));
                        Like_Button.setLiked(true);
                    }


                } else {
                    Constants.showDialog(DetailsActivity.this, ((ResponseProductDetails) result).getMessage());
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(DetailsActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(DetailsActivity.this, message);
            }
        });
    }

    private void showListPopupWindow(View anchorView) {
        final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setWidth(600);
        listPopupWindow.setAnchorView(anchorView);
        ListPopupWindowAdapter listPopupWindowAdapter = new ListPopupWindowAdapter(DetailsActivity.this, sampleData, new ListPopupWindowAdapter.OnClickDeleteButtonListener() {
            @Override
            public void onClickDeleteButton(int position) {
                ProductSize productSize = sampleData.get(position);
                size.setText(productSize.getName());
                SizeId = productSize.getSize_id();
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.setAdapter(listPopupWindowAdapter);
        listPopupWindow.show();
    }


    private void addToCart(String ProductId, String Quantity, String Color_Id, String Size_Id) {
        frameLoading.setVisibility(View.VISIBLE);
        new UserAPI().AddToCart(ProductId, Quantity, Color_Id, Size_Id, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                final ResponseAddToCart result1 = (ResponseAddToCart) result;
                if (result1.isStatus()) {
                    if (result1.getMessage().trim().equals("The product already exists in the cart")) {
                        Constants.showDialog(DetailsActivity.this, result1.getMessage());
                        num_product_cart.setText(result1.getCarts().getCount() + "");
                    } else {
                        Toast.makeText(getApplicationContext(), result1.getMessage(), Toast.LENGTH_LONG).show();
                        num_product_cart.setVisibility(View.GONE);
                        cartAnimation.playAnimation();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                num_product_cart.setText(result1.getCarts().getCount() + "");
                                num_product_cart.setVisibility(View.VISIBLE);
                            }
                        }, cartAnimation.getDuration() - 1000);
                    }

                } else {
                    Constants.showDialog(DetailsActivity.this, ((ResponseAddToCart) result).getMessage());
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(DetailsActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                ColorId = 0;
                SizeId = 0;
                frameLoading.setVisibility(View.GONE);

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(DetailsActivity.this, message);
            }
        });
    }

    private void getCartListCount() {
        new UserAPI().GetCountCartList(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseAddToCart result1 = (ResponseAddToCart) result;
                if (result1.isStatus()) {
                    num_product_cart.setText(result1.getCarts().getCount() + "");
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(DetailsActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(DetailsActivity.this, message);
            }
        });
    }


    private void addFavoriteProduct(String ProductId) {

        new UserAPI().addFavoriteProduct(ProductId, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject responseObject = (ResponseObject) result;
                if (responseObject.isStatus()) {
                    Constants.showDialog(DetailsActivity.this, responseObject.getMessage());
                } else {
                    Constants.showDialog(DetailsActivity.this, responseObject.getMessage());
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(DetailsActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(DetailsActivity.this, message);
            }
        });
    }

    private void removeFavoriteProduct(String ProductId) {

        new UserAPI().deleteFavoriteProduct(ProductId, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject responseObject = (ResponseObject) result;
                if (responseObject.isStatus()) {
                    Constants.showDialog(DetailsActivity.this, responseObject.getMessage());
                } else {
                    Constants.showDialog(DetailsActivity.this, responseObject.getMessage());
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(DetailsActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(DetailsActivity.this, message);
            }
        });
    }


    private int currentPage = 0;
    private Timer timer;

    private void setupAutoPager(final int imagesSize) {
        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {

                Pager_product_image.setCurrentItem(currentPage, true);
                if (currentPage == imagesSize) {
                    currentPage = 0;
                } else {
                    ++currentPage;
                }
            }
        };


        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 500, 2000);
    }

}
