package com.healthhelp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Space;

/**
 * Created by bhargavsarvepalli on 22/03/15.
 */
public class SplashActivity extends Activity {
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        callDisplayActivity();
        pref = getSharedPreferences("MyPref",0);
    }
    private void callDisplayActivity() {

        final Runnable splashScreenRunnable = new Runnable() {
            public void run() {
                Intent i;
                i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        };

        final Runnable searchScreenRunnable = new Runnable() {
            public void run() {
                Intent i;
                i = new Intent(SplashActivity.this, SearchActivity.class);
                startActivity(i);
                finish();
            }
        };


            if(pref!=null && pref.getBoolean("loggedIn",false)){
                    Handler handler = new Handler();
                    handler.postDelayed(searchScreenRunnable,1000);
                } else{
                    Handler handler = new Handler();
                    handler.postDelayed(splashScreenRunnable,1000);

                }
            }



}
