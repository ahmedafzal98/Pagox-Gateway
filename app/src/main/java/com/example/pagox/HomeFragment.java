package com.example.pagox;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import es.dmoral.toasty.Toasty;

public class HomeFragment extends Fragment {

    BarChart barChart;
    //BarData barData;
//    BarDataSet barDataSet;
//    SearchView searchView;
    String TotalApprovedTransactionAmt;
    String TotalVoidRefund;
    String TotalVoid;
    ListView listView;
    ArrayList<LiveTransaction> items = new ArrayList<>();
    LiveTransactionAdapter adapter;
    String dash_URL;
    String trans_URL;
    ArrayList<BarEntry> BARENTRY;
    ArrayList<String> BarEntryLabels;
    BarDataSet Bardataset;
    BarData BARDATA;
    String text_card_holder;
    Button button;
    LiveTransaction transaction;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        barChart = view.findViewById(R.id.bar_chart);
        listView = view.findViewById(R.id.list_view);
        button = view.findViewById(R.id.btn_refresh);
        progressBar = view.findViewById(R.id.progress_bar);
        dash_URL = "https://api.pagox.io/payment/dashboardapi";
        trans_URL = "https://api.pagox.io/payment/Txnlist";
        BARENTRY = new ArrayList<>();
        BarEntryLabels = new ArrayList<>();

        chartWork();
        listWork();

        //  searchView = view.findViewById(R.id.searvh_view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                chartWork();
                listWork();
                Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
    public void chartWork(){

        progressBar.setVisibility(View.VISIBLE);
        barChart.setVisibility(View.GONE);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, dash_URL, new JSONObject(), new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String TotalApprovedTransactionAmt = response.getString("TotalApprovedTransactionAmt");
                    String DeclinedAmount = response.getString("DeclinedAmount");
                    String TotalProfit = response.getString("TotalProfit");
                    String TotalVoidRefund = response.getString("TotalVoidRefund");

                    setValue(TotalApprovedTransactionAmt, TotalVoidRefund, TotalProfit, DeclinedAmount);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                barChart.setVisibility(View.VISIBLE);

                Toasty.error(getActivity(),"Internet Failure");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AuthorizationKey", "A_testA6Z21bIRnibIjRw1JMZUSRjt6ePl48ptcLD9YCdIdKw=");
                params.put("Content-Type", "application/json n");
                params.put("TransactionKey", "T_testA6Z21bIRnibIjRw1JMZUSZAFVu5lq2miQXsBcvgwBaw=");
                return params;
            }

        };
        progressBar.setVisibility(View.GONE);
        barChart.setVisibility(View.VISIBLE);
        requestQueue.add(objectRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setValue(String TotalApprovedTransactionAmt, String TotalVoidRefund, String TotalProfit, String DeclinedAmount) {


        barChart.getDescription().setEnabled(false);
        barChart.setExtraOffsets(5,10,5,5);
        barChart.invalidate();
        barChart.getLegend().setEnabled(false);

//        barChart.setDrawHoleEnabled(true);
//        barChart.setHoleColor(Color.WHITE);
//        barChart.setTransparentCircleRadius(61f);
//        barChart.setDrawEntryLabels(false);

        Legend legend = barChart.getLegend();
        legend.setEnabled(true);
        legend.setFormSize(10f);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1f, Float.parseFloat(TotalApprovedTransactionAmt)));
        entries.add(new BarEntry(2f, Float.parseFloat(DeclinedAmount)));
        entries.add(new BarEntry(3f, Float.parseFloat(TotalProfit)));
        entries.add(new BarEntry(4f, Float.parseFloat(TotalVoidRefund)));

        BarDataSet dataSet = new BarDataSet(entries,null);
//        dataSet.setSliceSpace(3f);
//        dataSet.setSelectionShift(5f);
        dataSet.setColors(new int[] {Color.GREEN,Color.RED,Color.BLUE,Color.YELLOW});

        YAxis left = barChart.getAxisLeft();
        YAxis right = barChart.getAxisRight();
        left.setValueFormatter(new LargeValueFormatter());
        right.setValueFormatter(new LargeValueFormatter());
        BarData barData = new BarData(dataSet);
        barData.setValueTextSize(10f);
        barData.setValueFormatter(new LargeValueFormatter());
        barData.setValueTextColor(Color.BLACK);
        barChart.animateY(2000);
        barChart.setData(barData);
        barChart.getLegend().setEnabled(false);
    }


    public void listWork() {

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, trans_URL, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<LiveTransaction> arrayList = new ArrayList<>();
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

                        transaction = new LiveTransaction(amount, id, min, text_card_holder, value, last4digit, status,remAmount,null,null,null,null);
                        arrayList.add(transaction);
                    }
                    if (getActivity()!= null){
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
                    jsonObject.put("DateFrom", "2020/06/28");
                    jsonObject.put("DateTo", "2020/08/21");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return jsonObject.toString().getBytes();
            }
        };
        requestQueue.add(request);
    }
}

