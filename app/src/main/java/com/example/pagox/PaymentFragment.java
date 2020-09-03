package com.example.pagox;


import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class PaymentFragment extends Fragment {

    Button btn_card,btn_profile,btn_pay_link;

    Dialog dialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_payment, container, false);
        btn_card = view.findViewById(R.id.btn_credit);
        btn_profile = view.findViewById(R.id.btn_profile);
        btn_pay_link = view.findViewById(R.id.btn_pay_link);
        dialog = new Dialog(getActivity());


        return view;
    }

}
