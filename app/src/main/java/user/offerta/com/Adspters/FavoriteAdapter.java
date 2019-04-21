package user.offerta.com.Adspters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import user.offerta.com.API.ResponseError;
import user.offerta.com.API.ResponseObject;
import user.offerta.com.API.UserAPI;
import user.offerta.com.Activities.DetailsActivity;
import user.offerta.com.Interfaces.UniversalCallBack;
import user.offerta.com.Module.Product;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;

import static user.offerta.com.Utils.Constants.Image_URL;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {
    private List<Product> products;
    private Context context;


    public FavoriteAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }


    @Override
    public FavoriteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_layout_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FavoriteAdapter.MyViewHolder holder, final int position) {
        final Product product = products.get(position);
        holder.productName.setText(product.getName());
        holder.price.setText(product.getPrice() + "");
        holder.frameLoading.setVisibility(View.VISIBLE);

        Picasso.with(context).load(Image_URL + product.getImage().get(0))
                .into(holder.product_image, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (holder.frameLoading != null) {
                            holder.frameLoading.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });

        holder.productType.setText(product.getCategory());
        holder.Like_Button.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                removeFavoriteProduct(product.getProduct_id() + "", position);
            }
        });
        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("ProductId", product.getProduct_id() + "");
                context.startActivity(intent);
            }
        });
        holder.Like_Button.setLiked(true);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, price, productName, productType;
        ImageView product_image;
        FrameLayout frameLoading;
        LikeButton Like_Button;
        RelativeLayout lay;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.product_name);
            productName = view.findViewById(R.id.productName);
            frameLoading = view.findViewById(R.id.frameLoading);
            price = view.findViewById(R.id.price);
            product_image = view.findViewById(R.id.product_image);
            productType = view.findViewById(R.id.productType);
            Like_Button = view.findViewById(R.id.Like_Button);
            lay=view.findViewById(R.id.lay);
        }
    }


    @Override
    public int getItemCount() {
        return products.size();
    }


    private void removeFavoriteProduct(String ProductId, final int position) {

        new UserAPI().deleteFavoriteProduct(ProductId, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseObject responseObject = (ResponseObject) result;
                if (responseObject.isStatus()) {
                    products.remove(position);
                    notifyDataSetChanged();
                    Constants.showDialog((Activity) context, responseObject.getMessage());
                } else {
                    Constants.showDialog((Activity) context, responseObject.getMessage());
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog((Activity) context, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void OnError(String message) {
                Constants.showDialog((Activity) context, message);
            }
        });
    }

}


