package com.firebase.dailyexpenses;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Splash extends AppCompatActivity{

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private String TAG="Splash.java";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //  final BasicDetails details=new BasicDetails(getApplicationContext());

        final AppPreferences appPreferences=new AppPreferences(this);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                 if (appPreferences.getIsNotFirstTime().trim().equals("false") ) {
                    //the app is being launched for first time, do something
                    Log.i(TAG, "Not First time: " + appPreferences.getIsNotFirstTime());

                    // first time task
                    Intent i = new Intent(Splash.this, SignupOrLogin.class);
                    startActivity(i);

                    // record the fact that the app has been started at least once
                }
                else if(appPreferences.getIsNotFirstTime().trim().equals("true")  )
                {
                    Intent i1 = new Intent(Splash.this, MainActivity.class);
                    startActivity(i1);
                }
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    // Detect when the back button is pressed
    private boolean _doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        if (_doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            this._doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to quit", Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                _doubleBackToExitPressedOnce = false;
            }
        }, 1000);
    }
}