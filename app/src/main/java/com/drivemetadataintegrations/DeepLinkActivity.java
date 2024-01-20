package com.drivemetadataintegrations;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DeepLinkActivity extends AppCompatActivity {
    private TextView game_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deeplik);
        game_title=findViewById(R.id.game_title);
        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {
            Log.e("extras",extras.getString("DeepLinkData"));
            game_title.setText("DeepLink-data :"+extras.getString("DeepLinkData"));

        }


    }
}
