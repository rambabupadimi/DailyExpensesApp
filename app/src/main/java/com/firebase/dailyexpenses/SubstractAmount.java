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

public class SubstractAmount extends AppCompatActivity implements View.OnClickListener,Constants{


    private EditText amountEditText,descriptionEditText;
    private Button addAmountButton,addNext;
    private ExpensesDAO expensesDAO;
    private String TAG ="SubstractAmount.java";
    String val="";
    String date ="";

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    ToggleButton petrolButton,vegetablesButton,transportButton,petsButton,
                billsButton,clothesButton,dinnerButton,partyButton,
                healthButton,giftButton,houseButton,hotelsButton;
    LinearLayout petrolLayout,vegetablesLayout,transportLayout,petsLayout,
            billsLayout,clothesLayout,dinnerLayout,partyLayout,
            healthLayout,giftLayout,houseLayout,hotelsLayout;
    TextView    petrolText,vegetablesText,transportText,petsText,
            billsText,clothesText,dinnerText,partyText,
            healthText,giftText,houseText,hotelsText;
    String      petrol="",vegetables="",transport="",pets="",
                bills="",clothes="",dinner="",party="",health="",
                gift="",house="",hotels="";
    String      resultDescription ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_amount);


        mAuth       =   FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses");

        amountEditText = (EditText) findViewById(R.id.amount_value);
        descriptionEditText = (EditText)findViewById(R.id.description_value);
        addAmountButton = (Button) findViewById(R.id.add);
        expensesDAO = new ExpensesDAO(this);
        petrolLayout    =   (LinearLayout)findViewById(R.id.petrol_layout);
        petrolButton = (ToggleButton)findViewById(R.id.petrol_button);
        petrolText      =   (TextView)findViewById(R.id.petrol_text);

        vegetablesLayout    =   (LinearLayout)findViewById(R.id.vegetables_layout);
        vegetablesButton = (ToggleButton)findViewById(R.id.vegetables_button);
        vegetablesText      =   (TextView)findViewById(R.id.vegetables_text);

        transportLayout    =   (LinearLayout)findViewById(R.id.transport_layout);
        transportButton = (ToggleButton)findViewById(R.id.transport_button);
        transportText      =   (TextView)findViewById(R.id.transport_text);

        petsLayout    =   (LinearLayout)findViewById(R.id.petrol_layout);
        petsButton = (ToggleButton)findViewById(R.id.pets_button);
        petsText      =   (TextView)findViewById(R.id.pets_text);

        billsLayout    =   (LinearLayout)findViewById(R.id.bills_layout);
        billsButton = (ToggleButton)findViewById(R.id.bills_button);
        billsText      =   (TextView)findViewById(R.id.bills_text);

        clothesLayout    =   (LinearLayout)findViewById(R.id.clothes_layout);
        clothesButton = (ToggleButton)findViewById(R.id.clothes_button);
        clothesText      =   (TextView)findViewById(R.id.clothes_text);

        dinnerLayout    =   (LinearLayout)findViewById(R.id.dinner_layout);
        dinnerButton = (ToggleButton)findViewById(R.id.dinner_button);
        dinnerText      =   (TextView)findViewById(R.id.dinner_text);

        partyLayout    =   (LinearLayout)findViewById(R.id.party_layout);
        partyButton = (ToggleButton)findViewById(R.id.party_button);
        partyText      =   (TextView)findViewById(R.id.party_text);

        healthLayout    =   (LinearLayout)findViewById(R.id.health_layout);
        healthButton = (ToggleButton)findViewById(R.id.health_button);
        healthText      =   (TextView)findViewById(R.id.health_text);

        giftLayout    =   (LinearLayout)findViewById(R.id.gifts_layout);
        giftButton = (ToggleButton)findViewById(R.id.gifts_button);
        giftText      =   (TextView)findViewById(R.id.gifts_text);

        houseLayout    =   (LinearLayout)findViewById(R.id.house_layout);
        houseButton = (ToggleButton)findViewById(R.id.house_button);
        houseText      =   (TextView)findViewById(R.id.house_text);

        hotelsLayout    =   (LinearLayout)findViewById(R.id.hotel_layout);
        hotelsButton = (ToggleButton)findViewById(R.id.hotel_button);
        hotelsText      =   (TextView)findViewById(R.id.hotel_text);


        petrolButton.setOnClickListener(this);
        vegetablesButton.setOnClickListener(this);
        transportButton.setOnClickListener(this);
        petsButton.setOnClickListener(this);
        billsButton.setOnClickListener(this);
        clothesButton.setOnClickListener(this);
        dinnerButton.setOnClickListener(this);
        partyButton.setOnClickListener(this);
        healthButton.setOnClickListener(this);
        giftButton.setOnClickListener(this);
        houseButton.setOnClickListener(this);
        hotelsButton.setOnClickListener(this);


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
                    Log.i(TAG,"after click petrol"+petrol);
                    String description = descriptionEditText.getText().toString();
                    if(petrol.trim().length()>0)
                    {
                        resultDescription = resultDescription.concat(petrol+",");
                    }
                    if(vegetables.trim().length()>0)
                    {
                        resultDescription = resultDescription.concat(vegetables+",");
                    }
                    if(transport.trim().length()>0)
                    {
                        resultDescription = resultDescription.concat(transport+",");
                    }
                    if(pets.trim().length()>0)
                    {
                        resultDescription = resultDescription.concat(pets+",");
                    }
                    if(bills.trim().length()>0)
                    {
                        resultDescription = resultDescription.concat(bills+",");
                    }
                    if(clothes.trim().length()>0)
                    {
                        resultDescription = resultDescription.concat(clothes+",");
                    }
                    if(dinner.trim().length()>0)
                    {
                        resultDescription = resultDescription.concat(dinner+",");
                    }
                    if(party.trim().length()>0)
                    {
                        resultDescription = resultDescription.concat(party+",");
                    }
                    if(health.trim().length()>0)
                    {
                        resultDescription = resultDescription.concat(health+",");
                    }
                    if(gift.trim().length()>0)
                    {
                        resultDescription = resultDescription.concat(gift+"");
                    }
                    if(house.trim().length()>0)
                    {
                        resultDescription = resultDescription.concat(house+"");
                    }
                    if(hotels.trim().length()>0)
                    {
                        resultDescription = resultDescription.concat(hotels+"");
                    }
                    else if(description.trim().length()>0)
                        resultDescription  = resultDescription.concat(description+",");

                    Log.i(TAG,"result description"+resultDescription);

                    AppPreferences appPreferences = new AppPreferences(SubstractAmount.this);
                    HashMap hashMap = new HashMap();
                    hashMap.put(COLUMN_AMOUNT,amountEditText.getText().toString());
                    hashMap.put(COLUMN_DESCRIPTION,resultDescription);
                    hashMap.put(COLUMN_DATE_TO_STORE,date);
                    hashMap.put(COLUMN_USER_ID,appPreferences.getUserId());
                    hashMap.put(FLAG,val);
                    databaseReference.child(appPreferences.getUserId()).push().setValue(hashMap);
                    databaseReference.keepSynced(true);
                    Intent intent = new Intent(SubstractAmount.this,MainActivity.class);
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
        if(view == petrolButton)
        {
            if( petrolButton.isChecked() == true)
            {
                petrolButton.setChecked(true);
                petrolLayout.setBackgroundColor(Color.parseColor("#66BBCC"));
                petrolButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                petrolText.setTextColor(Color.WHITE);
                petrol = "Petrol";
            }
            else
            {
                petrolButton.setChecked(false);
                petrolLayout.setBackgroundColor(Color.WHITE);
                petrolButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#66BBCC")));
                petrolText.setTextColor(Color.parseColor("#66BBCC"));
                petrol = "";
            }
        }
        else if(view == vegetablesButton)
        {
            if( vegetablesButton.isChecked() == true)
            {
                vegetablesButton.setChecked(true);
                vegetablesLayout.setBackgroundColor(Color.parseColor("#66BBCC"));
                vegetablesButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                vegetablesText.setTextColor(Color.WHITE);
                vegetables = "Vegetables";

            }
            else
            {
                vegetablesButton.setChecked(false);
                vegetablesLayout.setBackgroundColor(Color.WHITE);
                vegetablesButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#66BBCC")));
                vegetablesText.setTextColor(Color.parseColor("#66BBCC"));
                vegetables = "";

            }

        }
        else if(view == transportButton)
        {
            if( transportButton.isChecked() == true)
            {
                transportButton.setChecked(true);
                transportLayout.setBackgroundColor(Color.parseColor("#66BBCC"));
                transportButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                transportText.setTextColor(Color.WHITE);
                transport = "Transport";

            }
            else
            {
                transportButton.setChecked(false);
                transportLayout.setBackgroundColor(Color.WHITE);
                transportButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#66BBCC")));
                transportText.setTextColor(Color.parseColor("#66BBCC"));
                transport = "";

            }


        }
        else if(view == petsButton)
        {
            if( petsButton.isChecked() == true)
            {
                petsButton.setChecked(true);
                petsLayout.setBackgroundColor(Color.parseColor("#66BBCC"));
                petsButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                petsText.setTextColor(Color.WHITE);
                pets = "Pets";
            }
            else
            {
                petsButton.setChecked(false);
                petsLayout.setBackgroundColor(Color.WHITE);
                petsButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#66BBCC")));
                petsText.setTextColor(Color.parseColor("#66BBCC"));
                pets = "";

            }
        }
        else if(view == billsButton)
        {
            if( billsButton.isChecked() == true)
            {
                billsButton.setChecked(true);
                billsLayout.setBackgroundColor(Color.parseColor("#66BBCC"));
                billsButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                billsText.setTextColor(Color.WHITE);
                bills = "Bills";

            }
            else
            {
                billsButton.setChecked(false);
                billsLayout.setBackgroundColor(Color.WHITE);
                billsButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#66BBCC")));
                billsText.setTextColor(Color.parseColor("#66BBCC"));
                bills = "";

            }

        }
        else if(view == clothesButton)
        {
            if( clothesButton.isChecked() == true)
            {
                clothesButton.setChecked(true);
                clothesLayout.setBackgroundColor(Color.parseColor("#66BBCC"));
                clothesButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                clothesText.setTextColor(Color.WHITE);
                clothes = "Clothes";

            }
            else
            {
                clothesButton.setChecked(false);
                clothesLayout.setBackgroundColor(Color.WHITE);
                clothesButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#66BBCC")));
                clothesText.setTextColor(Color.parseColor("#66BBCC"));
                clothes = "";

            }

        }
        else if(view == dinnerButton)
        {
            if( dinnerButton.isChecked() == true)
            {
                dinnerButton.setChecked(true);
                dinnerLayout.setBackgroundColor(Color.parseColor("#66BBCC"));
                dinnerButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                dinnerText.setTextColor(Color.WHITE);
                dinner = "Dinner";

            }
            else
            {
                dinnerButton.setChecked(false);
                dinnerLayout.setBackgroundColor(Color.WHITE);
                dinnerButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#66BBCC")));
                dinnerText.setTextColor(Color.parseColor("#66BBCC"));
                dinner = "";
            }

        }
        else if(view == partyButton)
        {
            if( partyButton.isChecked() == true)
            {
                partyButton.setChecked(true);
                partyLayout.setBackgroundColor(Color.parseColor("#66BBCC"));
                partyButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                partyText.setTextColor(Color.WHITE);
                party = "Party";

            }
            else
            {
                partyButton.setChecked(false);
                partyLayout.setBackgroundColor(Color.WHITE);
                partyButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#66BBCC")));
                partyText.setTextColor(Color.parseColor("#66BBCC"));
                party = "";
            }

        }
        else if(view == healthButton)
        {
            if( healthButton.isChecked() == true)
            {
                healthButton.setChecked(true);
                healthLayout.setBackgroundColor(Color.parseColor("#66BBCC"));
                healthButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                healthText.setTextColor(Color.WHITE);
                health = "Health";
            }
            else
            {
                healthButton.setChecked(false);
                healthLayout.setBackgroundColor(Color.WHITE);
                healthButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#66BBCC")));
                healthText.setTextColor(Color.parseColor("#66BBCC"));
                health = "";

            }

        }
        else if(view == giftButton)
        {
            if( giftButton.isChecked() == true)
            {
                giftButton.setChecked(true);
                giftLayout.setBackgroundColor(Color.parseColor("#66BBCC"));
                giftButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                giftText.setTextColor(Color.WHITE);
                gift = "Gifts";

            }
            else
            {
                giftButton.setChecked(false);
                giftLayout.setBackgroundColor(Color.WHITE);
                giftButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#66BBCC")));
                giftText.setTextColor(Color.parseColor("#66BBCC"));
                gift = "";
            }

        }
        else if(view == houseButton)
        {
            if( houseButton.isChecked() == true)
            {
                houseButton.setChecked(true);
                houseLayout.setBackgroundColor(Color.parseColor("#66BBCC"));
                houseButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                houseText.setTextColor(Color.WHITE);
                house = "House";
            }
            else
            {
                hotelsButton.setChecked(false);
                houseLayout.setBackgroundColor(Color.WHITE);
                houseButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#66BBCC")));
                houseText.setTextColor(Color.parseColor("#66BBCC"));
                house = "";
            }

        }
        else if(view == hotelsButton)
        {
            if( hotelsButton.isChecked() == true)
            {
                hotelsButton.setChecked(true);
                hotelsLayout.setBackgroundColor(Color.parseColor("#66BBCC"));
                hotelsButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                hotelsText.setTextColor(Color.WHITE);
                hotels = "Hotels";
            }
            else
            {
                hotelsButton.setChecked(false);
                hotelsLayout.setBackgroundColor(Color.WHITE);
                hotelsButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#66BBCC")));
                hotelsText.setTextColor(Color.parseColor("#66BBCC"));
                hotels = "";
            }
        }

    }
}
