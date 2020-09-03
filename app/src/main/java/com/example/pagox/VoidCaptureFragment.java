package com.example.pagox;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;


public class VoidCaptureFragment extends Fragment {

    EditText edit_dateFrom, edit_dateTo;
    Button btn_search;
    ListView listView;
    String void_URL;
    String alert_URL;
    String date_From;
    String date_to;
    CheckBox check_void;
    CheckBox check_capture;
    CheckBox check_refund;
    LiveTransaction transaction;
    ArrayList<LiveTransaction> arrayList;
    LiveTransactionAdapter adapter;
    JsonObjectRequest objectRequest;
    boolean check;
    String remAmount;
    String id;
    String refId;
    String holderName;
    String expMonth;
    String expYear;
    String cvv;
    String cardNum;
    int value;
    float totalAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_void_capture, container, false);
        edit_dateFrom = view.findViewById(R.id.edit_dateFrom);
        edit_dateTo = view.findViewById(R.id.edit_dateTo);
        btn_search = view.findViewById(R.id.btn_search);
        listView = view.findViewById(R.id.listView);
        void_URL = "https://api.pagox.io/payment/OrderManagerlist";
        alert_URL = "https://api.pagox.io/payment/";
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        check = true;

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (date_From.length() > 0) {
                    if (date_to.length() > 0) {
                        date_From = edit_dateTo.getText().toString();
                        date_to = edit_dateFrom.getText().toString();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        date_From = simpleDateFormat.format(Date.parse(date_From.toString()));
                        date_to = simpleDateFormat.format(Date.parse(date_to.toString()));
                        check = false;

                        listWork();
                    } else {
                        edit_dateFrom.setError("Please fill this field");
                    }
                } else {
                    edit_dateTo.setError("Please fill this field");
                }

            }
        });

        edit_dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = year + "/" + month + "/" + day;
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        String date2 = dateFormat.format(Date.parse(date));
                        edit_dateTo.setText(date2);

                    }
                }, year, month, day);
                pickerDialog.show();
            }
        });

        edit_dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = year + "/" + month + "/" + day;
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        String date2 = dateFormat.format(Date.parse(date));
                        edit_dateFrom.setText(date2);

                    }
                }, year, month, day);
                pickerDialog.show();
            }
        });

        listWork();
        itemClick();
        return view;
    }

    public void itemClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {


                id = arrayList.get(position).getPaymentTypeId();
                refId = arrayList.get(position).getRef_id();
                if (id == "sale") {
                    value = 1;
                } else if (id == "auth"){
                    value = 3;
                }

                remAmount = arrayList.get(position).getRemAmount();
                holderName = arrayList.get(position).getTextCardHolder();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View view2 = getLayoutInflater().inflate(R.layout.alert_void, null);
                final EditText edit_amount = view2.findViewById(R.id.edit_amount_void);
                check_void = view2.findViewById(R.id.check_void);
                check_refund = view2.findViewById(R.id.check_refund);
                check_capture = view2.findViewById(R.id.check_capture);
                Button button = view2.findViewById(R.id.btn_submit);
                builder.setView(view2);
                edit_amount.setText(remAmount);

                check_void.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (check_void.isChecked()) {
                            check_refund.setChecked(false);
                            edit_amount.setEnabled(false);
                            value = 5;
                        }
                    }
                });
                check_refund.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        check_void.setChecked(false);
                        edit_amount.setEnabled(true);
                        value = 4;

                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(true);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                        objectRequest = new JsonObjectRequest(Request.Method.POST, alert_URL, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response.toString());
                                    if (jsonObject.has("IsError")) {
                                        String isError = jsonObject.getString("IsError");
                                        String message = jsonObject.getString("Message");
                                        if (isError == "false") {
                                            if (check_refund.isChecked()) {

                                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                                builder1.setTitle("Refund");
                                                builder1.setMessage(message);
                                                builder1.setIcon(R.drawable.ic_check_black_24dp);
                                                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                        listWork();
                                                        dialog.dismiss();

                                                    }
                                                });

                                                builder1.show();

                                            } else if (check_void.isChecked()) {

                                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                                builder1.setTitle("Void");
                                                builder1.setMessage(message);
                                                builder1.setIcon(R.drawable.ic_check_black_24dp);
                                                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                        listWork();
                                                        dialog.dismiss();

                                                    }
                                                });

                                                builder1.show();

                                            }else if (check_capture.isChecked()){
                                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                                builder1.setTitle("Capture");
                                                builder1.setMessage(message);
                                                builder1.setIcon(R.drawable.ic_check_black_24dp);
                                                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                        listWork();
                                                        dialog.dismiss();

                                                    }
                                                });

                                                builder1.show();
                                            }
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

                                        refId = jsonObject.getString("PaymentReferenceID");
                                        JSONObject result = jsonObject.getJSONObject("Result");
                                        String isError = result.getString("IsError");
                                        String message = result.getString("Message");

                                        if (isError == "false") {
                                            if (check_refund.isChecked()) {

                                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                                builder1.setTitle("Refund");
                                                builder1.setMessage(message);
                                                builder1.setIcon(R.drawable.ic_check_black_24dp);
                                                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                        listWork();
                                                        dialog.dismiss();

                                                    }
                                                });

                                                builder1.show();

                                            } else if (check_void.isChecked()) {
                                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                                builder1.setTitle("Void");
                                                builder1.setMessage(message);
                                                builder1.setIcon(R.drawable.ic_check_black_24dp);
                                                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                        listWork();
                                                        dialog.dismiss();

                                                    }
                                                });

                                                builder1.show();
                                            }else if (check_capture.isChecked()){
                                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                                builder1.setTitle("Capture");
                                                builder1.setMessage(message);
                                                builder1.setIcon(R.drawable.ic_check_black_24dp);
                                                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                        listWork();
                                                        dialog.dismiss();

                                                    }
                                                });

                                                builder1.show();
                                            }
                                        }


                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                                if (value == 4) {
                                    String tamount = edit_amount.getText().toString();
                                    float m1 = Float.parseFloat(tamount);
                                    float m2 = Float.parseFloat(remAmount);
                                    totalAmount = (m2 - m1);
                                    try {
                                        jsonObject.put("PaymentReferenceID", refId);
                                        jsonObject.put("TenderTypeID", "1");
                                        jsonObject.put("Amount", tamount);
                                        JSONObject cardInfo = new JSONObject();
                                        cardInfo.put("CardHolderName", holderName);
                                        cardInfo.put("PaymentTypeID", value);
                                        cardInfo.put("CurrencyTypeID", 1);

                                        jsonObject.put("CardInformation", cardInfo);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else if (value == 3){
                                    try {
                                        jsonObject.put("PaymentReferenceID", refId);
                                        jsonObject.put("TenderTypeID", "1");
                                        jsonObject.put("Amount", remAmount);
                                        JSONObject cardInfo = new JSONObject();
                                        cardInfo.put("CardHolderName", holderName);
                                        cardInfo.put("PaymentTypeID", value);

                                        jsonObject.put("CardInformation", cardInfo);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    try {
                                        jsonObject.put("PaymentReferenceID", refId);
                                        jsonObject.put("TenderTypeID", "1");
                                        jsonObject.put("Amount", remAmount);
                                        JSONObject cardInfo = new JSONObject();
                                        cardInfo.put("CardHolderName", holderName);
                                        cardInfo.put("PaymentTypeID", value);

                                        jsonObject.put("CardInformation", cardInfo);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                return jsonObject.toString().getBytes();
                            }
                        };
                        objectRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        requestQueue.add(objectRequest);
                    }
                });
                dialog.dismiss();

                if (id == "sale") {
                    check_capture.setEnabled(false);
                    check_refund.setEnabled(true);
                    check_void.setEnabled(true);
                } else if (id == "auth") {
                    check_refund.setEnabled(false);
                    check_void.setEnabled(false);
                    check_capture.setEnabled(true);
                }

                dialog.show();
            }
        });
    }


    public void listWork() {

        if (check == false) {

        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(c.getTime());
            c.add(Calendar.DATE, -30);
            final Date dateFrom = c.getTime();
            Date dateTo = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            date_From = simpleDateFormat.format(Date.parse(dateFrom.toString()));
            date_to = simpleDateFormat.format(Date.parse(dateTo.toString()));

        }
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, void_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    arrayList = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject refId = response.getJSONObject(i);
                        String id = refId.getString("PaymentReferenceID");
                        String amount = refId.getString("Amount");
                        String remAmount = refId.getString("RemainingAmount");
                        String text_card_holder = "no name";
                        String value = "No Value";
                        JSONObject jsonObject = refId.getJSONObject("CardInformation");
                        int paymenyId = jsonObject.getInt("PaymentTypeID");
                        if (paymenyId == 1) {
                            value = "sale";

                            if (jsonObject.has("AccountName")) {
                                text_card_holder = jsonObject.getString("AccountName");
                            }
                            JSONObject resultObject = refId.getJSONObject("Result");
                            String status = resultObject.getString("Status");
                            String min = resultObject.getString("TransactionDate");
                            String last4digit = resultObject.getString("Last4Digit");
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
                            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                            long time = 0;
                            try {
                                time = sdf.parse(min).getTime();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            transaction = new LiveTransaction(amount, id, min, text_card_holder, value, last4digit, status, remAmount, cardNum, cvv, expMonth, expYear);
                            arrayList.add(transaction);


                            if (getActivity() != null) {

                                adapter = new LiveTransactionAdapter(getActivity(), R.layout.row, arrayList);
                                listView.setAdapter(adapter);
                            }

                        } else if (paymenyId == 2) {
                            value = "auth";


                            if (jsonObject.has("AccountName")) {
                                text_card_holder = jsonObject.getString("AccountName");
                            }
                            JSONObject resultObject = refId.getJSONObject("Result");
                            String status = resultObject.getString("Status");
                            String min = resultObject.getString("TransactionDate");
                            String last4digit = resultObject.getString("Last4Digit");
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
                            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                            long time = 0;
                            try {
                                time = sdf.parse(min).getTime();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            transaction = new LiveTransaction(amount, id, min, text_card_holder, value, last4digit, status, remAmount, cardNum, cvv, expMonth, expYear);
                            arrayList.add(transaction);


                            if (getActivity() != null) {

                                adapter = new LiveTransactionAdapter(getActivity(), R.layout.row, arrayList);
                                listView.setAdapter(adapter);
                            }

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AuthorizationKey", "A_testA6Z21bIRnibIjRw1JMZUSRjt6ePl48ptcLD9YCdIdKw=");
                params.put("TransactionKey", "T_testA6Z21bIRnibIjRw1JMZUSZAFVu5lq2miQXsBcvgwBaw=");
                params.put("Content-Type", "application/json");
                return params;
            }

            @Override
            public byte[] getBody() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DateFrom", date_From);
                    jsonObject.put("DateTo", date_to);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return jsonObject.toString().getBytes();
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }

}
