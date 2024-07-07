package com.drivemetadataintegrations;

import android.app.Application;
import android.content.Context;

import com.drivemetadata.DriveMetaData;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MetaDataSampleApplication extends  Application{

    @Override
    public void onCreate() {
        super.onCreate();
        // DriveMetaData.setMetaAppId(this,"336547575673335");

        DriveMetaData driveMetadata = new DriveMetaData.Builder(this, 1635,
                "4d17d90c78154c9a5569c073b67d8a5a22b2fabfc5c9415b6e7f709d68762054",3020).build();
        // Set the initialized instance as a globally accessible instance.
        DriveMetaData.setSingletonInstance(driveMetadata);
        DriveMetaData.FB_APP_ID = "336547575673335";

        FacebookSdk.setApplicationId("336547575673335");
        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(this);


        DriveMetaData.enableDebugLog(this,true);

    }


    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }
}
