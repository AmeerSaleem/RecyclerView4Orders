package com.example.recyclerview4orders;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ArrayList<OrderClass> list_order_details;
    AlertDialog alertDialog;
    TextView dialog_submit_button,dialog_full_payment;
    EditText dialog_input_pay,dialog_input_total_pay;
    RecyclerView rcv_orders;
    int intTotalCost,intTotalRemaining,intTotalPaid;
    LinearLayout add_order_button;

    TextView TotalCost,TotalRemaining,TotalPaid,TotalPayButton,DialogSubmitPay,DialogTotalPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TotalPayButton = findViewById(R.id.total_pay_button);

//        TotalPayButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
//                alertDialog = dialogBuilder.create();
//                View dialog_view = getLayoutInflater().inflate(R.layout.dialog_layout_total_pay,null);
//                alertDialog.setView(dialog_view);
//                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//                DialogSubmitPay = dialog_view.findViewById(R.id.dialog_total_pay_submit);
//                DialogTotalPay = dialog_view.findViewById(R.id.dialog_total_payment_text);
//                dialog_input_total_pay = dialog_view.findViewById(R.id.dialog_edit_text_total_pay_input);
//
//                DialogSubmitPay.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(TextUtils.isEmpty(dialog_input_total_pay.getText().toString())){
//                            Toast.makeText(MainActivity.this, "Please enter amount to pay", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                    }
//                });
//
//            }
//        });
        add_order_button = findViewById(R.id.add_order);
        add_order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Order added", Toast.LENGTH_SHORT).show();
            }
        });
        TotalCost = findViewById(R.id.total_cost);
        TotalRemaining = findViewById(R.id.remaining_balance);
        TotalPaid = findViewById(R.id.total_paid);
        intTotalCost = 0;
        intTotalRemaining = 0;
        intTotalPaid = 0;
        rcv_orders = findViewById(R.id.recycler_order_list);
        list_order_details = new ArrayList<>();

        list_order_details.add(new OrderClass("01","A1306X","06 May 2019","8140","8140","0",true));
        list_order_details.add(new OrderClass("02","A1305X","06 May 2019","11300","0","11300",false));
        list_order_details.add(new OrderClass("03","A1304X","03 May 2019","5800","5800","0",true));
        list_order_details.add(new OrderClass("04","A1303X","02 May 2019","2600","2600","0",true));
        list_order_details.add(new OrderClass("05","A1302X","02 May 2019","4300","4300","0",true));

        getTotalValues();

        final OrderListAdapter adapter = new OrderListAdapter(getApplicationContext(),list_order_details);
        adapter.setOnItemClickListener(new OrderListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(final int position) {
//                Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                if (list_order_details.get(position).isPending) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialog = dialogBuilder.create();
                    View dialog_view = getLayoutInflater().inflate(R.layout.dialog_layout,null);
                    alertDialog.setView(dialog_view);
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    dialog_submit_button = dialog_view.findViewById(R.id.dialog_submit);
                    dialog_full_payment = dialog_view.findViewById(R.id.dialog_full_payment_text);
                    dialog_input_pay = dialog_view.findViewById(R.id.dialog_edit_text_input1);
                    dialog_submit_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(TextUtils.isEmpty(dialog_input_pay.getText().toString())){
                                Toast.makeText(MainActivity.this, "Please enter amount to pay", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            int pay_input = Integer.parseInt(dialog_input_pay.getText().toString());
                            int order_remaining = Integer.parseInt(list_order_details.get(position).remaining);

                            if(pay_input > order_remaining ){
                                Toast.makeText(MainActivity.this, "Payment higher than outstanding. Try Again", Toast.LENGTH_SHORT).show();
                            }
                            else{

    //                            OrderClass oc = list_order_details.get(position);
                                int new_remaining = Integer.parseInt(list_order_details.get(position).getRemaining()) - pay_input;
                                int new_paid = Integer.parseInt(list_order_details.get(position).getPaid()) + pay_input;
                                list_order_details.get(position).setPaid(String.valueOf(new_paid));
                                list_order_details.get(position).setRemaining(String.valueOf(new_remaining));
                                if(new_remaining == 0){
    //                                Toast.makeText(MainActivity.this, "yes", Toast.LENGTH_SHORT).show();
                                    list_order_details.get(position).isPending = false;
                                }

                                    alertDialog.dismiss();
                                    adapter.notifyItemChanged(position);
                                    getTotalValues();
    //                                new Timer().schedule(new TimerTask() {
    //                                    @Override
    //                                    public void run() {
    //
    //                                    }
    //                                },1000);
                            }

                        }
                    });

                    dialog_full_payment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            list_order_details.get(position).setRemaining(String.valueOf(0));
                            list_order_details.get(position).setPaid(list_order_details.get(position).getOrderCost());
                            list_order_details.get(position).isPending = false;

                            alertDialog.dismiss();
                            adapter.notifyItemChanged(position);
                            getTotalValues();

                        }
                    });

