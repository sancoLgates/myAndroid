package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String message = ((Intent) intent).getStringExtra(MainActivity.EXTRA_MESSAGES);

        TextView textView = findViewById(R.id.textView);
        textView.setText(getResources().getString(R.string.yourMessage) + ' ' + message);
    }
}
