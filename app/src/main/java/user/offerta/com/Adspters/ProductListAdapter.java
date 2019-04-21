package user.offerta.com.Adspters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yarolegovich.discretescrollview.DiscreteScrollView;

import java.util.List;

import user.offerta.com.Activities.DetailsActivity;
import user.offerta.com.Interfaces.OnLoadMoreListener;
import user.offerta.com.Interfaces.ProductClickListener;
import user.offerta.com.Module.Product;
import user.offerta.com.R;

import static user.offerta.com.Utils.Constants.Image_URL;


public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DiscreteScrollView.OnItemChangedListener {
    private List<Product> products;
    private Context context;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private static ProductClickListener sClickListener;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean loading = true;


    public void setOnItemClickListener(ProductClickListener clickListener) {
        sClickListener = clickListener;
    }

    public ProductListAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;

    }

    public void setLoaded(boolean isLoad) {
        loading = isLoad;
//        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == products.size() - 1) {
            return VIEW_PROG;
        } else {
            return VIEW_ITEM;
        }
    }

    public ProductListAdapter(List<Product> products, Context context, OnLoadMoreListener mOnLoadMoreListener) {
        this.products = products;
        this.context = context;
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
            vh = new MyViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar_bottom_layout, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ProductListAdapter.MyViewHolder) {

            final Product product = products.get(position);
            ((ProductListAdapter.MyViewHolder) holder).title.setText(product.getName());
            ((ProductListAdapter.MyViewHolder) holder).price.setText(product.getPrice() + "");

            // Show progress bar
            ((ProductListAdapter.MyViewHolder) holder).frameLoading.setVisibility(View.VISIBLE);
            // Hide progress bar on successful load
            Picasso.with(context).load(Image_URL + product.getImage().get(0))
                    .into(((ProductListAdapter.MyViewHolder) holder).Main_Cat_image, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (((ProductListAdapter.MyViewHolder) holder).frameLoading != null) {
                                ((ProductListAdapter.MyViewHolder) holder).frameLoading.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });
            ((ProductListAdapter.MyViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("ProductId", product.getProduct_id() + "");
                    context.startActivity(intent);
                }
            });
            ((ProductListAdapter.MyViewHolder) holder).cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sClickListener.onItemClickListener(position, view, product);
                }
            });
        } else {

            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            if (mOnLoadMoreListener != null) {
                if (loading == true) {
                    ((ProgressViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
                    mOnLoadMoreListener.onLoadMore(position);
                } else {
                    ((ProgressViewHolder) holder).progressBar.setVisibility(View.GONE);
                }
            }

        }

    }

    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, price;
        ImageView Main_Cat_image, cart;
        FrameLayout frameLoading;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tittle);
            Main_Cat_image = view.findViewById(R.id.product_image);
            frameLoading = view.findViewById(R.id.frameLoading);
            price = view.findViewById(R.id.price);
            cart = view.findViewById(R.id.cart);


        }
    }


    @Override
    public int getItemCount() {
        return products.size();
    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.progress_bar_bottom);
        }

    }

}


