package com.example.pagox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class spashscreen2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spashscreen2);

        TextView textView1 = findViewById(R.id.text_footerText1);
        TextView textView2 = findViewById(R.id.text_footerText2);
        TextView textView3 = findViewById(R.id.text_footerText3);

        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(spashscreen2.this , MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();

        Animation animation = null;
        animation = AnimationUtils.loadAnimation(this,R.anim.splashanimation);

        textView1.startAnimation(animation);
        textView2.startAnimation(animation);
        textView3.startAnimation(animation);
    }
}
