package com.example.pagox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CardProfileAdapter extends ArrayAdapter implements Filterable {
    private Context mcontext;
    int mresource;
    private ArrayList<CardProfile> list,tempArray;
    CustomFilter cs;

    public CardProfileAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CardProfile> arrayList) {

        super(context, resource);
        mcontext = context;
        mresource = resource;
        list = arrayList;
        tempArray = arrayList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int profile_id = list.get(position).getProfileId();
        String cardHolderName = list.get(position).getCardHoldername();
        String date = list.get(position).getDate();
        String tenderType = list.get(position).getTenderType();
        String paymentType = list.get(position).getPaymentType();
        String cardNum = list.get(position).getCardNumber();
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(R.layout.card_profile_row,null);
        }
        TextView txt_profileId = convertView.findViewById(R.id.profile_id);
        TextView txt_card_hol_name = convertView.findViewById(R.id.card_holder_name);
        TextView txt_txtdate = convertView.findViewById(R.id.date);
        TextView txt_tender_type = convertView.findViewById(R.id.tender_type);
        TextView txt_payment_type = convertView.findViewById(R.id.payment_type);
        TextView txt_card_num = convertView.findViewById(R.id.card_num);

        txt_profileId.setText(String.valueOf(profile_id));
        txt_card_hol_name.setText(String.valueOf(cardHolderName));
        txt_txtdate.setText(date);
        txt_tender_type.setText(tenderType);
        txt_payment_type.setText(paymentType);
        txt_card_num.setText(String.valueOf(cardNum));
        return convertView;
    }
        @Override
    public Filter getFilter() {

        if (cs == null){
            cs = new CustomFilter();
        }
        return cs;
    }

    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults filterResults = new FilterResults();
            if(constraint!=null && constraint.length()>0) {
                constraint = constraint.toString();


                 ArrayList<CardProfile> list = new ArrayList<>();
                 for (int i = 0; i < tempArray.size(); i++) {
                    if (tempArray.get(i).getCardHoldername().contains(constraint)) {
                        CardProfile cardProfile = new CardProfile(tempArray.get(i).getProfileId(), tempArray.get(i).getCardHoldername(), tempArray.get(i).getCardNumber(), tempArray.get(i).getTenderType(), tempArray.get(i).getPaymentType(), tempArray.get(i).getDate());
                        list.add(cardProfile);
                    }

                }
                filterResults.count = list.size();
                filterResults.values = list;
            }else {

                filterResults.count = tempArray.size();
                filterResults.values = tempArray;

            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            list = (ArrayList<CardProfile>)filterResults.values;
            notifyDataSetChanged();
        }
    }
}
