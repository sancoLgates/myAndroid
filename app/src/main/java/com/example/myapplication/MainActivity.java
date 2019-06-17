package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
//import android.widget.EditText;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button butt;
    TextView hello;

    public static final String EXTRA_MESSAGES = "com.example.myapplication.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        butt = findViewById(R.id.button);
        hello = findViewById(R.id.hello);

        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                butt.setText("button clicked");
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

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();

        //pake spasi doank gk bisa submit, ajib
        if(message.trim().equals("")){
//        masih bisa submit isi pake spasi doank
//        if(TextUtils.isEmpty(message)){
            editText.setHint("pesan kosong");
            editText.setError(getResources().getString(R.string.message_error));
//            Toast.makeText(this, "message is empty Bosssssssssssssssssssssssssssssssssssssssssssss", Toast.LENGTH_SHORT).show(); //2 second delay
            Toast.makeText(this, "message is empty Bosssssssssssssssssssssssssssssssssssssssssssss", Toast.LENGTH_LONG).show(); //3.5 second delay

            int s = 6000;
            Snackbar.make(view, "snackbar", Snackbar.LENGTH_LONG).setDuration(s).show();
        } else {
            intent.putExtra(EXTRA_MESSAGES, message);
//          intent.putExtra(EXTRA_MESSAGE, "test");
            startActivity(intent);
        }

    }
}
