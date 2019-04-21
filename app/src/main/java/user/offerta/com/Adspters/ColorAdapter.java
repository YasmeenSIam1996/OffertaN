package user.offerta.com.Adspters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.List;

import user.offerta.com.Interfaces.ObjectClickListener;
import user.offerta.com.Module.ColorPicker;
import user.offerta.com.R;


public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

    private RecyclerView parentRecycler;
    private List<ColorPicker> data;
    private static ObjectClickListener sClickListener;


    public void setOnItemClickListener(ObjectClickListener clickListener) {
        sClickListener = clickListener;
    }

    public ColorAdapter(List<ColorPicker> data) {
        this.data = data;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parentRecycler = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.color_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ColorPicker colorPicker = data.get(position);
        GradientDrawable sd = (GradientDrawable) holder.imageView.getBackground().mutate();

      try {
          sd.setColor(Color.parseColor(colorPicker.getHex()));
      }catch (Exception e){
          sd.setColor(Color.parseColor(new StringBuilder(colorPicker.getHex()).deleteCharAt(1).toString()));
      }
        sd.invalidateSelf();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.color_image);
            itemView.findViewById(R.id.color_image).setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            parentRecycler.smoothScrollToPosition(getAdapterPosition());
        }
    }


}
