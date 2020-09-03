package com.example.pagox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    TextView textView;
    ImageView imageView,footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView = (ImageView) findViewById(R.id.splash);
        TextView textView1 = (TextView) findViewById(R.id.text_footerText1);
        TextView textView2 = (TextView) findViewById(R.id.text_footerText2);
        TextView textView3 = (TextView) findViewById(R.id.text_footerText3);

        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(SplashScreen.this , spashscreen2.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();

        Animation animation = null;
        animation = AnimationUtils.loadAnimation(this,R.anim.splashanimation);
        imageView.startAnimation(animation);
    }
}
