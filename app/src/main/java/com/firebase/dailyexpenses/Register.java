package com.firebase.dailyexpenses;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Register extends AppCompatActivity implements Constants{

    EditText name,phone,password,email;
    Button register,get;
    private ExpensesDAO expensesDAO;
    private String TAG ="Register";

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth   = FirebaseAuth.getInstance();
        progressDialog  =   new ProgressDialog(this);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        name = (EditText) findViewById(R.id.name);
        phone = (EditText)findViewById(R.id.phone);
        password = (EditText)findViewById(R.id.password);
        expensesDAO =   new ExpensesDAO(this);
        register = (Button)findViewById(R.id.register);
        email   =   (EditText)findViewById(R.id.email);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String sPhone       = phone.getText().toString();
                final String sName        = name.getText().toString();
                String sPassword    = password.getText().toString();
                String sEmail       =   email.getText().toString();


                if(sPhone.length()>0 && sName.length()>0 && sPassword.length()>0 && sEmail.length() >0 ) {

                    progressDialog.setMessage("Signing up...");
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(sEmail, sPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userid = mAuth.getCurrentUser().getUid();
                                DatabaseReference currentuserdb = firebaseDatabase.child(userid);

                                HashMap hashMap = new HashMap();
                                hashMap.put(COLUMN_NAME, sName);
                                hashMap.put(COLUMN_PHONE, sPhone);

                               // currentuserdb.push().setValue(hashMap);
                                currentuserdb.setValue(hashMap);
                                currentuserdb.keepSynced(true);

                                progressDialog.dismiss();

                                AppPreferences appPreferences = new AppPreferences(Register.this);
                                appPreferences.setIsNotFirstTime(true);
                                appPreferences.setUserId(userid);
                                firebaseDatabase.keepSynced(true);
                                Intent intent = new Intent(Register.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                                        Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(Register.this,"Sorry !! Please enter proper details.",Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(Register.this,"Please fill mandatory fields",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}
