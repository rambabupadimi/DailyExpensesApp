package com.firebase.dailyexpenses;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddAmount extends AppCompatActivity implements View.OnClickListener , Constants {


    private EditText amountEditText,descriptionEditText;
    private Button addAmountButton,addNext;
    private ExpensesDAO expensesDAO;
    private String TAG ="AddAmount.java";
    String val="";
    String date ="";

    LinearLayout savingLayout,salaryLayout,dailywagesLayout;
    ToggleButton savingButton,salaryButton,dailywagesButton;
    TextView    savingsText,salaryText,dailywagesText;
    String  savings="",salary="",dailywages="";
    String resultDescription="";



    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_amount2);


        mAuth   =   FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses");


        savingLayout    =   (LinearLayout)findViewById(R.id.savings_layout);
        salaryLayout    =   (LinearLayout)findViewById(R.id.salary_layout);
        dailywagesLayout    =   (LinearLayout)findViewById(R.id.daily_wages_layout);

        savingButton    =   (ToggleButton)findViewById(R.id.savings_button);
        salaryButton    =   (ToggleButton)findViewById(R.id.salary_button);
        dailywagesButton    =   (ToggleButton)findViewById(R.id.daily_wages_button);

        savingsText     =   (TextView)findViewById(R.id.savings_text);
        salaryText      =   (TextView)findViewById(R.id.salary_text);
        dailywagesText  =   (TextView)findViewById(R.id.daily_wages_text);

        amountEditText = (EditText) findViewById(R.id.amount_value1);
        descriptionEditText = (EditText)findViewById(R.id.description_value1);
        addAmountButton = (Button) findViewById(R.id.add1);
        expensesDAO = new ExpensesDAO(this);

        savingButton.setOnClickListener(this);
        salaryButton.setOnClickListener(this);
        dailywagesButton.setOnClickListener(this);

        try {
            val = getIntent().getStringExtra("key").toString();
            date = getIntent().getStringExtra("date").toString();
            Log.i(TAG,"value"+val);
        }catch (Exception e)
        {
            e.printStackTrace();
        }



        addAmountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String description = descriptionEditText.getText().toString();
                    if(salary.trim().length()>0)
                    {
                        resultDescription = resultDescription.concat(salary+",");
                    }
                    else if(savings.trim().length()>0)
                    {
                        resultDescription = resultDescription.concat(savings+",");
                    }
                    else if(dailywages.trim().length()>0)
                    {
                        resultDescription = resultDescription.concat(dailywages+",");
                    }
                    else if(description.trim().length()>0)
                        resultDescription  = resultDescription.concat(description+",");
                    Log.i(TAG,"result description"+resultDescription);

                    AppPreferences appPreferences = new AppPreferences(AddAmount.this);


                    HashMap hashMap = new HashMap();
                    hashMap.put(COLUMN_AMOUNT,amountEditText.getText().toString());
                    hashMap.put(COLUMN_DESCRIPTION,resultDescription);
                    hashMap.put(COLUMN_DATE_TO_STORE,date);
                    hashMap.put(COLUMN_USER_ID,appPreferences.getUserId());
                    hashMap.put(FLAG,val);

                    databaseReference.child(appPreferences.getUserId()).push().setValue(hashMap);
                    databaseReference.keepSynced(true);

                    Intent intent = new Intent(AddAmount.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);





                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch (id) {
            case android.R.id.home:
                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
            if(view == savingButton)
            {
                if( savingButton.isChecked() == true)
                {
                    savingButton.setChecked(true);
                    savingLayout.setBackgroundColor(Color.parseColor("#66BBCC"));
                    savingButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                    savingsText.setTextColor(Color.WHITE);
                    savings = "Savings";
                }
                else
                {
                    savingButton.setChecked(false);
                    savingLayout.setBackgroundColor(Color.WHITE);
                    savingButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#66BBCC")));
                    savingsText.setTextColor(Color.parseColor("#66BBCC"));
                    savings = "";
                }
            }
        else if(view == salaryButton)
            {
                if( salaryButton.isChecked() == true)
                {
                    salaryButton.setChecked(true);
                    salaryLayout.setBackgroundColor(Color.parseColor("#66BBCC"));
                    salaryButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                    salaryText.setTextColor(Color.WHITE);
                    salary = "Salary";
                }
                else
                {
                    salaryButton.setChecked(false);
                    salaryLayout.setBackgroundColor(Color.WHITE);
                    salaryButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#66BBCC")));
                    salaryText.setTextColor(Color.parseColor("#66BBCC"));
                    salary = "";
                }
            }
        else if(view == dailywagesButton)
            {
                if( dailywagesButton.isChecked() == true)
                {
                    dailywagesButton.setChecked(true);
                    dailywagesLayout.setBackgroundColor(Color.parseColor("#66BBCC"));
                    dailywagesButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                    dailywagesText.setTextColor(Color.WHITE);
                    dailywages = "Daily Wages";
                }
                else
                {
                    dailywagesButton.setChecked(false);
                    dailywagesLayout.setBackgroundColor(Color.WHITE);
                    dailywagesButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#66BBCC")));
                    dailywagesText.setTextColor(Color.parseColor("#66BBCC"));
                    dailywages = "";
                }
            }
    }
}
