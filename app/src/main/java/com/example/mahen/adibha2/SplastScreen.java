package com.example.mahen.adibha2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mahen.adibha2.Preferences.PrefsManager;

public class SplastScreen extends AppCompatActivity {

    public static final String LOG_TAG = "splash";

    private Handler splash = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splast_screen);

        splash.postDelayed(new Runnable() {
            @Override
            public void run() {
                try
                {
                    PrefsManager prefsManager = new PrefsManager(getApplicationContext());
                    if (!prefsManager.isFirstTimeLaunch()) {
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Intent intent = new Intent(getApplicationContext(),HomeScreen.class);
                        startActivity(intent);
                        finish();
                    }

                }
                catch (Exception ex)
                {
                    Log.i(LOG_TAG,ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }, 2000);

    }
}
