package com.drivemetadataintegrations;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.impl.model.Preference;
import androidx.work.impl.utils.PreferenceUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.drivemetadata.DriveMetaData;
import com.drivemetadata.callbacks.DeepLinkCallBack;
import com.drivemetadata.callbacks.DriveMetaDataCallbacks;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdValue;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdapterResponseInfo;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.ads.admanager.AppEventListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.mediation.MediationBannerAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.Utils;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public class MainActivity  extends  AppCompatActivity implements View.OnClickListener, DriveMetaDataCallbacks {
    Button btn_add_to_cart,btn_checkout,updateToCart;
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1;


    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);
        updateToCart=findViewById(R.id.upDateCart);
        updateToCart.setOnClickListener(this);

        btn_add_to_cart.setOnClickListener(this);

        btn_checkout = findViewById(R.id.btn_checkout);
        EditText metaReferrer = findViewById(R.id.metaReferrer);
        EditText googleReferrer = findViewById(R.id.googleReferrer);
        EditText instReferrer = findViewById(R.id.instarReferrer);
        btn_checkout.setOnClickListener(this);
        tokenGeneration(this);

        DriveMetaData.with(this).setDriveMetaDataCallbacks(this::onResponse);
       // DriveMetaData.enableDebugLog(this,true);
//        Log.e("DMD",DriveMetaData.getAnonymousId(this));// DriveMetaData.getAnonymousId(this);
//        Log.e("DMD",DriveMetaData.getDeviceId(this));
       Log.e("DMD Advertisement ID ",DriveMetaData.getAdvertisementId(this));
//        Log.e("DMD",DriveMetaData.getDeviceDetails(this));

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.e("AdMod", "" + initializationStatus);
            }
        });
        AdView  mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // add paid event listener


        /* Handle the deeplink via drivemetadata */
       // String data = DriveMetaData.handleDeepLink(this,uri); // deeplink method
       // Log.e("Data",uri.toString());
        //

// fetching the background  data from sdk
        Uri uri = this.getIntent().getData();
        DriveMetaData.getBackgroundData(MainActivity.this,uri,new DeepLinkCallBack() {
            @Override
            public void onResponse(String response) {
                if(response!=null && response!="")
                {
                     Intent intent = new Intent(MainActivity.this,DeepLinkActivity.class);
                     intent.putExtra("deepLinkData",response);
                     startActivity(intent);
                }

            }

            @Override
            public void onError(Exception e) {
                // Handle errors
                Log.d("API Response exception", e.toString()); //getting response
            }
        });
        String googleRefererData = DriveMetaData.getGoogleInstallReferrer(this);


        String metaRefererData = DriveMetaData.getMetaInstallReferrer(this);
        String instRefererData  = DriveMetaData.getInstagramInstallReferrer(this);
        googleReferrer.setText(" : "+ googleRefererData);
        metaReferrer.setText(" : "+ metaRefererData);
        instReferrer.setText(" : " + instRefererData);

     // DriveMetaData.getDeviceDetails(this);

    }
    private void tokenGeneration(Context context) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult();
                       storeTokenLogic(context,token);
                    } else {
                        Log.e("FCM Token", "Failed to get token", task.getException());
                    }
                });
    }

