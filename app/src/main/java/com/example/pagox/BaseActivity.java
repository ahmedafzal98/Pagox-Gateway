package com.example.pagox;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;

import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class BaseActivity extends AppCompatActivity {

    ListView listView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Button btn_card, btn_profile, btn_pay_link, btn_voidCapture;
    SearchView searchView;
    ArrayList<LiveTransaction> items = new ArrayList<>();
    LiveTransactionAdapter adapter;
    Toolbar toolbar;
    BottomNavigationView navigationView;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        navigationView = findViewById(R.id.bott_nav);
        getWindow().setStatusBarColor(Color.LTGRAY);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.payment:
                        fragment = new PaymentFragment();
                        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
                        View view = getLayoutInflater().inflate(R.layout.alert_dialog, null);
                        btn_card = view.findViewById(R.id.btn_credit);
                        btn_profile = view.findViewById(R.id.btn_profile);
                        btn_pay_link = view.findViewById(R.id.btn_pay_link);
                        btn_voidCapture = view.findViewById(R.id.btn_voiCapture);
                        builder.setView(view);

                        final AlertDialog dialog = builder.create();
                        dialog.setCanceledOnTouchOutside(true);
                        btn_card.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, new CreditcardFragment()).commit();
                                dialog.dismiss();
                            }
                        });
                        btn_pay_link.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, new payLinkFragment()).commit();
                                dialog.dismiss();
                            }
                        });
                        btn_profile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                getSupportFragmentManager().beginTransaction().replace(R.id.container, new CardProfileFragment()).commit();
                                dialog.dismiss();

                            }
                        });
                        btn_voidCapture.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, new VoidCaptureFragment()).commit();
                                dialog.dismiss();
                            }
                        });
                        dialog.show();


                        break;
                    case R.id.signout:
                       AlertDialog.Builder builder1 = new AlertDialog.Builder(BaseActivity.this);
                       builder1.setTitle("SignOut");
                       builder1.setMessage("Are You Sure You want to SignOut ?");
                       builder1.setNegativeButton("No",null);
                       builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                               startActivity(intent);
                           }
                       });
                       builder1.show();
                        break;
                    case R.id.Home:
                        fragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    case R.id.record:
                        fragment = new RecordsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

                }
//
                return false;
            }
        });


//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                BaseActivity.this.adapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                BaseActivity.this.adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

//                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    public void listViewWork() {
//        LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
//
////        View footerView = inflater.inflate(R.layout.footer_view, null, false);
////        listView.addFooterView(footerView);//Add view to list view as footer view
//
//
//        LiveTransaction transaction1 = new LiveTransaction("USD 500.00", "Payment ref ID:" + "58102", "1918 minutes ago");
//        LiveTransaction transaction2 = new LiveTransaction("USD 600.00", "Payment ref ID:" + "48102", "2918 minutes ago");
//        LiveTransaction transaction3 = new LiveTransaction("USD 700.00", "Payment ref ID:" + "38102", "3918 minutes ago");
//        LiveTransaction transaction4 = new LiveTransaction("USD 200.00", "Payment ref ID:" + "28102", "3918 minutes ago");
//        LiveTransaction transaction5 = new LiveTransaction("USD 300.00", "Payment ref ID:" + "18102", "4918 minutes ago");
//
//        items.add(transaction1);
//        items.add(transaction2);
//        items.add(transaction3);
//        items.add(transaction4);
//
//        adapter = new LiveTransactionAdapter(this, R.layout.row, items);
//        listView.setAdapter(adapter);
}



