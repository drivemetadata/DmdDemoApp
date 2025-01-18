package com.drivemetadataintegrations;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.drivemetadata.DriveMetaData;
import com.drivemetadata.callbacks.DriveMetaDataCallbacks;

import org.json.JSONException;
import org.json.JSONObject;

public class DeepLinkActivity extends AppCompatActivity implements DriveMetaDataCallbacks {
    private TextView game_title;
    private Button buy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondactivity);
        game_title=findViewById(R.id.details);
        buy=findViewById(R.id.buy);
        DriveMetaData.with(this).setDriveMetaDataCallbacks(this::onResponse);

        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {
            game_title.setText("DeepLink-data :"+extras.getString("deepLinkData"));

        }
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    DriveMetaData.with(DeepLinkActivity.this).sendTags(userObject.toString(),"Buy");
                } catch (JSONException e) {
                    Log.v("Exception",e.toString());
                }
            }
        });


    }

    @Override
    public void onResponse(boolean b, String s, String s1) {

    }
}
