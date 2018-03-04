package com.firebase.dailyexpenses;

import android.app.Application;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by user on 29-01-2017.
 */
public class ExpensesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseAnalytics.getInstance(this);


     /*
       if(!FirebaseApp.getApps(this).isEmpty())
       {


       }
*/
    }
}