//                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                    alertDialog.show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Selected order already processed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPrintClick(int position) {

            }
        });


        TotalPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (list_order_details.get(list_order_details.size()-1).isPending) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialog = dialogBuilder.create();
                    View dialog_view = getLayoutInflater().inflate(R.layout.dialog_layout_total_pay,null);
                    alertDialog.setView(dialog_view);
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    DialogSubmitPay = dialog_view.findViewById(R.id.dialog_total_pay_submit);
                    DialogTotalPay = dialog_view.findViewById(R.id.dialog_total_payment_text);
                    dialog_input_total_pay = dialog_view.findViewById(R.id.dialog_edit_text_total_pay_input);

                    DialogTotalPay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TotalPaid.setText(TotalCost.getText().toString());
                            TotalRemaining.setText("Rs 0" );

                            for(int i = 0;i < list_order_details.size();i++){
                                list_order_details.get(i).isPending = false;
                                list_order_details.get(i).remaining = "0";
                                list_order_details.get(i).paid = list_order_details.get(i).orderCost;

                            }
                            adapter.notifyDataSetChanged();
                            alertDialog.dismiss();
                        }
                    });
                    DialogSubmitPay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(TextUtils.isEmpty(dialog_input_total_pay.getText().toString())){
                                Toast.makeText(MainActivity.this, "Please enter amount to pay", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            int pay_input1 = Integer.parseInt(dialog_input_total_pay.getText().toString());
                            int order_remaining1 = Integer.parseInt(TotalRemaining.getText().toString().substring(3));

                            if(pay_input1 > order_remaining1 ){
                                Toast.makeText(MainActivity.this, "Payment higher than outstanding. Try Again", Toast.LENGTH_SHORT).show();
                            }
                            else{
//                                order_remaining1 -= pay_input1;
//                                TotalRemaining.setText("Rs " + String.valueOf(order_remaining1));
//                                int total_paid = Integer.parseInt( TotalPaid.getText().toString().substring(3));
//                                total_paid += pay_input1;
//                                TotalPaid.setText(String.valueOf(total_paid));

                                for(int i =0;i < list_order_details.size();i++){
                                    if(pay_input1 > 0) {
                                        if (list_order_details.get(i).isPending) {
                                            if (Integer.parseInt(list_order_details.get(i).remaining) < pay_input1) {

                                                pay_input1 -= Integer.parseInt(list_order_details.get(i).remaining);
                                                list_order_details.get(i).remaining = "0";
                                                list_order_details.get(i).isPending = false;
                                                list_order_details.get(i).setPaid(list_order_details.get(i).getOrderCost());

                                            } else if ((Integer.parseInt(list_order_details.get(i).remaining) > pay_input1)&& (pay_input1 > 0)) {

                                                int remaining = (Integer.parseInt(list_order_details.get(i).remaining)) - pay_input1;
                                                list_order_details.get(i).remaining = String.valueOf(remaining);
                                                int init_paid = Integer.parseInt(list_order_details.get(i).getPaid());
                                                init_paid += pay_input1;
                                                list_order_details.get(i).paid = String.valueOf(init_paid);
                                                pay_input1 = 0;
                                                break;
                                            } else if(pay_input1 != 0){
                                                list_order_details.get(i).remaining = "0";
                                                list_order_details.get(i).isPending = false;
                                                list_order_details.get(i).setPaid(list_order_details.get(i).getOrderCost());
                                                pay_input1 = 0;
                                                break;

                                            }
                                        }
                                    }
                                }

                                alertDialog.dismiss();
                                adapter.notifyDataSetChanged();
                            }

                            getTotalValues();

                        }
                    });

                    alertDialog.show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Payment for shop complete", Toast.LENGTH_SHORT).show();
                }
            }
        });



        LinearLayoutManager manager = new LinearLayoutManager(null);
        rcv_orders.setLayoutManager(manager);
        rcv_orders.setAdapter(adapter);
        startIntroAnimation();
    }

    private void startIntroAnimation() {


        rcv_orders.setTranslationX(rcv_orders.getWidth());
        rcv_orders.setAlpha(0f);
        rcv_orders.animate()
                .translationX(0f)
                .setDuration(1000)
                .alpha(1f)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

    }

    private void getTotalValues() {

        intTotalCost = 0;
        intTotalRemaining = 0;
        intTotalPaid = 0;

        for(OrderClass oc:list_order_details){

                intTotalCost += Integer.parseInt(oc.getOrderCost());
                intTotalRemaining += Integer.parseInt(oc.getRemaining());
                intTotalPaid += Integer.parseInt(oc.getPaid());

        }

        TotalCost.setText("Rs " + String.valueOf(intTotalCost));
        TotalRemaining.setText("Rs " + String.valueOf(intTotalRemaining));
        TotalPaid.setText("Rs " +  String.valueOf(intTotalPaid));

    }

}
