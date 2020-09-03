package com.example.pagox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class summary extends AppCompatActivity {

    EditText edit_credit, edit_holderName, edit_cvv, edit_exp_date, edit_amount, edit_month;
    Button button;
    ProgressBar progressBar;
    String message, credit, holderName, cvv, expMonth, expYear, amount ,value, zip, credit_URL;
    boolean isProfile;
    JsonObjectRequest objectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        edit_credit = findViewById(R.id.edit_credit);
        edit_holderName = findViewById(R.id.edit_holder_name);
        edit_cvv = findViewById(R.id.edit_cvv);
        edit_exp_date = findViewById(R.id.edit_expi_date);
        edit_month = findViewById(R.id.edit_expi_month);
        edit_amount = findViewById(R.id.edit_amount);
        credit_URL = "https://api.pagox.io/payment";
        progressBar = findViewById(R.id.prog_bard);
        button = findViewById(R.id.btn_transaction);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnWork();
            }
        });


        Intent intent = getIntent();
        credit = intent.getStringExtra("credit");
        holderName = intent.getStringExtra("holderName");
        cvv = intent.getStringExtra("cvv");
        expYear = intent.getStringExtra("date");
        expMonth = intent.getStringExtra("expiMonth");
        amount = intent.getStringExtra("amount");
        value = intent.getStringExtra("value");
        message = intent.getStringExtra("message");
        isProfile = intent.getBooleanExtra("isProfile",false);

        edit_credit.setText(credit);
        edit_holderName.setText(holderName);
        edit_cvv.setText(cvv);
        edit_amount.setText(amount);
        edit_exp_date.setText(expYear);
        edit_month.setText(expMonth);

    }

    public void btnWork() {
        progressBar.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);
        RequestQueue requestQueue = Volley.newRequestQueue(summary.this);
        objectRequest = new JsonObjectRequest(Request.Method.POST, credit_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    objectRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    JSONObject jsonObject = new JSONObject(response.toString());
                    if (jsonObject.has("IsError")) {
                        String isError = jsonObject.getString("IsError");
                        String message = jsonObject.getString("Message");
                        String authCode = jsonObject.getString("AuthCode");
                        if (isError == "false") {
                            AlertDialog.Builder builder = new AlertDialog.Builder(summary.this);
                            builder.setTitle("Transaction Approved");
                            builder.setMessage(message);
                            builder.setMessage(authCode);
                            builder.setIcon(R.drawable.ic_check_black_24dp);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(summary.this, BaseActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });

                            builder.show();

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(summary.this);
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
                        String authCode = result.getString("AuthCode");
                        if (isError == "false") {
                            AlertDialog.Builder builder = new AlertDialog.Builder(summary.this);
                            builder.setTitle("Transaction Approved");
                            builder.setMessage(message);
                            builder.setMessage(authCode);
                            builder.setIcon(R.drawable.ic_check_black_24dp);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(summary.this, BaseActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            builder.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(summary.this);
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
                button.setVisibility(View.VISIBLE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);

                Toast.makeText(summary.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    cardInfo.put("ExpiryYear", expYear);
                    cardInfo.put("IsProfileSave", isProfile);

                    jsonObject.put("CardInformation", cardInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return jsonObject.toString().getBytes();
            }
        };
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(objectRequest);
    }
}
