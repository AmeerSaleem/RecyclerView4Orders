package com.example.recyclerview4orders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ProductItemHolder> {

    Context context;
    ArrayList<OrderClass> order_list;
    onItemClickListener mListener;

    public interface onItemClickListener {
        public void onItemClick(int position);
        public void onPrintClick(int position);
    }

    public void setOnItemClickListener(OrderListAdapter.onItemClickListener listener) {
        mListener = listener;
    }

    public OrderListAdapter(Context context, ArrayList<OrderClass> order_list) {
        this.context = context;
        this.order_list = order_list;
    }

    public class ProductItemHolder extends RecyclerView.ViewHolder {

        TextView order_id,order_date,order_amount,order_payment_remaining,order_amount_paid;
        TextView order_paid_display,order_pending_display;
        TextView enter_pay,print_order;
        LinearLayout linear_row;
        ImageView go2details;
        ImageView delete_icon;

        public ProductItemHolder(View itemView, final onItemClickListener listener) {
            super(itemView);

            order_id = itemView.findViewById(R.id.row_order_id);
            order_date = itemView.findViewById(R.id.row_order_date);
            order_amount = itemView.findViewById(R.id.row_order_amount);
            order_payment_remaining = itemView.findViewById(R.id.row_order_remaining);
            order_amount_paid = itemView.findViewById(R.id.row_amount_paid);

            order_paid_display = itemView.findViewById(R.id.order_status_paid);
            order_pending_display = itemView.findViewById(R.id.order_status_pending);
            enter_pay = itemView.findViewById(R.id.enter_pay1);
            print_order = itemView.findViewById(R.id.print_order1);

            enter_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

//            go2details.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onItemClick(position);
//                        }
//                    }
//                }
//            });

//            delete_icon.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listener != null){
//                        int position = getAdapterPosition();
//                        if(position != RecyclerView.NO_POSITION){
//                            listener.onPrintClick(position);
//                        }
//                    }
//                }
//            });
        }
    }

    @NonNull
    @Override
    public ProductItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(context).inflate(R.layout.row_order_list, viewGroup, false);
        ProductItemHolder pih = new ProductItemHolder(v, mListener);
        return pih;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemHolder orderItemHolder, int i) {

        OrderClass oc = order_list.get(i);

        orderItemHolder.order_id.setText(oc.orderId);

        orderItemHolder.order_date.setText(oc.date);
        orderItemHolder.order_amount.setText(oc.orderCost);
        orderItemHolder.order_payment_remaining.setText(oc.remaining);
        orderItemHolder.order_amount_paid.setText(oc.paid);

        orderItemHolder.enter_pay.setVisibility(View.VISIBLE);
        orderItemHolder.print_order.setVisibility(View.VISIBLE);

        if(oc.isPending){
            orderItemHolder.order_pending_display.setVisibility(View.VISIBLE);
            orderItemHolder.order_paid_display.setVisibility(View.GONE);

        }
        else{
            orderItemHolder.order_paid_display.setVisibility(View.VISIBLE);
            orderItemHolder.order_pending_display.setVisibility(View.GONE);

        }
//        productItemHolder.product_icon.setImageBitmap(product_list.get(i).product_image);

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    }

    @Override
    public int getItemCount() {
        return order_list.size();
    }
}
