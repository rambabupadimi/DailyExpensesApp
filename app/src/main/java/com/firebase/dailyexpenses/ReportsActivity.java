package com.firebase.dailyexpenses;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class ReportsActivity extends AppCompatActivity {

    private EditText startDate,endDate;
    private Button searchButton;
    private RecyclerView searchResultRecyclerView;

    private DatePicker datePicker;
    private Calendar calendar;
    Calendar myCalendar;
    private int year, month, day;
    private String TAG = "ReportsActivity.java";

    LinearLayout noResultsNotFound;
    Query databaseReference1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        noResultsNotFound = (LinearLayout) findViewById(R.id.no_results_found_layout);
        startDate = (EditText)findViewById(R.id.start_date_edittext);
        endDate     =   (EditText)findViewById(R.id.end_date_edititext);
        searchButton    =   (Button)findViewById(R.id.search_button);
        searchResultRecyclerView    = (RecyclerView)findViewById(R.id.search_result_recycler_view);
        myCalendar = Calendar.getInstance();
        noResultsNotFound.setVisibility(View.VISIBLE);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }
        };

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ReportsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                new DatePickerDialog(ReportsActivity.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Log.i(TAG,"startdate"+startDate.getText().toString());
                Log.i(TAG,"end date"+endDate.getText().toString());
                AppPreferences appPreferences = new AppPreferences(ReportsActivity.this);
                if(startDate.getText().toString().length()>0 && endDate.getText().toString().length()>0) {
                   databaseReference1 = FirebaseDatabase.getInstance().getReference("Expenses").child(appPreferences.getUserId()).orderByChild("datetostore").startAt(startDate.getText().toString()).endAt(endDate.getText().toString());
                    Log.i(TAG,"database references is"+databaseReference1);

                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getChildrenCount()==0)
                            {
                                noResultsNotFound.setVisibility(View.VISIBLE);
                                searchResultRecyclerView.setVisibility(View.GONE);

                            }
                            else
                            {
                                noResultsNotFound.setVisibility(View.GONE);
                                searchResultRecyclerView.setVisibility(View.VISIBLE);

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    FirebaseRecyclerAdapter<ExpensesModel, ReportsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ExpensesModel, ReportsViewHolder>(
                            ExpensesModel.class,
                            R.layout.reports_adapter_layout,
                            ReportsViewHolder.class,
                            databaseReference1
                    ) {
                        @Override
                        protected void populateViewHolder(ReportsViewHolder viewHolder, ExpensesModel model, int position) {

                            String datetostore = model.getDatetostore();

                            viewHolder.setAmount(model.getAmount());
                            viewHolder.setDescription(model.getDescription());
                            viewHolder.setFlag(model.getFlag());
                            viewHolder.setCreatedTime(datetostore.split("/")[1]+"-"+datetostore.split("/")[2]);
                            // viewHolder.setCreatedTime(model.getDatetostore());
                            if (position % 2 == 0)
                                viewHolder.mview.setBackgroundColor(getResources().getColor(R.color.gray));
                            else
                                viewHolder.mview.setBackgroundColor(getResources().getColor(R.color.white));

                        }
                    };
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ReportsActivity.this);
                    searchResultRecyclerView.setLayoutManager(layoutManager);
                    searchResultRecyclerView.setAdapter(firebaseRecyclerAdapter);
                }
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        startDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel1() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        endDate.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    public static class ReportsViewHolder extends RecyclerView.ViewHolder
    {

        View mview;
        public TextView amountTextView;
        public TextView descriptionTextView;
        private TextView plus;
        LinearLayout reportsTopLayout;

        public ReportsViewHolder(View itemView) {
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
