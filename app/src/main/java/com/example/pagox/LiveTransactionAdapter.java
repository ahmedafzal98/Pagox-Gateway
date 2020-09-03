package com.example.pagox;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LiveTransactionAdapter extends ArrayAdapter<LiveTransactionAdapter>{

    private Context mcontext;
    int mresource;
    private ArrayList<LiveTransaction> list;

    public LiveTransactionAdapter(@NonNull Context context, int resource, @NonNull ArrayList<LiveTransaction> arrayList) {
        super(context, resource);
        mcontext = context;
        mresource = resource;
        list = arrayList;
    }

    public void update(ArrayList<LiveTransaction> results){

        list = new ArrayList<>();
        list.addAll(results);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       String amount = list.get(position).getAmount();
       String minutes = list.get(position).getMinutes();
       String refId = list.get(position).getRef_id();
       String textCardHolder = list.get(position).getTextCardHolder();
       String paymentId = list.get(position).getPaymentTypeId();
       String last4digit = list.get(position).getLast4digit();
       String status = list.get(position).getStatus();
       String remAmount = list.get(position).getRemAmount();
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(R.layout.row,null);
        }
        TextView txt_card_hol = convertView.findViewById(R.id.text_Card_Holder);
        TextView txt_usd = convertView.findViewById(R.id.amount);
        TextView txt_amount = convertView.findViewById(R.id.usd);
        TextView txt_min = convertView.findViewById(R.id.minutes);
        TextView txt_pay_id = convertView.findViewById(R.id.txt_paymemt_id);
        TextView txt_last4digit = convertView.findViewById(R.id.txt_last4digit);
        TextView txt_remAmount = convertView.findViewById(R.id.txt_remAmount);


        txt_last4digit.setText(last4digit);
        if (status == "Declined"){
            txt_last4digit.setTextColor(Color.RED);
        }else {
            txt_last4digit.setTextColor(Color.GREEN);
        }

        txt_min.setText(minutes);
        txt_usd.setText(refId);
        txt_card_hol.setText(textCardHolder);
        txt_pay_id.setText(paymentId);



        if (remAmount!=null){
            txt_remAmount.setVisibility(View.VISIBLE);
            txt_remAmount.setText("USD"+ " " + remAmount);

        }else {
           txt_amount.setText("USD"+ " " + amount);
        }
        return convertView;
    }
}
