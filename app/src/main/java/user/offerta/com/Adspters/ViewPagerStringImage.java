package user.offerta.com.Adspters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import java.util.List;

import user.offerta.com.Module.Advertisement;
import user.offerta.com.R;
import user.offerta.com.Utils.Constants;

/**
 * Created by JASMIN on 01/03/2017.
 */

public class ViewPagerStringImage extends PagerAdapter {

    private Context mContext;
    private List<String> mResources;

    public ViewPagerStringImage(Context mContext, List<String> mResources) {
        this.mContext = mContext;
        this.mResources = mResources;
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item, container, false);
        ImageView imageView = itemView.findViewById(R.id.img_pager_item);
        final ProgressBar progress_bar = itemView.findViewById(R.id.progress_bar);
        Picasso.with(mContext).load(Constants.Image_URL + mResources.get(position))
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (progress_bar != null) {
                            progress_bar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((LinearLayout) object);
    }
}