//    private void storeTokenLogic(Context context, String token) {
//        // Get SharedPreferences to retrieve the current token
//        SharedPreferences sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
//        String storedToken = sharedPreferences.getString("device_token", "");
//
//        // Check if the token has changed
//        if (storedToken == "" || !storedToken.equals(token)) {
//            // Token has changed, update it in SharedPreferences
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("device_token", token);
//            editor.apply();
//
//            // Create the JSON structure to send the new token to the API
//            JSONObject deviceToken = new JSONObject();
//            JSONObject userIdentifier = new JSONObject();
//            JSONObject deviceTokenObject = new JSONObject();
//            try {
//                deviceToken.put("device_token", token);
//                userIdentifier.put("customer_id", "9789");  // Replace with actual user data
//                deviceTokenObject.put("deviceNotificationToken", deviceToken);
//                deviceTokenObject.put("userIdentifier", userIdentifier);
//                // Send the token update via API call (or any other service you're using)
//                DriveMetaData.with(MainActivity.this).sendTags(deviceTokenObject.toString(), "deviceToken");
//                Log.e("Device Token ",token);
//            } catch (JSONException e) {
//                Log.e("Exception",e.toString());
//            }
//        } else {
//            Log.e("Device Token ",token);
//
//            // Token is the same, no update needed
//            Log.d("TokenCheck", "Token is already up-to-date.");
//        }
//    }

    private void storeTokenLogic(Context context, String token) {
        // For devices with Android 13 or higher, check notification permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                // If permission is not granted, request it
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_REQUEST_CODE);
            } else {
                // If permission is granted, proceed with storing the token
                handleTokenUpdate(context, token);
            }
        } else {
            // If the device is below Android 13, no need for specific permission check
            handleTokenUpdate(context, token);
        }
    }

    private void handleTokenUpdate(Context context, String token) {
        // Get SharedPreferences to retrieve the current token
        SharedPreferences sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String storedToken = sharedPreferences.getString("device_token", "");

        // Check if the token has changed
        if (storedToken == null || !storedToken.equals(token)) {
            // Token has changed, update it in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("device_token", token);
            editor.apply();

            // Create the JSON structure to send the new token to the API
            JSONObject deviceToken = new JSONObject();
            JSONObject userIdentifier = new JSONObject();
            JSONObject deviceTokenObject = new JSONObject();
            try {
                deviceToken.put("device_token", token);
                userIdentifier.put("mobile", "7905717240"); // Replace with actual user data
                userIdentifier.put("customer_id", "9789");  // Replace with actual user data
                deviceTokenObject.put("deviceNotificationToken", deviceToken);
                deviceTokenObject.put("userIdentifier", userIdentifier);

                // Send the token update via API call (or any other service you're using)
                DriveMetaData.with(MainActivity.this).sendTags(deviceTokenObject.toString(), "deviceToken");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            // Token is the same, no update needed
            Log.d("TokenCheck", "Token is already up-to-date."+token);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {

            case R.id.btn_add_to_cart:
                try {
                    Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_SHORT).show();
                    JSONObject userDetails = new JSONObject();
                    JSONObject userObject =  new JSONObject();
                    userDetails.put("first_name", "Amit");
                    userDetails.put("last_name", "Gupta");
                    userDetails.put("age", "21");
                    userDetails.put("gender", "Male");
                    userDetails.put("dob", "24-04-1995");
                    userDetails.put("country", "India");
                    userDetails.put("zip", "110096");
                    userDetails.put("city", "Delhi");
                    userDetails.put("state", "Delhi");
                    userDetails.put("address", "A-412 Noida");
                    userDetails.put("mobile", "454545454455");
                    userDetails.put("mobile2", "45455t565656656");
                    userObject.put("userDetails",userDetails );
                    DriveMetaData.with(this).sendTags(userObject.toString(),"addCart");
                } catch (JSONException e) {
                    Log.v("Exception",e.toString());
                }
                break;
            case R.id.upDateCart:
                try {
                    JSONObject userDetails = new JSONObject();
                    JSONObject userObject =  new JSONObject();
                    userDetails.put("first_name", "Amit Kumar");
                    userDetails.put("last_name", "Gupta");
                    userDetails.put("age", "21");
                    userDetails.put("gender", "Male");
                    userDetails.put("dob", "24-04-1995");
                    userDetails.put("country", "India");
                    userDetails.put("zip", "110096");
                    userDetails.put("city", "Delhi");
                    userDetails.put("state", "Delhi");
                    userDetails.put("address", "A-412 Noida");
                    userDetails.put("mobile", "454545454455");
                    userDetails.put("mobile2", "45455t565656656");
                    userObject.put("userDetails",userDetails );
                    DriveMetaData.with(this).sendTags(userObject.toString(),"update");
                } catch (Exception e) {
                   Log.v("Exception",e.toString());
                }
                break;

            case R.id.btn_checkout:
                try {
                    JSONObject userDetails = new JSONObject();
                    JSONObject userObject =  new JSONObject();
                    userDetails.put("first_name", "Amit");
                    userDetails.put("last_name", "Gupta");
                    userDetails.put("age", "21");
                    userDetails.put("gender", "Male");
                    userDetails.put("dob", "24-04-1995");
                    userDetails.put("country", "India");
                    userDetails.put("zip", "110096");
                    userDetails.put("city", "Delhi");
                    userDetails.put("state", "Delhi");
                    userDetails.put("address", "A-412 Noida");
                    userDetails.put("mobile", "454545454455");
                    userDetails.put("mobile2", "45455t565656656");
                    userObject.put("userDetails",userDetails );
                    DriveMetaData.with(this).sendTags(userObject.toString(),"delete");
                } catch (Exception e) {
                    Log.v("Exception",e.toString());
                }
                break;

        }

    }




    @Override
    public void onResponse(boolean status, String message, String requestAcknowledgementID) {
        Log.d("MainActivity", "status "+status+ " message "+message+"requestAcknowledgementID =====    > "+requestAcknowledgementID);
    }


}