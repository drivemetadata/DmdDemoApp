package com.drivemetadataintegrations;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle the notification message here

    }

    @Override
    public void onNewToken(String token) {
        // Handle new token (send it to your backend server)
        super.onNewToken(token);
        Log.e("Token",token);

        // Send this token to your server or save it in SharedPreferences
    }
}