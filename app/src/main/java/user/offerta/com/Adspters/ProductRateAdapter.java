package user.offerta.com.Adspters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.List;

import user.offerta.com.Module.Product;
import user.offerta.com.Module.ProductItem;
import user.offerta.com.R;


public class ProductRateAdapter extends RecyclerView.Adapter<ProductRateAdapter.MyViewHolder> {
    private List<ProductItem> products;
    private Context context;


    public ProductRateAdapter(List<ProductItem> products, Context context) {
        this.products = products;
        this.context = context;

    }


    @Override
    public ProductRateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rate_product_item, parent, false);
        return new ProductRateAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ProductRateAdapter.MyViewHolder holder, final int position) {
        final ProductItem product = products.get(position);
        holder.title.setText(product.getName());

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        SimpleRatingBar points;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.product_name);
            points= view.findViewById(R.id.points);

        }
    }


    @Override
    public int getItemCount() {
        return products.size();
    }




}


