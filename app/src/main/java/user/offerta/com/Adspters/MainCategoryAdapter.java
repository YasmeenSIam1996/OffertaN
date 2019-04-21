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

import user.offerta.com.Interfaces.ContentClickListener;
import user.offerta.com.Module.MainCategory;
import user.offerta.com.R;

import static user.offerta.com.Utils.Constants.Image_URL;


public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.MyViewHolder> {
    private List<MainCategory> categoryList;
    private Context context;


    private static ContentClickListener sClickListener;


    public void setOnItemClickListener(ContentClickListener clickListener) {
        sClickListener = clickListener;
    }

    public MainCategoryAdapter(List<MainCategory> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;

    }


    @Override
    public MainCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_category_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MainCategoryAdapter.MyViewHolder holder, final int position) {
        final MainCategory category = categoryList.get(position);
        holder.title.setText(category.getName());

        holder.frameLoading.setVisibility(View.VISIBLE);
        Picasso.with(context).load(Image_URL + category.getImage())
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
                sClickListener.onItemClickListener(position,view,category);
            }
        });


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView Main_Cat_image;
        FrameLayout frameLoading;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tittle);
            Main_Cat_image = view.findViewById(R.id.Main_Cat_image);
            frameLoading=view.findViewById(R.id.frameLoading);

        }
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }


}


