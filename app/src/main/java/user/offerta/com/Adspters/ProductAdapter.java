package user.offerta.com.Adspters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import user.offerta.com.Activities.DetailsActivity;
import user.offerta.com.Module.Product;
import user.offerta.com.R;

import static user.offerta.com.Utils.Constants.Image_URL;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private List<Product> products;
    private Context context;


    public ProductAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;

    }


    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.last_product_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ProductAdapter.MyViewHolder holder, final int position) {
        final Product product = products.get(position);
        holder.title.setText(product.getName());
        holder.price.setText(product.getPrice() + "");

        // Show progress bar
        holder.frameLoading.setVisibility(View.VISIBLE);
        // Hide progress bar on successful load
        Picasso.with(context).load(Image_URL + product.getImage().get(0))
                .into(holder.Main_Cat_image, new com.squareup.picasso.Callback() {
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("ProductId", product.getProduct_id() + "");
                context.startActivity(intent);
            }
        });


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, price;
        ImageView Main_Cat_image;
        FrameLayout frameLoading;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tittle);
            Main_Cat_image = view.findViewById(R.id.product_image);
            frameLoading = view.findViewById(R.id.frameLoading);
            price = view.findViewById(R.id.price);

        }
    }


    @Override
    public int getItemCount() {
        return products.size();
    }




}


