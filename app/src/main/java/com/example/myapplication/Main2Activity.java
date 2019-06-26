package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import static android.content.ContentValues.TAG;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "FCM Service";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        Log.d(TAG, "danutest");

        sendNotification("Do you want to go to bandung tomorrow ?");

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
//
//                        // Log and toast
                        String msg = getString(R.string.fcm_token, token);
                        Log.d(TAG, msg);
                        Toast.makeText(Main2Activity.this, msg, Toast.LENGTH_LONG).show();
                    }
                });

        Log.d(TAG, "danz"+Build.VERSION.SDK_INT+Build.VERSION_CODES.O);

        if (getIntent().getExtras() != null) {
            Log.d(TAG, "danutestsssssssssssssssss");

            // Call your NotificationActivity here..
//            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
//            startActivity(intent);
        }
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, Main2Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.channelid);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_tri);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_sanz)
                        .setLargeIcon(bitmap)
                        .setContentTitle("Hi Kama")
                        .setContentText(messageBody)
//                        .setStyle(new NotificationCompat.BigTextStyle()
//                                .bigText("Much longer text that cannot fit one line..."))
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitmap)
                                .bigLargeIcon(null))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setLights(Color.BLUE,1,1)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}