package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button butt;
    TextView hello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        butt = findViewById(R.id.button);
        hello = findViewById(R.id.hello);

        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                butt.setText("Butten");
// browser url redirect
//                Intent browserIntent = new Intent(
//                        Intent.ACTION_VIEW,
//                        Uri.parse("http://android.okhelp.cz/category/software/"));
//                startActivity(browserIntent);

//                webview
                WebView webView = (WebView) findViewById(R.id.webview);
//                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setBlockNetworkLoads (false);
                webView.loadUrl("http://android.okhelp.cz/category/software/");


            }
        });
    }
}
