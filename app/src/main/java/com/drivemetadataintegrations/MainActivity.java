package com.drivemetadataintegrations;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.drivemetadata.DriveMetaData;
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
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public class MainActivity  extends  AppCompatActivity implements View.OnClickListener, DriveMetaDataCallbacks {
    Button btnView,btn_add_to_cart,btn_checkout;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);

        btn_add_to_cart.setOnClickListener(this);

        btn_checkout = findViewById(R.id.btn_checkout);

        btn_checkout.setOnClickListener(this);

        DriveMetaData.with(this).setDriveMetaDataCallbacks(this::onResponse);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.e("AdMod", "" + initializationStatus);
            }
        });
//        List<String> testDeviceIds = Arrays.asList("7C1175DBFB5D5BE7DFE6E8145160EE93");
//        RequestConfiguration configuration =
//                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
//        MobileAds.setRequestConfiguration(configuration);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.setOnPaidEventListener(new OnPaidEventListener() {
//                                           @Override
//                                           public void onPaidEvent(@NonNull AdValue adValue) {
//                                               System.out.println("getCurrencyCode"+adValue.getCurrencyCode());
//                                               System.out.println("getValueMicros"+adValue.getValueMicros());
//                                               System.out.println("getPrecisionType"+adValue.getPrecisionType());
//
//
//                                           }
//                                       });

        DriveMetaData.sendBannerDetails(this,mAdView,adRequest);


                mAdView.loadAd(adRequest);
        // add paid event listener



        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String deviceToken = getString(R.string.msg_token_fmt, token);
                        Log.d("DeviceToken", deviceToken);

                        JSONObject userDetails = new JSONObject();
                        JSONObject userObject =  new JSONObject();
                        try {
                            userDetails.put("device_token", deviceToken);
                            userObject.put("deviceNotificationToken",userDetails );
                            DriveMetaData.with(MainActivity.this).sendTags(userObject.toString(),"deviceToken");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                });






    }






    // Create the game timer, which counts down to the end of the level
    // and shows the "retry" button.


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_view:


                break;
            case R.id.btn_add_to_cart:
                Log.e("Click","click");
                Intent intent = new Intent(MainActivity.this,SecondAdActivity.class);
                startActivity(intent);

//                JSONObject userDetails = new JSONObject();
//                JSONObject userObject =  new JSONObject();
//                try {
//                    userDetails.put("first_name", "testUser");
//                    userDetails.put("last_name", "Yadav");
//                    userDetails.put("age", "21");
//                    userDetails.put("gender", "Male");
//                    userDetails.put("dob", "24-04-1995");
//                    userDetails.put("country", "India");
//                    userDetails.put("zip", "110096");
//                    userDetails.put("city", "Delhi");
//                    userDetails.put("state", "Delhi");
//                    userDetails.put("address", "A-412 Noida");
//                    userDetails.put("mobile", "454545454455");
//                    userDetails.put("mobile2", "45455t565656656");
//                    userObject.put("userDetails",userDetails );
//                    DriveMetaData.with(this).sendTags(userObject.toString(),"update");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                break;
            case R.id.btn_checkout:
                Intent intent1 = new Intent(MainActivity.this,RewardAdActivity.class);
                startActivity(intent1);
                break;

        }

    }




    @Override
    public void onResponse(boolean status, String message, String requestAcknowledgementID) {
        Log.d("MainActibity", "status "+status+ " message "+message);
    }


}