package com.example.pagox;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.google.android.material.datepicker.MaterialDatePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import es.dmoral.toasty.Toasty;

public class RecordsFragment extends Fragment {

    EditText edit_dateTo, edit_dateFrom;
    Button button;
    ProgressBar progressBar;
    String list_URL;
    LiveTransaction transaction;
    LiveTransactionAdapter adapter;
    ArrayList<LiveTransaction> arrayList;
    ListView listView;
    String date_From;
    String date_to;
    String dateTo;
    String refId;
    String records_URL;
    boolean check;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_records, container, false);
        edit_dateTo = view.findViewById(R.id.edit_dateTo);
        edit_dateFrom = view.findViewById(R.id.edit_dateFrom);
        progressBar = view.findViewById(R.id.progress);
        listView = view.findViewById(R.id.list_view);
        list_URL = "https://api.pagox.io/payment/Txnlist";
        records_URL = "https://api.pagox.io/payment/search";
        listView = view.findViewById(R.id.list_view);
        button = view.findViewById(R.id.btn_search);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        check = true;

        listWork();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_dateFrom.length() > 0){
                    if (edit_dateFrom.length() > 0){
                        date_From = edit_dateTo.getText().toString();
                        date_to = edit_dateFrom.getText().toString();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        date_From = simpleDateFormat.format(Date.parse(date_From.toString()));
                        date_to = simpleDateFormat.format(Date.parse(date_to.toString()));
                        check = false;

                        listWork();
                    }else {
                        edit_dateTo.setError("Please fill this field");
                    }
                }else {
                    edit_dateFrom.setError("Please fill this field");
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

        onitemclick();

        return view;
    }

    public void listWork() {
        progressBar.setVisibility(View.VISIBLE);
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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, list_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    arrayList = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject refId = response.getJSONObject(i);
                        String id = refId.getString("PaymentReferenceID");
                        String amount = refId.getString("Amount");
                        String text_card_holder = "no name";
                        String value = "No Value";
                        JSONObject jsonObject = refId.getJSONObject("CardInformation");
                        int paymenyId = jsonObject.getInt("PaymentTypeID");
                        if (paymenyId == 1) {
                            value = "sale";
                        } else if (paymenyId == 2) {
                            value = "Auth";
                        } else if (paymenyId == 3) {
                            value = "Capture";
                        } else if (paymenyId == 4) {
                            value = "Refund";
                        } else if (paymenyId == 5) {
                            value = "Void";
                        }
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

                        transaction = new LiveTransaction(amount, id, min, text_card_holder, value, last4digit, status, null, null, null, null, null);
                        arrayList.add(transaction);

                    }

                    if (getActivity() != null) {

                        adapter = new LiveTransactionAdapter(getActivity(), R.layout.row, arrayList);
                        listView.setAdapter(adapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(getActivity(), "Internet Failure", Toast.LENGTH_SHORT).show();
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
                    jsonObject.put("DateFrom",date_From);
                    jsonObject.put("DateTo", date_to);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);
                return jsonObject.toString().getBytes();
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    public void onitemclick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final String id = arrayList.get(position).getRef_id();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Transaction Record")
                        .setMessage("Do You want to see more details about this transaction ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, records_URL, null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        try {
                                            int typeId = response.getInt("TenderTypeID");
                                            String value = null;
                                            if (typeId == 1) {
                                                value = "sale";
                                            } else if (typeId == 2) {
                                                value = "Auth";
                                            } else if (typeId == 3) {
                                                value = "Capture";
                                            } else if (typeId == 4) {
                                                value = "Refund";
                                            } else if (typeId == 5) {
                                                value = "Void";
                                            }
                                            String amount = response.getString("Amount");
                                            String remAmount = response.getString("RemainingAmount");
                                            String processId = response.getString("ProcessorID");
                                            JSONObject result = response.getJSONObject("Result");
                                            String last4digit = result.getString("Last4Digit");
                                            String status = result.getString("Status");
                                            String message = result.getString("Message");
                                            String authCode = result.getString("AuthCode");
                                            JSONObject cardInfo = response.getJSONObject("CardInformation");
                                            int payId = cardInfo.getInt("PaymentTypeID");
                                            String value2 = null;
                                            if (payId == 1) {
                                                value2 = "sale";
                                            } else if (payId == 2) {
                                                value2 = "Auth";
                                            } else if (payId == 3) {
                                                value2 = "Capture";
                                            } else if (payId == 4) {
                                                value2 = "Refund";
                                            } else if (payId == 5) {
                                                value2 = "Void";
                                            }
                                            String accId = cardInfo.getString("AccountTypeID");
                                            String currId = cardInfo.getString("CurrencyTypeID");
                                            String expMonth = cardInfo.getString("ExpiryMonth");
                                            String expyear = cardInfo.getString("ExpiryYear");
                                            String isProfileSave = cardInfo.getString("IsProfileSave");
                                            String accNum = cardInfo.getString("AccountNumber");
                                            String rouNo = cardInfo.getString("RoutingNo");

                                            Intent intent = new Intent(getActivity(), TransactionDetails.class);
                                            intent.putExtra("TenderTypeID", value);
                                            intent.putExtra("Amount", amount);
                                            intent.putExtra("RemainingAmount", remAmount);
                                            intent.putExtra("ProcessorID", processId);
                                            intent.putExtra("Last4Digit", last4digit);
                                            intent.putExtra("Status", status);
                                            intent.putExtra("Message", message);
                                            intent.putExtra("AuthCode", authCode);
                                            intent.putExtra("PaymentTypeID", value2);
                                            intent.putExtra("AccountTypeID", accId);
                                            intent.putExtra("CurrencyTypeID", currId);
                                            intent.putExtra("ExpiryMonth", expMonth);
                                            intent.putExtra("ExpiryYear", expyear);
                                            intent.putExtra("IsProfileSave", isProfileSave);
                                            intent.putExtra("AccountNumber", accNum);
//                            intent.putExtra("RoutingNo",rouNo);
                                            startActivity(intent);
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
                                            jsonObject.put("PaymentReferenceID", id);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        return jsonObject.toString().getBytes();
                                    }
                                };

                                requestQueue.add(objectRequest);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.show();

            }
        });
    }

}
