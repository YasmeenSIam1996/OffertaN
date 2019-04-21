package user.offerta.com.Adspters;

import android.app.Activity;
import android.app.Dialog;
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

import user.offerta.com.Interfaces.CartClickListener;
import user.offerta.com.Interfaces.CartDeleteClickListener;
import user.offerta.com.Module.Product;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;

import static user.offerta.com.Utils.Constants.Image_URL;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private List<Product> products;
    private Context context;
    private Dialog dialog;
    private static CartClickListener sClickListener;
    private static CartDeleteClickListener cartDeleteClickListener;


    public void setOnItemClickListener(CartClickListener clickListener) {
        sClickListener = clickListener;
    }

    public void setOnItemClickListener(CartDeleteClickListener clickListener) {
        cartDeleteClickListener = clickListener;
    }
    public CartAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;

    }


    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CartAdapter.MyViewHolder holder, final int position) {
        final Product product = products.get(position);
        holder.title.setText(product.getName());
        holder.price.setText(product.getPrice() + "");
        holder.count.setText(product.getQuantity() + "");

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


        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.valueOf(holder.count.getText().toString()) + 1;
                sClickListener.onItemClickListener(position, view, product, holder.count, num);

            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int num = Integer.valueOf(holder.count.getText().toString());

                if (num > 1) {
                    sClickListener.onItemClickListener(position, view, product, holder.count, (num - 1));

                } else {
                    Constants.showDialog((Activity) context, "Quantity equal one");
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartDeleteClickListener.onItemClickListener(position, view, product);

            }
        });


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, price, count, product_name;
        ImageView Main_Cat_image, plus, minus, delete;
        FrameLayout frameLoading;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.product_name);
            product_name = view.findViewById(R.id.product_name);
            frameLoading = view.findViewById(R.id.frameLoading);
            price = view.findViewById(R.id.price);
            delete = view.findViewById(R.id.delete);
            plus = view.findViewById(R.id.plus);
            minus = view.findViewById(R.id.minus);
            count = view.findViewById(R.id.count);
            Main_Cat_image = view.findViewById(R.id.Main_Cat_image);


        }
    }


    @Override
    public int getItemCount() {
        return products.size();
    }



}


