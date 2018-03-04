package com.firebase.dailyexpenses;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class MonthlyReports extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private RecyclerView monthlyReportRecyclerview;
    Query databaseReference1;
    FirebaseAuth mAuth;

    LinearLayout noresultfoundLayout,recyclerviewLayout;

    private String TAG ="MonthlyReports";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_reports);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        mAuth       =   FirebaseAuth.getInstance();


        noresultfoundLayout =   (LinearLayout)findViewById(R.id.results_not_found_layout);
        recyclerviewLayout  =   (LinearLayout)findViewById(R.id.recycler_view_layout);




        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select Here");
        categories.add("Jan");
        categories.add("Feb");
        categories.add("March");
        categories.add("April");
        categories.add("May");
        categories.add("June");
        categories.add("July");
        categories.add("Aug");
        categories.add("Sept");
        categories.add("Oct");
        categories.add("Nov");
        categories.add("Dec");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        monthlyReportRecyclerview   =   (RecyclerView)findViewById(R.id.monthly_reports_recyclerview);

    }

    @Override
    public void onItemSelected(AdapterView<?>parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        if(position!=0)
        {
            call(""+position);
        }

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


    private void call(String selectedMonth)
    {

        Log.i(TAG,"selected month"+selectedMonth);
        String startDate,endDate;
        if(selectedMonth.length()==1) {
            startDate = "2018/0" + selectedMonth + "/01";
            endDate = "2018/0" + selectedMonth + "/31";
        } else {
            startDate = "2018/" + selectedMonth + "/01";
            endDate = "2018/" + selectedMonth + "/31";
        }

        Log.i(TAG,"startdate"+startDate);
        Log.i(TAG,"end date"+endDate);
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Expenses").child(mAuth.getCurrentUser().getUid()).orderByChild("datetostore").startAt(startDate).endAt(endDate);
        Log.i(TAG,"database reference 1"+databaseReference1);
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount()==0)
                    {
                        noresultfoundLayout.setVisibility(View.VISIBLE);
                        recyclerviewLayout.setVisibility(View.GONE);
                    }
                else
                    {
                        noresultfoundLayout.setVisibility(View.GONE);
                        recyclerviewLayout.setVisibility(View.VISIBLE);
                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FirebaseRecyclerAdapter<ExpensesModel, MonthlyReportsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ExpensesModel, MonthlyReportsViewHolder>(
                ExpensesModel.class,
                R.layout.reports_adapter_layout,
                MonthlyReportsViewHolder.class,
                databaseReference1
        ) {
            @Override
            protected void populateViewHolder(MonthlyReportsViewHolder viewHolder, ExpensesModel model, int position) {

                Log.i(TAG,"values are"+model.getDatetostore());

                String datetostore = model.getDatetostore();

                viewHolder.setAmount(model.getAmount());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setFlag(model.getFlag());
                viewHolder.setCreatedTime(datetostore.split("/")[1]+"-"+datetostore.split("/")[2]);
                if (position % 2 == 0)
                    viewHolder.mview.setBackgroundColor(getResources().getColor(R.color.gray));
                else
                    viewHolder.mview.setBackgroundColor(getResources().getColor(R.color.white));

            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(MonthlyReports.this);
        monthlyReportRecyclerview.setLayoutManager(layoutManager);
        monthlyReportRecyclerview.setAdapter(firebaseRecyclerAdapter);

    }



    public static class MonthlyReportsViewHolder extends RecyclerView.ViewHolder
    {

        View mview;
        LinearLayout reportsTopLayout;

        public MonthlyReportsViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            reportsTopLayout = (LinearLayout)itemView.findViewById(R.id.reportsTopLayout);

        }

        public  void setCreatedTime(String createdTime1)
        {
            Log.i("testing","created time is"+createdTime1);
            TextView  createdTime = (TextView) mview.findViewById(R.id.createdtimetextview);
            createdTime.setText(""+createdTime1);
        }
        public void setAmount(String amount)
        {
            TextView amountTextViw =  (TextView) mview.findViewById(R.id.adapter_amount);
            amountTextViw.setText(""+amount);
        }

        public void setDescription(String description)
        {
            TextView descriptionTextView  = (TextView)mview.findViewById(R.id.adapter_description);
            descriptionTextView.setText(description);
        }

        public void setFlag(String flag)
        {
            TextView flagText  = (TextView) mview.findViewById(R.id.adapter_amount_plus);
            flagText.setText(flag);
            if(flag.equalsIgnoreCase("plus"))
            {
                flagText.setTextColor(Color.parseColor("#228B22"));
                flagText.setText("+");
            }
            else
            {
                flagText.setText("-");
                flagText.setTextColor(Color.parseColor("#FF0000"));
            }
        }

    }


}
