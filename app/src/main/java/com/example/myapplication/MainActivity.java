package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button butt, login, close, logout, main2, phone, recycler;
    ImageButton tricrud;
    TextView hello, total, userdata;
    EditText username, password;

    int counter = 3;

    public static final String EXTRA_MESSAGES = "com.example.myapplication.MESSAGE";

    private Toolbar mTopToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SET TOP BAR
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        main2 = findViewById(R.id.main2);
        phone = findViewById(R.id.phonereg);
        tricrud = findViewById(R.id.tricrud);
        recycler = findViewById(R.id.recycler);

        main2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, phoneReg.class);
                startActivity(intent);
            }
        });

        tricrud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FirebasecrudActivity.class);
                startActivity(intent);
            }
        });

        recycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecyclerActivity.class);
                startActivity(intent);
            }
        });

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

//        LOGIN
//        login = (Button)findViewById(R.id.login);
        close = (Button)findViewById(R.id.close);
        logout = (Button)findViewById(R.id.logout);
//        username = (EditText)findViewById(R.id.username);
//        password = (EditText)findViewById(R.id.password);
//        total = (TextView)findViewById(R.id.attempt);
//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
//                    Toast.makeText(getApplicationContext(), "Redirecting ...", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
//
//                    total.setVisibility(View.VISIBLE);
////                    total.setBackgroundColor(Color.RED);
//                    counter--;
//                    total.setText(String.format("%s%s", getResources().getString(R.string.attemptleft), Integer.toString(counter)));
//
//                    if(counter == 0) {
//                        login.setEnabled(false);
//                    }
//                }
//            }
//        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

//        Firebase access user info
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userdata = (TextView)findViewById(R.id.userdata);
        if(user != null) {

            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photo = user.getPhotoUrl();
            String uid = user.getUid();

//            check if user mail is verified
            boolean emailVerified = user.isEmailVerified();
            userdata.setText(" email:"+email+" uid:"+uid+emailVerified);
            logout.setVisibility(View.VISIBLE);
        }

//        logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                userdata.setText("Login lah...");
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
            editText.setHint(getResources().getString(R.string.message_kosong));
            editText.setError(getResources().getString(R.string.message_error));
//            Toast.makeText(this, "message is empty Bosssssssssssssssssssssssssssssssssssssssssssss", Toast.LENGTH_SHORT).show(); //2 second delay
            Toast.makeText(this, getResources().getString(R.string.message_error), Toast.LENGTH_LONG).show(); //3.5 second delay

            int s = 6000;
            Snackbar.make(view, getResources().getString(R.string.message_kosong), Snackbar.LENGTH_LONG).setDuration(s).show();
        } else {
            intent.putExtra(EXTRA_MESSAGES, message);
//          intent.putExtra(EXTRA_MESSAGE, "test");
            startActivity(intent);
        }

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                // User chose the "Settings" item, show the app settings UI...
//                return true;
//
//            case R.id.action_favorite:
//                // User chose the "Favorite" action, mark the current item
//                // as a favorite...
//                return true;
//
//            default:
//                // If we got here, the user's action was not recognized.
//                // Invoke the superclass to handle it.
//                return super.onOptionsItemSelected(item);
//
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.topbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
