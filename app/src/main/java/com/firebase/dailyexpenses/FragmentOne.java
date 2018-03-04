package com.firebase.dailyexpenses;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by eunoiatechnologies on 15/09/16.
 */

public class FragmentOne extends Fragment {

    private Button plus,minus;
    private RecyclerView recyclerView;
    ExpensesDAO expensesDAO;
    private TextView total;
    HashMap map;
    private String TAG ="FramentOne.java";
    String sendDate;
    Query databaseReference1;
    ValueEventListener databaseReference2;

     FirebaseAuth mAuth;

    LinearLayout noResultsNotFound,dayTotalLayout;
    // newInstance constructor for creating fragment with arguments
    public static FragmentOne newInstance(int page, String title,HashMap map) {
        FragmentOne fragmentFirst = new FragmentOne();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        args.putSerializable("map",map);
 //       args.putSerializable("map",map);
        fragmentFirst.setArguments(args);
        return fragmentFirst;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        plus            = (Button) view.findViewById(R.id.plus);
        minus           = (Button) view.findViewById(R.id.minus);
        recyclerView    = (RecyclerView) view.findViewById(R.id.recycler_view);
        expensesDAO     =   new ExpensesDAO(getContext());
        total           =   (TextView) view.findViewById(R.id.total);
        noResultsNotFound   =   (LinearLayout)view.findViewById(R.id.no_results_found_layout);
        dayTotalLayout  =   (LinearLayout)view.findViewById(R.id.day_total_layout);

        mAuth           =   FirebaseAuth.getInstance();

        map = (HashMap) getArguments().getSerializable("map");
        sendDate = map.get("date").toString();

        AppPreferences appPreferences = new AppPreferences(getContext());
        databaseReference1   = FirebaseDatabase.getInstance().getReference("Expenses").child(appPreferences.getUserId()).orderByChild("datetostore").equalTo(sendDate);
        Log.i(TAG,"database refreence 1" +databaseReference1);
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()==0)
                {
                    noResultsNotFound.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    dayTotalLayout.setVisibility(View.GONE);
                }
                else
                {
                    noResultsNotFound.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    dayTotalLayout.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);



    plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AddAmount.class);
                intent.putExtra("key","plus");
                intent.putExtra("date",map.get("date").toString());
                startActivity(intent);
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SubstractAmount.class);
                intent.putExtra("key","minus");
                intent.putExtra("date",map.get("date").toString());
                startActivity(intent);
            }
        });

        try
        {

            databaseReference2   = FirebaseDatabase.getInstance().getReference("Expenses").child(appPreferences.getUserId()).orderByChild("datetostore").equalTo(sendDate).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.i(TAG, "value1" + dataSnapshot.getValue());
                    Log.i(TAG, "children count" + dataSnapshot.getChildrenCount());
                    Log.i(TAG, "childrens are" + dataSnapshot.getChildren());
                    int totalplus = 0, totalminus = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ExpensesModel user = snapshot.getValue(ExpensesModel.class);
                        System.out.println(user.getAmount());
                        if (user.getFlag().equalsIgnoreCase("plus"))
                            totalplus += Integer.parseInt(user.getAmount());
                        else
                            totalminus += Integer.parseInt(user.getAmount());
                    }

                    total.setText("" + (totalplus - totalminus));
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            Log.i(TAG,"database refreence 2" +databaseReference2);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return view;
    }






    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<ExpensesModel,ExpensesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ExpensesModel, ExpensesViewHolder>(
                ExpensesModel.class,
                R.layout.expenses_adapter_layout,
                ExpensesViewHolder.class,
                databaseReference1
        ) {
            @Override
            protected void populateViewHolder(ExpensesViewHolder viewHolder, final ExpensesModel model, int position) {
                Log.i(TAG,"model is"+model.toString());
                viewHolder.setAmount(model.getAmount());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setFlag(model.getFlag());
                if(position%2==0)
                    viewHolder.mview.setBackgroundColor(getResources().getColor(R.color.gray));
                else
                    viewHolder.mview.setBackgroundColor(getResources().getColor(R.color.white));
                viewHolder.mview.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                    showInfoDialog(model);
                   //    Toast.makeText(getContext(),"click me",Toast.LENGTH_LONG).show();
                        return false;
                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }



    public void showInfoDialog(final ExpensesModel expensesModel) {
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final AlertDialog alertDialog = builder.create();
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_layout, null);
            Button accept = (Button) view.findViewById(R.id.update);
            ImageView close = (ImageView) view.findViewById(R.id.close_button);
            EditText amount = (EditText) view.findViewById(R.id.edit_amount);
            EditText description = (EditText) view.findViewById(R.id.edit_description);
            amount.setText(""+expensesModel.getAmount());
            description.setText(expensesModel.getDescription());
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    databaseReference1   = FirebaseDatabase.getInstance().getReference("Expenses").child(mAuth.getCurrentUser().getUid());
                    alertDialog.dismiss();
                }
            });
            alertDialog.setView(view);
            alertDialog.show();
            alertDialog.setCancelable(false);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    public static class ExpensesViewHolder extends RecyclerView.ViewHolder
    {

        View mview;
        public ExpensesViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setAmount(String amount)
        {
            TextView amountTextViw =  (TextView) mview.findViewById(R.id.adapter_amount);
            amountTextViw.setText(""+amount);
        }

        public void setDescription(String description)
        {
            TextView descriptionTextView  = (TextView) mview.findViewById(R.id.adapter_amount_description);
            if(description!=null && description.charAt(description.length()-1)==',')
            {
                description = description.substring(0, description.length()-1);
            }
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
