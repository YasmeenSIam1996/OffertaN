package user.offerta.com.Activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import user.offerta.com.API.ResponseCategoryContent;
import user.offerta.com.API.ResponseError;
import user.offerta.com.API.ResponseFilter;
import user.offerta.com.API.ResponseMainCategory;
import user.offerta.com.API.UserAPI;
import user.offerta.com.Adspters.ColorAdapter;
import user.offerta.com.Adspters.ListCatFilterPopupAdapter;
import user.offerta.com.Adspters.ListPopupWindowAdapter;
import user.offerta.com.Adspters.ListSizeFilterPopupAdapter;
import user.offerta.com.Adspters.ListSubCatFilterPopupAdapter;
import user.offerta.com.Interfaces.ObjectClickListener;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.Module.ColorPicker;
import user.offerta.com.Module.MainCategory;
import user.offerta.com.Module.Product;
import user.offerta.com.Module.SizeFilter;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;

public class FilterActivity extends AppCompatActivity implements DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder> {
    private EditText from, to;
    private RelativeLayout pickerCat, pickerSubCat, pickerSiza;
    private TextView catSubVal, catVal, size, tittle;
    private FrameLayout frameLoading;
    private List<SizeFilter> sampleData;
    private int SizeId = 0, ColorId = 0, CatId = 0, SubCatId = 0;
    private DiscreteScrollView ColorPicker;
    private ColorAdapter colorAdapter;
    private List<MainCategory> mainCategoryList;
    private List<MainCategory> categoryContentList;
    private Button apply;
    private List<ColorPicker> colorPickers;
    private boolean isFirst = false;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_filter);

        intiViews();
        getFilterData();
        getMainCategories();
        setUpActions();

    }

    private void setUpActions() {
        pickerCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCatListPopupWindow(view);

            }
        });
        pickerSiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSizeListPopupWindow(view);
            }
        });
        pickerSubCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CatId == 0) {
                    Constants.showDialog(FilterActivity.this, getResources().getString(R.string.ChoiceCat));
                } else {
                    showSubCatListPopupWindow(view);
                }
            }
        });
    }

    private void intiViews() {
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        pickerCat = findViewById(R.id.pickerCat);
        pickerSubCat = findViewById(R.id.pickerSubCat);
        catSubVal = findViewById(R.id.catSubVal);
        catVal = findViewById(R.id.catVal);
        pickerSiza = findViewById(R.id.pickerSiza);
        size = findViewById(R.id.size);
        frameLoading = findViewById(R.id.frameLoading);
        sampleData = new Vector<>();
        mainCategoryList = new Vector<>();
        categoryContentList = new Vector<>();
        colorPickers = new Vector<>();
        ColorPicker = findViewById(R.id.ColorPicker);
        apply = findViewById(R.id.apply);
        tittle = findViewById(R.id.tittle);
        back = findViewById(R.id.back);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from_ = from.getText().toString().trim();
                String to_ = to.getText().toString().trim();
                if (SubCatId != 0) {
                    getFilterDataResult(SubCatId + "", "1", ColorId + "", to_, from_);
                } else {

                    getFilterDataResult(CatId + "", "1", ColorId + "", to_, from_);

                }

            }
        });

        tittle.setText(getResources().getText(R.string.FILTER));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FilterActivity.this, HomeActivity.class));
                finish();
            }
        });
    }


    private void getFilterData() {

        new UserAPI().getFilterData(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseFilter responseObject = (ResponseFilter) result;
                if (responseObject.isStatus()) {
                    sampleData.addAll(responseObject.getMainCart().getSizes());
                    colorPickers = responseObject.getMainCart().getColors();
                    intiTap();
                } else {
                    Constants.showDialog(FilterActivity.this, responseObject.getMessage());
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(FilterActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(FilterActivity.this, message);
            }
        });
    }


    private void showSizeListPopupWindow(View anchorView) {
        final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setWidth(600);
        listPopupWindow.setAnchorView(anchorView);
        ListSizeFilterPopupAdapter listPopupWindowAdapter = new ListSizeFilterPopupAdapter(FilterActivity.this, sampleData, new ListPopupWindowAdapter.OnClickDeleteButtonListener() {
            @Override
            public void onClickDeleteButton(int position) {
                SizeFilter sizeFilter = sampleData.get(position);
                size.setText(sizeFilter.getName());
                SizeId = sizeFilter.getId();
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.setAdapter(listPopupWindowAdapter);

        listPopupWindow.show();
    }


    private void showCatListPopupWindow(View anchorView) {
        final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setWidth(1000);
        listPopupWindow.setAnchorView(anchorView);
        ListCatFilterPopupAdapter listPopupWindowAdapter = new ListCatFilterPopupAdapter(FilterActivity.this, mainCategoryList, new ListPopupWindowAdapter.OnClickDeleteButtonListener() {
            @Override
            public void onClickDeleteButton(int position) {
                SubCatId = 0;
                catSubVal.setText(getResources().getString(R.string.SubCategory));

                MainCategory mainCategory = mainCategoryList.get(position);
                catVal.setText(mainCategory.getName());
                CatId = mainCategory.getId();
                getCategoryContent(CatId + "", "1");
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.setAdapter(listPopupWindowAdapter);

        listPopupWindow.show();
    }


    private void showSubCatListPopupWindow(View anchorView) {
        final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setWidth(1000);
        listPopupWindow.setAnchorView(anchorView);
        ListSubCatFilterPopupAdapter listPopupWindowAdapter = new ListSubCatFilterPopupAdapter(FilterActivity.this, categoryContentList, new ListPopupWindowAdapter.OnClickDeleteButtonListener() {
            @Override
            public void onClickDeleteButton(int position) {
                MainCategory mainCategory = categoryContentList.get(position);
                catSubVal.setText(mainCategory.getName());
                SubCatId = mainCategory.getId();
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.setAdapter(listPopupWindowAdapter);

        listPopupWindow.show();
    }

    private void intiTap() {
        colorAdapter = new ColorAdapter(colorPickers);
        ColorPicker.setSlideOnFling(true);
        ColorPicker.setAdapter(colorAdapter);
        ColorPicker.addOnItemChangedListener(FilterActivity.this);
        ColorPicker.scrollToPosition(0);
        ColorPicker.setItemTransitionTimeMillis(144);
        ColorPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        colorAdapter.setOnItemClickListener(new ObjectClickListener() {
            @Override
            public void onItemClickListener(int position, View view, ColorPicker colorPicker) {
                ColorId = colorPickers.get(position).getColor_id();

            }
        });


    }

    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
        if (isFirst)
            ColorId = colorPickers.get(adapterPosition).getColor_id();
        else {
            isFirst = true;
        }
    }


    private void getMainCategories() {
        new UserAPI().MainCategories(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseMainCategory responseCart = (ResponseMainCategory) result;
                if (responseCart.isStatus()) {
                    mainCategoryList.addAll(responseCart.getMainCategories());
                } else {
                    Constants.showDialog(FilterActivity.this, responseCart.getMessage());
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(FilterActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(FilterActivity.this, message);
            }
        });
    }

    private void getCategoryContent(String category_id, String page) {
        frameLoading.setVisibility(View.VISIBLE);
        new UserAPI().CategoryContent(category_id, page, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseCategoryContent responseCategoryContent = (ResponseCategoryContent) result;
                if (responseCategoryContent.isStatus()) {
                    if (!responseCategoryContent.getCategoryContent().isFinalCont()) {
                        categoryContentList.addAll(responseCategoryContent.getCategoryContent().getCategories());

                    } else {
                        Intent intent = new Intent(FilterActivity.this, ProductListActivity.class);
                        intent.putExtra("ProductList", (Serializable) responseCategoryContent.getCategoryContent().getProducts());
                        startActivity(intent);
                    }

                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(FilterActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(FilterActivity.this, message);
            }
        });
    }


    private void getFilterDataResult(String category_id, String page, String colors, String price_to, String price_form) {
        frameLoading.setVisibility(View.VISIBLE);
        new UserAPI().getFilterProductResult(category_id, page, colors, price_to, price_form, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseCategoryContent responseObject = (ResponseCategoryContent) result;
                if (responseObject.isStatus()) {
                    List<Product> products = responseObject.getCategoryContent().getProducts();

                    if (products.size() == 0) {
                        Constants.showDialog(FilterActivity.this, getResources().getString(R.string.ProductNull));
                    } else {
                        Intent intent = new Intent(FilterActivity.this, ProductListActivity.class);
                        intent.putExtra("ProductList", (Serializable) products);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Constants.showDialog(FilterActivity.this, getResources().getString(R.string.ProductNull));
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(FilterActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                frameLoading.setVisibility(View.GONE);
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(FilterActivity.this, message);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FilterActivity.this, HomeActivity.class));
        finish();
    }


}
