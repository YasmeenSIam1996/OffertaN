package user.offerta.com.Interfaces;

import android.view.View;

import user.offerta.com.Module.LocationData;
import user.offerta.com.Module.Product;


public interface CartClickListener {
    void onItemClickListener(int position, View view, Product product,View numText,int num);
}
