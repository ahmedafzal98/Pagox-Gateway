package com.example.pagox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PayLinkAdapter extends ArrayAdapter {

    private Context mcontext;
    int mresource;
    private ArrayList<PayLink> list;
    public PayLinkAdapter(@NonNull Context context, int resource, @NonNull ArrayList<PayLink> arrayList) {
        super(context, resource);
        mcontext = context;
        mresource = resource;
        list = arrayList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String date = list.get(position).getDate();
        String expire = list.get(position).getExpire();
        String cancel = list.get(position).getCancel();
        String paydate = list.get(position).getPayDate();
        String paid = list.get(position).getPaid();
        int accessCode = list.get(position).getAccessCode();
        String paylink = list.get(position).getPayLink();
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(R.layout.pay_link_row,null);
        }
        TextView txt_date = convertView.findViewById(R.id.date);
        TextView txt_expire = convertView.findViewById(R.id.expiry);
        TextView txt_cancel = convertView.findViewById(R.id.cancel);
        TextView txt_pay_date = convertView.findViewById(R.id.pay_date);
        TextView txt_paid = convertView.findViewById(R.id.paid);
        TextView txt_access_code = convertView.findViewById(R.id.access_code);

        txt_date.setText(String.valueOf(date));
        txt_expire.setText(String.valueOf(expire));
        txt_cancel.setText(cancel);
        txt_pay_date.setText(paydate);
        txt_paid.setText(paid);
        txt_access_code.setText(String.valueOf(accessCode));
        return convertView;
    }
}
