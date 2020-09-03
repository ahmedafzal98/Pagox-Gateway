package com.example.pagox;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class CardProfileFragment extends Fragment implements TextWatcher {

    ArrayList<CardProfile> items = new ArrayList<>();
    CardProfileAdapter profileAdapter;
    ListView listView;
    EditText edit_amount, search_view;
    CheckBox check_sale, check_auth;
    String profile_URL;
    Button btn_refresh;
    TextView textView;
    String delete_URL;
    String payment_URL;
    ArrayList<CardProfile> arrayList, arrayFilter;
    CardProfile cardProfile;
    int profileId;
    String tenderType;
    int value = 1;
    String editAmount;
    AlertDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_profile, container, false);
        listView = view.findViewById(R.id.lisview);
        search_view = view.findViewById(R.id.search_view);
        textView = view.findViewById(R.id.txt_total_records);
        btn_refresh = view.findViewById(R.id.btn_refresh_profile);
        search_view.addTextChangedListener(this);
        delete_URL = "https://api.pagox.io/Payment/DeleteProfile";
        profile_URL = "https://api.pagox.io/Payment/GetAllProfile";
        payment_URL = "https://api.pagox.io/payment/";

        itemClick();
        listviewWork();

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listviewWork();
                Toasty.normal(getActivity(),"Refresh");
            }
        });
        return view;
    }

    public void paymentWork(final int position) {

    }

    public void deletework(final int position) {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, delete_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    if (jsonObject.has("IsError")) {
                        String isError = jsonObject.getString("IsError");
                        final String message = jsonObject.getString("Message");
                        if (isError == "false") {
                            arrayList.remove(position);
                            profileAdapter.notifyDataSetChanged();
                            listviewWork();
                            Toasty.normal(getActivity(), message, Toast.LENGTH_SHORT).show();
                            int size = arrayList.size();
                            textView.setText("Total Records " + size);
                        } else {


                        }


                    } else {
                        JSONObject result = jsonObject.getJSONObject("Result");
                        String isError = result.getString("IsError");
                        final String message = result.getString("Status");
                        if (isError == "false") {
                            arrayList.remove(position);
                            profileAdapter.notifyDataSetChanged();
                            listviewWork();
                            Toasty.normal(getActivity(), message, Toast.LENGTH_SHORT).show();
                            int size = arrayList.size();
                            textView.setText("Total Records " + size);
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
                    jsonObject.put("ProfileID", profileId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return jsonObject.toString().getBytes();
            }
        };
        requestQueue.add(request);

    }

    public void itemClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                CharSequence[] dialogItem = {"Delete Profile", "Payment"};
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:

                                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Delete Profile");
                                builder.setMessage("Are you sure you want to delete ?");
                                builder.setIcon(R.drawable.ic_delete_black_24dp);
                                builder.setNegativeButton("No", null);
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        deletework(position);
                                    }
                                });
                                builder.show();


                                break;


                            case 1:
                                final AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                View view2 = getLayoutInflater().inflate(R.layout.alert_card_profile_payment, null);
                                edit_amount = view2.findViewById(R.id.edit_amount_profile);
                                check_sale = view2.findViewById(R.id.check_sale_p);
                                check_auth = view2.findViewById(R.id.check_auth_p);
                                Button btn_process = view2.findViewById(R.id.btn_process);
                                Button btn_cancel = view2.findViewById(R.id.btn_cancel);
                                builder1.setView(view2);

                                check_sale.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (check_sale.isChecked()) {
                                            check_auth.setChecked(false);
                                            value = 1;
                                        } else if (check_auth.isChecked()) {
                                            check_sale.setChecked(false);
                                            value = 2;
                                        }
                                    }
                                });
                                check_auth.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        if (check_auth.isChecked()) {
                                            check_sale.setChecked(false);
                                            value = 2;
                                        } else if (check_sale.isChecked()) {
                                            check_auth.setChecked(false);
                                            value = 1;
                                        }
                                    }
                                });

                                final AlertDialog dialog = builder1.create();
                                dialog.setCanceledOnTouchOutside(true);


                                btn_process.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                                        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, payment_URL, null, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {

                                                try {
                                                    JSONObject jsonObject = new JSONObject(response.toString());
                                                    if (jsonObject.has("IsError")) {
                                                        String isError = jsonObject.getString("IsError");
                                                        String message = jsonObject.getString("Message");
                                                        if (isError == "true") {
                                                            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                            builder.setTitle("Declined");
                                                            builder.setMessage(message);
                                                            builder.setIcon(R.drawable.ic_error_outline_black_24dp);
                                                            builder.show();
                                                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                                    listviewWork();
                                                                    dialog.dismiss();

                                                                }
                                                            });

                                                            builder.show();
                                                            dialog.dismiss();
                                                        }

                                                    } else {

                                                        JSONObject result = jsonObject.getJSONObject("Result");
                                                        String isError = result.getString("IsError");
                                                        String Status = result.getString("Status");
                                                        String AuthCode = result.getString("AuthCode");


                                                        if (isError == "false") {
                                                            if (check_auth.isChecked()) {
                                                                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                                builder.setTitle(Status);
                                                                builder.setMessage(AuthCode);
                                                                builder.setIcon(R.drawable.ic_check_black_24dp);
                                                                builder.show();
                                                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                                        listviewWork();
                                                                        dialog.dismiss();

                                                                    }
                                                                });
                                                                builder.show();
                                                                dialog.dismiss();

                                                            } else {
                                                                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                                builder.setTitle(Status);
                                                                builder.setMessage(AuthCode);
                                                                builder.setIcon(R.drawable.ic_check_black_24dp);
                                                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                                        listviewWork();
                                                                        dialog.dismiss();

                                                                    }
                                                                });
                                                                builder.show();
                                                                dialog.dismiss();
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
                                                int profileId = arrayList.get(position).getProfileId();
                                                tenderType = arrayList.get(position).getTenderType();
                                                editAmount = edit_amount.getText().toString();

                                                JSONObject jsonObject = new JSONObject();
                                                try {
                                                    jsonObject.put("TenderTypeID", tenderType);
                                                    jsonObject.put("Amount", editAmount);

                                                    JSONObject cardInfo = new JSONObject();

                                                    cardInfo.put("IsProfileSave", true);
                                                    cardInfo.put("PaymentTypeID", value);
                                                    cardInfo.put("ProfileID", profileId);

                                                    jsonObject.put("CardInformation", cardInfo);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                return jsonObject.toString().getBytes();

                                            }
                                        };
                                        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                        requestQueue.add(request);
                                    }
                                });

                                btn_cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        listviewWork();
                                        dialog.dismiss();
                                    }
                                });

                                builder1.show();
                                dialog.dismiss();


                                break;

                        }
                    }
                });
                builder.create().show();


            }
        });
    }

    public void listviewWork() {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, profile_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                arrayList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String tenId = jsonObject.getString("TenderTypeID");
                        JSONObject cardInfo = jsonObject.getJSONObject("CardInformation");
                        String holderName = cardInfo.getString("CardHolderName");
                        int payId = cardInfo.getInt("PaymentTypeID");
                        String cardNum = cardInfo.getString("CardNumber");
                        profileId = cardInfo.getInt("ProfileID");
                        String date = cardInfo.getString("Custom");
                        String value = "No Value";
                        if (payId == 1) {
                            value = "sale";
                        } else if (payId == 2) {
                            value = "Auth";
                        } else if (payId == 3) {
                            value = "Capture";
                        } else if (payId == 4) {
                            value = "Refund";
                        } else if (payId == 5) {
                            value = "Void";
                        }

                        cardProfile = new CardProfile(profileId, holderName, cardNum, tenId, value, date);
                        arrayList.add(cardProfile);


                        profileAdapter = new CardProfileAdapter(getActivity(), R.layout.row, arrayList);
                        listView.setAdapter(profileAdapter);
                        int size = arrayList.size();
                        textView.setText("Total Records " + size);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                    jsonObject.put("IsAllProfile", true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return jsonObject.toString().getBytes();
            }
        };

        requestQueue.add(request);


    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        profileAdapter.getFilter().filter(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

}
