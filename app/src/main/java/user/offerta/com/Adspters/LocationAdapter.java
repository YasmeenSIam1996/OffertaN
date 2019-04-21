package user.offerta.com.Adspters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;


import java.util.List;

import user.offerta.com.Interfaces.DeleteLocationClickListener;
import user.offerta.com.Interfaces.ProductClickListener;
import user.offerta.com.Module.LocationData;
import user.offerta.com.R;


public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {
    private List<LocationData> locationData;
    private Context context;
    private int idChecked = -1;
    private double lat = 0, lang = 0;
    private boolean isDelete;
    private static DeleteLocationClickListener sClickListener;


    public void setOnItemClickListener(DeleteLocationClickListener clickListener) {
        sClickListener = clickListener;
    }

    public LocationAdapter(List<LocationData> locationData, Context context, boolean isDelete) {
        this.locationData = locationData;
        this.context = context;
        this.isDelete = isDelete;
    }

    @Override
    public LocationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final LocationAdapter.MyViewHolder holder, final int position) {
        final LocationData data = locationData.get(position);
        holder.title.setText(data.getName());
        holder.radio.setChecked(position == idChecked);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sClickListener.onItemClickListener(position, view, data);

            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        RadioButton radio;
        ImageView delete;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.location_text);
            radio = view.findViewById(R.id.radio);
            delete = view.findViewById(R.id.delete);
            radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocationData locationData1 = locationData.get(getAdapterPosition());
                    idChecked = getAdapterPosition();
                    notifyDataSetChanged();
                    lat = locationData1.getLat();
                    lang = locationData1.getLng();

                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocationData locationData1 = locationData.get(getAdapterPosition());
                    idChecked = getAdapterPosition();
                    notifyDataSetChanged();
                    lat = locationData1.getLat();
                    lang = locationData1.getLng();
                }
            });
            if (isDelete) {
                delete.setVisibility(View.VISIBLE);
                radio.setVisibility(View.GONE);

            } else {
                delete.setVisibility(View.GONE);
                radio.setVisibility(View.VISIBLE);
            }


        }
    }


    @Override
    public int getItemCount() {
        return locationData.size();
    }

    public double getLat() {
        return lat;
    }

    public double getLang() {
        return lang;
    }


}


