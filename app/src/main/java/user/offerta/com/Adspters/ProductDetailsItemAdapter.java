package user.offerta.com.Adspters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import user.offerta.com.Module.ProductItem;
import user.offerta.com.R;

import static user.offerta.com.Utils.Constants.Image_URL;


public class ProductDetailsItemAdapter extends RecyclerView.Adapter<ProductDetailsItemAdapter.MyViewHolder> {
    private List<ProductItem> products;
    private Context context;


    public ProductDetailsItemAdapter(List<ProductItem> products, Context context) {
        this.products = products;
        this.context = context;
    }


    @Override
    public ProductDetailsItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ProductDetailsItemAdapter.MyViewHolder holder, final int position) {
        final ProductItem product = products.get(position);
        holder.productName.setText(product.getName());
        holder.price.setText(product.getPrice() + "");
        holder.quantity.setText(product.getQuantity() + "");
        // Show progress bar
        holder.frameLoading.setVisibility(View.VISIBLE);
        // Hide progress bar on successful load
        Picasso.with(context).load(Image_URL + product.getImage())
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


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, price, productName, quantity;
        ImageView product_image;
        FrameLayout frameLoading;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.product_name);
            productName = view.findViewById(R.id.productName);
            frameLoading = view.findViewById(R.id.frameLoading);
            price = view.findViewById(R.id.price);
            product_image = view.findViewById(R.id.product_image);
            quantity = view.findViewById(R.id.quantity);
        }
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

}


