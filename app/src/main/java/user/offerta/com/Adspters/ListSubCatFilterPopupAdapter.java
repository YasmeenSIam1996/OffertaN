package user.offerta.com.Adspters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import user.offerta.com.Activities.FilterActivity;
import user.offerta.com.Module.MainCategory;
import user.offerta.com.R;

public class ListSubCatFilterPopupAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<MainCategory> mDataSource;
    private LayoutInflater layoutInflater;
    private ListPopupWindowAdapter.OnClickDeleteButtonListener clickDeleteButtonListener;


    public ListSubCatFilterPopupAdapter(FilterActivity activity, List<MainCategory> sampleData, ListPopupWindowAdapter.OnClickDeleteButtonListener onClickDeleteButtonListener) {
        this.mActivity = activity;
        this.mDataSource = sampleData;
        layoutInflater = mActivity.getLayoutInflater();
        this.clickDeleteButtonListener = onClickDeleteButtonListener;
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
            holder.tvTitle = convertView.findViewById(R.id.text_title);
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