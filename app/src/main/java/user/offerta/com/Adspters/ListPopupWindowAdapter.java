package user.offerta.com.Adspters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import user.offerta.com.Module.ProductSize;
import user.offerta.com.R;

public class ListPopupWindowAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<ProductSize> mDataSource ;
    private LayoutInflater layoutInflater;
    private OnClickDeleteButtonListener clickDeleteButtonListener;

    public ListPopupWindowAdapter(Activity activity, List<ProductSize> dataSource, @NonNull OnClickDeleteButtonListener clickDeleteButtonListener) {
        this.mActivity = activity;
        this.mDataSource = dataSource;
        layoutInflater = mActivity.getLayoutInflater();
        this.clickDeleteButtonListener = clickDeleteButtonListener;
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public String getItem(int position) {
        return mDataSource.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_list_popup_window, null);
            holder.tvTitle =  convertView.findViewById(R.id.text_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickDeleteButtonListener.onClickDeleteButton(position);
            }
        });

        // bind data
        holder.tvTitle.setText(getItem(position));
        return convertView;
    }

    public class ViewHolder {
        private TextView tvTitle;
    }

    // interface to return callback to activity
    public interface OnClickDeleteButtonListener {
        void onClickDeleteButton(int position);
    }
}