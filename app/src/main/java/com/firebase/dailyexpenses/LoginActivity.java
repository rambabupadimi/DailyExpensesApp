package com.firebase.dailyexpenses;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    EditText loginEmail,loginPassword,loginMemberUser;
    Button loginUserButtonTAB,loginMemberButtonTAB;
    Button loginUser,loginMember;
    ExpensesDAO expensesDAO;

    private String TAG ="LoginActivity";

    private FirebaseAuth mAuth;
    private DatabaseReference firebaseDatabase,firebaseDatabase1;
    Query query;
    LinearLayout loginUserSection,loginMemberSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth    =    FirebaseAuth.getInstance();
        firebaseDatabase    =   FirebaseDatabase.getInstance().getReference().child("Users");

        firebaseDatabase.keepSynced(true);
        expensesDAO =  new ExpensesDAO(LoginActivity.this);
        loginEmail  =   (EditText) findViewById(R.id.login_email);
        loginPassword   =   (EditText) findViewById(R.id.login_password);

        loginMemberUser =   (EditText) findViewById(R.id.login_member_userid);


        loginUser       =   (Button) findViewById(R.id.login_user_button);
        loginMember     =   (Button) findViewById(R.id.login_member_button);

        loginUserButtonTAB  =   (Button) findViewById(R.id.login_user);
        loginMemberButtonTAB    =   (Button)findViewById(R.id.login_member);

        loginUserSection    =   (LinearLayout) findViewById(R.id.login_user_section);
        loginMemberSection  =   (LinearLayout) findViewById(R.id.login_member_section);

        loginMemberSection.setVisibility(View.GONE);


        loginMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = loginMemberUser.getText().toString();
                Log.i(TAG,"username"+username);
            }
        });

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginMemberSection.setVisibility(View.GONE);
                loginUserSection.setVisibility(View.VISIBLE);
                loginUser.setBackgroundColor(Color.parseColor("#5ba8b7"));
                loginUser.setTextColor(Color.WHITE);
                loginMember.setBackgroundColor(Color.WHITE);
                loginMember.setTextColor(Color.parseColor("#5ba8b7"));

                String lEmail = loginEmail.getText().toString();
                String lpassword = loginPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(lEmail,lpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            checkUserExist();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this,"Your credentials are wrong",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        loginMemberButtonTAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserSection.setVisibility(View.GONE);
                loginMemberSection.setVisibility(View.VISIBLE);
                loginUserButtonTAB.setBackgroundColor(Color.WHITE);
                loginUserButtonTAB.setTextColor(Color.parseColor("#5ba8b7"));
                loginMemberButtonTAB.setBackgroundColor(Color.parseColor("#5ba8b7"));
                loginMemberButtonTAB.setTextColor(Color.WHITE);

            }
        });


        loginUserButtonTAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserSection.setVisibility(View.VISIBLE);
                loginMemberSection.setVisibility(View.GONE);
                loginUserButtonTAB.setBackgroundColor(Color.parseColor("#5ba8b7"));
                loginUserButtonTAB.setTextColor(Color.WHITE);
                loginMemberButtonTAB.setBackgroundColor(Color.WHITE);
                loginMemberButtonTAB.setTextColor(Color.parseColor("#5ba8b7"));

            }
        });


    }

    private void checkUserExist()
    {
        final String usrid = mAuth.getCurrentUser().getUid();
        Log.i(TAG,"userid id"+usrid);

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.i(TAG,"snap shot"+dataSnapshot);
                if(dataSnapshot.hasChild(usrid))
                {
                    AppPreferences appPreferences = new AppPreferences(LoginActivity.this);
                    appPreferences.setIsNotFirstTime(true);
                    appPreferences.setUserId(usrid);
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                            Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"You need to setup your acccount",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
