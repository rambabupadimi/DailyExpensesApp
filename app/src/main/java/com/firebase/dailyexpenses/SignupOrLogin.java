package com.firebase.dailyexpenses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SignupOrLogin extends AppCompatActivity {

    Button signup,siginin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_or_login);
        signup  = (Button)findViewById(R.id.signup);
        siginin =   (Button)findViewById(R.id.signin);


           AppPreferences appPreferences = new AppPreferences(this);
        if (appPreferences.getIsNotFirstTime().trim().equals("false") ) {

            // record the fact that the app has been started at least once
        }
        else if(appPreferences.getIsNotFirstTime().trim().equals("true")  )
        {
            Intent i1 = new Intent(this, MainActivity.class);
            i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                    Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i1);
        }


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupOrLogin.this,Register.class);
                startActivity(intent);
            }
        });
        siginin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupOrLogin.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
