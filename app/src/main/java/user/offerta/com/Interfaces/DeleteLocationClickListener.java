package user.offerta.com.Interfaces;

import android.view.View;

import user.offerta.com.Module.LocationData;
import user.offerta.com.Module.MainCategory;


public interface DeleteLocationClickListener {
    void onItemClickListener(int position, View view, LocationData locationData);
}
