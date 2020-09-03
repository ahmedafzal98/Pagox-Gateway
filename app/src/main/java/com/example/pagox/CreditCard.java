//package com.example.pagox;
//
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//
//import android.graphics.PorterDuff;
//import android.os.Build;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.TextView;
//import androidx.appcompat.widget.Toolbar;
//
//public class CreditCard extends AppCompatActivity {
//
//    TextView car_no,swipe_card,txt_cvv,card_holder,exp_year,exp_month,amoun,txt_acc_num,txt_acc_name,txt_rou_num,txt_amon;
//
//    EditText card1,card2,card3,card4,expiry_month,cvv,card_holder_name,expiry_year,amount,first_name,last_name,country,city
//            ,company_name,phone_no,address1,address2,state,zip_code,email,invoice,account_no,account_name,routing_no,Camount;
//    Toolbar toolbar2;
//    CheckBox checkCredit,echeck,sale,auth,recurring;
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_credit_card);
//
//        init();
//        creditCardWork();
//        editTextWork();
//        checkBoxWork();
//
//        setSupportActionBar(toolbar2);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setTitle("Payment Details");
////        toolbar2.setLogo(R.drawable.ic_menu_black_24dp);
//    }
//
//    public void creditCardWork(){
//        card1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    card1.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    card1.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
//        card1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (card1.length() ==4){
//                    card1.clearFocus();
//                    card2.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        card2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    card2.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    card2.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
//        card2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                if (card2.length()==4){
//                    card2.clearFocus();
//                    card3.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        card3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    card3.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    card3.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
//
//        card3.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (card3.length()==4){
//                    card3.clearFocus();
//                    card4.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        card4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    card4.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    card4.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
//
//        card4.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (card4.length()==4){
//                    card4.clearFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//    }
//
//    public void editTextWork(){
//        expiry_month.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    if (hasFocus){
//                        expiry_month.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                    }else {
//                        expiry_month.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                    }
//                }
//            }
//        });
//
//        cvv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    cvv.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    cvv.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
//
//        card_holder_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    card_holder_name.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    card_holder_name.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
//
//        expiry_year.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    expiry_year.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    expiry_year.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
//
//        amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    amount.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    amount.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
//        first_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    first_name.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    first_name.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
//        last_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    last_name.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    last_name.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
////        country.setOnFocusChangeListener(new View.OnFocusChangeListener() {
////            @Override
////            public void onFocusChange(View view, boolean hasFocus) {
////                if (hasFocus){
////                    country.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
////                }else {
////                    country.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
////
////                }
////            }
////        });
//        city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    city.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    city.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
//        company_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    company_name.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    company_name.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
//        phone_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    phone_no.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    phone_no.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
//
//        address1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    address1.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    address1.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
//        address2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    address2.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    address2.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
//        state.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    state.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    state.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
//
//        zip_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    zip_code.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    zip_code.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
//        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    email.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    email.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
//        invoice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus){
//                    invoice.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint), PorterDuff.Mode.SRC_ATOP);
//                }else {
//                    invoice.getBackground().mutate().setColorFilter(ContextCompat.getColor(getActivity(), R.color.tint2), PorterDuff.Mode.SRC_ATOP);
//
//                }
//            }
//        });
//    }
//
//    public void checkBoxWork(){
//
//        echeck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (echeck.isChecked()){
//                    card1.setVisibility(View.GONE);
//                    card2.setVisibility(View.GONE);
//                    card3.setVisibility(View.GONE);
//                    card4.setVisibility(View.GONE);
//                    cvv.setVisibility(View.GONE);
//                    car_no.setVisibility(View.GONE);
//                    txt_cvv.setVisibility(View.GONE);
//                    card_holder.setVisibility(View.GONE);
//                    exp_year.setVisibility(View.GONE);
//                    expiry_month.setVisibility(View.GONE);
//                    amoun.setVisibility(View.GONE);
//
////                    swipe_card.setVisibility(View.GONE);
//                    card_holder_name.setVisibility(View.GONE);
//                    expiry_year.setVisibility(View.GONE);
//                    amount.setVisibility(View.GONE);
//                    account_name.setVisibility(View.VISIBLE);
//                    account_no.setVisibility(View.VISIBLE);
//                    routing_no.setVisibility(View.VISIBLE);
//                    exp_month.setVisibility(View.GONE);
//                    Camount.setVisibility(View.VISIBLE);
////                    txt_acc_num.setVisibility(View.VISIBLE);
////                    txt_acc_num.setVisibility(View.VISIBLE);
////                    txt_rou_num.setVisibility(View.VISIBLE);
////                    amoun.setVisibility(View.VISIBLE);
//                    checkCredit.setChecked(false);
//                    sale.setChecked(true);
//                }
//            }
//        });
//
//        checkCredit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                echeck.setChecked(false);
//                account_no.setVisibility(View.GONE);
//                account_name.setVisibility(View.GONE);
//                routing_no.setVisibility(View.GONE);
//                amount.setVisibility(View.GONE);
//
//                card1.setVisibility(View.VISIBLE);
//                card2.setVisibility(View.VISIBLE);
//                card3.setVisibility(View.VISIBLE);
//                card4.setVisibility(View.VISIBLE);
//                cvv.setVisibility(View.VISIBLE);
//                car_no.setVisibility(View.VISIBLE);
//                txt_cvv.setVisibility(View.VISIBLE);
//                card_holder.setVisibility(View.VISIBLE);
//                exp_year.setVisibility(View.VISIBLE);
//                expiry_month.setVisibility(View.VISIBLE);
//                exp_month.setVisibility(View.VISIBLE);
//                amoun.setVisibility(View.VISIBLE);
//                Camount.setVisibility(View.GONE);
//                swipe_card.setVisibility(View.VISIBLE);
//                card_holder_name.setVisibility(View.VISIBLE);
//                expiry_year.setVisibility(View.VISIBLE);
//                amount.setVisibility(View.VISIBLE);
//                sale.setChecked(true);
//
//            }
//        });
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public void init(){
//        card1 = findViewById(R.id.card1);
//        card2 = findViewById(R.id.card2);
//        card3 = findViewById(R.id.card3);
//        card4 = findViewById(R.id.card4);
//        expiry_month = findViewById(R.id.exp_mon);
//        cvv = findViewById(R.id.cvv);
//        card_holder_name = findViewById(R.id.cardHolderName);
//        expiry_year = findViewById(R.id.expiryYear);
//        amount = findViewById(R.id.amount);
//        first_name = findViewById(R.id.first_name);
//        last_name =findViewById(R.id.last_name);
////        country = findViewById(R.id.country);
//        city = findViewById(R.id.city);
//        company_name = findViewById(R.id.company_name);
//        phone_no = findViewById(R.id.phone_no);
//        address1 = findViewById(R.id.adress1);
//        address2 = findViewById(R.id.adress2);
//        state = findViewById(R.id.state);
//        zip_code = findViewById(R.id.postal_code);
//        email = findViewById(R.id.email);
//        invoice = findViewById(R.id.invoice);
//        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
//        checkCredit = findViewById(R.id.check_credit_card);
//        echeck = findViewById(R.id.check_echeck);
//        sale = findViewById(R.id.check_sale);
//        auth = findViewById(R.id.check_auth);
//        recurring = findViewById(R.id.check_recurring);
//        account_no = findViewById(R.id.acc_num);
//        account_name = findViewById(R.id.acc_name);
//        routing_no = findViewById(R.id.rou_num);
//        Camount = findViewById(R.id.c_amount);
////        swipe_card = findViewById(R.id.txt_swipe);
//        txt_cvv = findViewById(R.id.txt_cvv);
//        card_holder = findViewById(R.id.txt_card_holder);
//        exp_year = findViewById(R.id.txt_expiry_year);
//        exp_month = findViewById(R.id.txt_expiry_month);
//        amoun = findViewById(R.id.txt_amount);
//        car_no = findViewById(R.id.txt_card);
//        txt_acc_num = findViewById(R.id.acc_num);
//        txt_acc_name = findViewById(R.id.acc_name);
//        txt_rou_num = findViewById(R.id.txt_rou_num);
//        txt_amon = findViewById(R.id.txt_amounts);
//    }
//
//}
