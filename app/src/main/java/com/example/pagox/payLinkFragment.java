package com.example.pagox;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class payLinkFragment extends Fragment {
    PayLinkAdapter linkAdapter;
    Button btn_card,btn_profile;
    ArrayList<PayLink> items = new ArrayList<>();

    ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pay_link, container, false);
        listView = view.findViewById(R.id.lisview);
//        btn_card = view.findViewById(R.id.btn_card);
//        btn_profile = view.findViewById(R.id.btn_profile);
        listViewWork();

        return view;
    }
    public void listViewWork(){

        PayLink payLink1 = new PayLink("7/29/2020 8:31:46 PM","false","false","pending","false",53463,"https://gateway.pagox.io/payment/onlinepay?pid=gV6GYMmpFWkWf+BT5srBIA==");
        PayLink payLink2 = new PayLink("7/29/2020 8:31:46 PM","false","false","pending","false",53463,"https://gateway.pagox.io/payment/onlinepay?pid=gV6GYMmpFWkWf+BT5srBIA==");
        PayLink payLink3 = new PayLink("7/29/2020 8:31:46 PM","false","false","pending","false",53463,"https://gateway.pagox.io/payment/onlinepay?pid=gV6GYMmpFWkWf+BT5srBIA==");
        PayLink payLink4 = new PayLink("7/29/2020 8:31:46 PM","false","false","pending","false",53463,"https://gateway.pagox.io/payment/onlinepay?pid=gV6GYMmpFWkWf+BT5srBIA==");

        items.add(payLink1);
        items.add(payLink2);
        items.add(payLink3);
        items.add(payLink4);

        linkAdapter = new PayLinkAdapter(getActivity(),R.layout.pay_link_row,items);
        listView.setAdapter(linkAdapter);
    }

}
