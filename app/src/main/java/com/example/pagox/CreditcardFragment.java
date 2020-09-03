package com.example.pagox;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class CreditcardFragment extends Fragment {

    EditText edit_credit, edit_card_holder_name, edit_cvv, edit_date, edit_amount, edit_month;
    Button btn_transaction;
    CheckBox check_isProfile;
    RadioButton radio_sale,radio_auth;
    TextView txt_click;
    ProgressBar progressBar;
    String validate_URL;
    String value;
    Boolean check;
    int pos = 3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creditcard, container, false);
        txt_click = view.findViewById(R.id.txt_clickHere);
        edit_credit = view.findViewById(R.id.edit_card_num);
        edit_card_holder_name = view.findViewById(R.id.edit_card_hol_name);
        edit_cvv = view.findViewById(R.id.edit_cvv);
        edit_date = view.findViewById(R.id.edit_exp_date);
        edit_amount = view.findViewById(R.id.edit_amount);
        edit_month = view.findViewById(R.id.edit_exp_month);
        radio_auth = view.findViewById(R.id.radio_auth);
        radio_sale = view.findViewById(R.id.radio_sale);
        btn_transaction = view.findViewById(R.id.btn_transaction);
        check_isProfile = view.findViewById(R.id.check_isProfile);
        progressBar = view.findViewById(R.id.prog_bar);
        validate_URL = " https://api.pagox.io/payment/validatePayment";

        txt_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view2 = getLayoutInflater().inflate(R.layout.alert_dialog_customer, null);
                builder.setView(view2);
                final AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });

        transactionWork();
        check_isProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_isProfile.isChecked()){
                    check = true;
                }else {
                    check = false;
                }
            }
        });

        return view;

    }

    public void transactionWork() {
        btn_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radio_sale.isChecked()){
                    value = "1";
                }else if (radio_auth.isChecked()){
                    value = "2";
                }


                String holderName = edit_card_holder_name.getText().toString();
                String credit = edit_credit.getText().toString();
                String cvv = edit_cvv.getText().toString();
                String date = edit_date.getText().toString();
                String amount = edit_amount.getText().toString();
                String expMonth = edit_month.getText().toString();
//                String radioSale = radio_sale.getText().toString();
//                String radioAuth = radio_auth.getText().toString();

                if (holderName.length() > 0) {
                    if (credit.length() > 0) {
                        if (cvv.length() > 0) {
                            if (date.length() > 0) {
                                if (amount.length() > 0) {
                                    if (expMonth.length() > 0) {
                                        if (radio_sale.isChecked()==false||radio_auth.isChecked()==false){

                                            transaction(credit, holderName, cvv, date, amount, expMonth);
                                        }else {
                                            radio_auth.setError("Please select");
                                            radio_sale.setError("Please select");
                                        }

                                    } else {
                                        edit_month.setError("Enter Expiry Month");
                                    }

                                } else {
                                    edit_amount.setError("Enter Amount");
                                }
                            } else {
                                edit_date.setError("Enter Expiry Date");
                            }
                        } else {
                            edit_cvv.setError("Enter CVV ");
                        }

                    } else {
                        edit_credit.setError("Enter Credit Card Number");
                    }
                } else {
                    edit_card_holder_name.setError("Enter Card Holder Name");
                }
            }
        });
    }

    public void transaction(final String credit, final String holderName, final String cvv, final String date, final String amount, final String expMonth) {

        progressBar.setVisibility(View.VISIBLE);
        btn_transaction.setVisibility(View.GONE);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, validate_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    if (jsonObject.has("IsError")) {
                        String isError = jsonObject.getString("IsError");
                        String message = jsonObject.getString("Message");
                        if (isError == "false") {

                            Intent intent = new Intent(getActivity(), summary.class);
                            intent.putExtra("credit", credit);
                            intent.putExtra("holderName", holderName);
                            intent.putExtra("cvv", cvv);
                            intent.putExtra("date", date);
                            intent.putExtra("amount", amount);
                            intent.putExtra("message", message);
                            intent.putExtra("expiMonth", expMonth);
                            intent.putExtra("value", value);
                            intent.putExtra("isProfile" , check);

                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Transaction Declined")
                                    .setMessage(message)
                                    .setIcon(R.drawable.ic_error_outline_black_24dp)
                                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                            builder.show();
                        }
                    } else {
                        JSONObject result = jsonObject.getJSONObject("Result");
                        String isError = result.getString("IsError");
                        final String message = result.getString("Message");
                        if (isError == "false") {
                            Intent intent = new Intent(getActivity(), summary.class);
                            intent.putExtra("credit", credit);
                            intent.putExtra("holderName", holderName);
                            intent.putExtra("cvv", cvv);
                            intent.putExtra("date", date);
                            intent.putExtra("amount", amount);
                            intent.putExtra("message", message);
                            intent.putExtra("expiMonth", expMonth);
                            intent.putExtra("value", value);
                            intent.putExtra("isProfile" , check);
                            startActivity(intent);
                            getActivity().finish();

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Transaction Declined")
                                    .setMessage(message)
                                    .setIcon(R.drawable.ic_error_outline_black_24dp)
                                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                            builder.show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressBar.setVisibility(View.GONE);
                btn_transaction.setVisibility(View.VISIBLE);

//                Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(getActivity(), "Internet Failure", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btn_transaction.setVisibility(View.VISIBLE);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("AuthorizationKey", "A_testA6Z21bIRnibIjRw1JMZUSRjt6ePl48ptcLD9YCdIdKw=");
                params.put("TransactionKey", "T_testA6Z21bIRnibIjRw1JMZUSZAFVu5lq2miQXsBcvgwBaw=");
                return params;
            }

            @Override
            public byte[] getBody() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("TenderTypeID", "1");
                    jsonObject.put("Amount", amount);
                    JSONObject cardInfo = new JSONObject();
                    cardInfo.put("CardHolderName", holderName);
                    cardInfo.put("PaymentTypeID", value);
                    cardInfo.put("CardNumber", credit);
                    cardInfo.put("CVV", cvv);
                    cardInfo.put("ExpiryMonth", expMonth);
                    cardInfo.put("ExpiryYear", date);
                    cardInfo.put("IsProfileSave",check);

                    jsonObject.put("CardInformation", cardInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return jsonObject.toString().getBytes();
            }
        };
        requestQueue.add(objectRequest);


    }

}
