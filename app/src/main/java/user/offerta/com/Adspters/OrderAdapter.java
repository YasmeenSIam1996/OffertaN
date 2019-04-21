package user.offerta.com.Adspters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import user.offerta.com.Activities.OrderDetailsActivity;
import user.offerta.com.Module.Order;
import user.offerta.com.R;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private List<Order> orders;
    private Context context;


    public OrderAdapter(List<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }


    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final OrderAdapter.MyViewHolder holder, final int position) {
        final Order order = orders.get(position);
        holder.order_name.setText("#" + order.getId());
        holder.price.setText(order.getTotal_price() + "");
        holder.date.setText(order.getDate() + "");
        holder.time.setText(order.getTime() + "");

        holder.track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("orderId", order.getId());
                context.startActivity(intent);
            }
        });
        holder.invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView time, price, date, order_name;
        Button invoice, track;

        public MyViewHolder(View view) {
            super(view);
            order_name = view.findViewById(R.id.order_name);
            time = view.findViewById(R.id.time);
            date = view.findViewById(R.id.date);
            price = view.findViewById(R.id.price);
            invoice = view.findViewById(R.id.invoice);
            track = view.findViewById(R.id.track);
        }
    }


    @Override
    public int getItemCount() {
        return orders.size();
    }


}


