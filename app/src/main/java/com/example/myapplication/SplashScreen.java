package com.example.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        // Splash Screen using activity 4 sec, new intent
//        setContentView(R.layout.activity_splash_screen);
//
//        int TIMEOUT = 4000;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent i = new Intent(SplashScreen.this, MainActivity.class);
//                startActivity(i);
//                finish();
//            }
//        }, TIMEOUT);
    }
}
