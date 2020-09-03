package com.example.pagox;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    TextView email, password, txt_url, codeitech_url;
    EditText edit_email, edit_password;
    ImageView imageView;
    Button button_submit;
    ProgressBar progressBar;
    String Login_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_password = (EditText) findViewById(R.id.edit_pass);
        button_submit = (Button) findViewById(R.id.btn_submit);
        email = (TextView) findViewById(R.id.txt_email);
        password = (TextView) findViewById(R.id.txt_pass);
        txt_url = (TextView) findViewById(R.id.txt_pagox_url);
        progressBar = findViewById(R.id.prog_bar);
        imageView = (ImageView) findViewById(R.id.logo);
        Login_URL = "https://api.pagox.io/payment/loginapi";

        loginWork();
        editTextWork();
        redirectToPagoxSite();
    }

    public void redirectToPagoxSite() {
        txt_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://pagox.io/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://pagox.io/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
    }

    public void editTextWork() {
        edit_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    email.setTextColor(getResources().getColor(R.color.tint));
                    edit_email.getBackground().mutate().setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tint), PorterDuff.Mode.SRC_ATOP);

                } else {
                    email.setTextColor(getResources().getColor(R.color.tint2));
                    edit_email.getBackground().mutate().setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tint2), PorterDuff.Mode.SRC_ATOP);

                }

            }
        });

        edit_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    password.setTextColor(getResources().getColor(R.color.tint));
                    edit_password.getBackground().mutate().setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tint), PorterDuff.Mode.SRC_ATOP);

                } else {
                    password.setTextColor(getResources().getColor(R.color.tint2));
                    edit_password.getBackground().mutate().setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.tint2), PorterDuff.Mode.SRC_ATOP);
                }
            }
        });
    }

    public void loginWork() {
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edit_email.getText().toString();
                String password = edit_password.getText().toString();

                if (!email.isEmpty()||!password.isEmpty()){
                    login(email,password);
                }else {
                    edit_email.setError("Please insert email");
                    edit_password.setError("Please insert password");
                }

            }
        });
    }
    public void login(final String email, final String password){


        progressBar.setVisibility(View.VISIBLE);
        button_submit.setVisibility(View.GONE);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        StringRequest request = new StringRequest(Request.Method.POST, Login_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String isError = jsonObject.getString("IsError");
                    if(isError == "false"){
                        Toasty.success(MainActivity.this,"You Are successfully Logged In",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,BaseActivity.class);
                        startActivity(intent);
                        finish();

                    }else {
                        Toasty.error(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressBar.setVisibility(View.GONE);
                button_submit.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                button_submit.setVisibility(View.VISIBLE);
                Toasty.error(MainActivity.this,"Internet Failure",Toast.LENGTH_SHORT).show();
            }
        })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String , String> params = new HashMap<String, String>();
                params.put("AuthorizationKey","A_testA6Z21bIRnibIjRw1JMZUSRjt6ePl48ptcLD9YCdIdKw=");
                params.put("TransactionKey","T_testA6Z21bIRnibIjRw1JMZUSZAFVu5lq2miQXsBcvgwBaw=");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userName", email);
                params.put("password", password);
                return params;
            }
        };
        requestQueue.add(request);
    }
}
