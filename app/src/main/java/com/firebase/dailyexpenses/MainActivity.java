package com.firebase.dailyexpenses;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,Constants{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String TAG = "MainActivity.java";
    private TextView totalAmount;
    TextView navigationLeftName,navigationLeftPhone,navigationLeftEmail;
    ExpensesDAO expensesDAO;

    private  FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener mAuthListener;

    private ValueEventListener databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        expensesDAO = new ExpensesDAO(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth   =   FirebaseAuth.getInstance();
        mAuthListener   =   new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() ==null)
                {
                    Intent intent = new Intent(MainActivity.this,Register.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        };

        totalAmount = (TextView) findViewById(R.id.total_amount);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_layout, null);
        View header=navigationView.getHeaderView(0);

        navigationLeftName = (TextView)header.findViewById(R.id.nav_header_name);
        navigationLeftPhone = (TextView) header.findViewById(R.id.nav_header_phone);
        navigationLeftEmail =   (TextView) header.findViewById(R.id.nav_header_email);
        if(mAuth!=null)
        {

           DatabaseReference firebaseDatabase   = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid());
           firebaseDatabase.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null) {

                    RegisterModel registerModel =  dataSnapshot.getValue(RegisterModel.class);
                    Log.i(TAG, "value is are name" + registerModel.getName());

                    navigationLeftName.setText(registerModel.getName());
                    navigationLeftEmail.setText(mAuth.getCurrentUser().getEmail());
                    navigationLeftPhone.setText(""+registerModel.getPhone());

                /*    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        Log.i(TAG, "value is are" + messageSnapshot.getValue(RegisterModel.class));
                     }
*/
                }
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });

        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
     //   setupViewPager(viewPager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
      //  viewPager.setCurrentItem(11);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
           //     Toast.makeText(MainActivity.this,"page scrolled",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageSelected(int position) {
            //    Toast.makeText(MainActivity.this,"page selected",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            //    Toast.makeText(MainActivity.this,"page scroll state changed",Toast.LENGTH_LONG).show();
            }
        });


        AppPreferences appPreferences = new AppPreferences(this);
        Log.i(TAG, "appp user id"+appPreferences.getUserId());
       // databaseReference   = FirebaseDatabase.getInstance().getReference("Expenses").child(appPreferences.getUserId());
        databaseReference   = FirebaseDatabase.getInstance().getReference("Expenses").child(appPreferences.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG,"value1"+dataSnapshot.getValue());
                Log.i(TAG,"children count"+dataSnapshot.getChildrenCount());
                Log.i(TAG,"childrens are"+dataSnapshot.getChildren());
                int totalplus=0,totalminus=0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ExpensesModel user = snapshot.getValue(ExpensesModel.class);
                    System.out.println(user.getAmount());
                   if(user.getFlag().equalsIgnoreCase("plus"))
                        totalplus += Integer.parseInt(user.getAmount());
                    else
                        totalminus += Integer.parseInt(user.getAmount());
                }

            totalAmount.setText(""+(totalplus-totalminus));
   }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG,"value"+databaseError);
            }
        });

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_report) {
            Intent intent = new Intent(this,ReportsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            AppPreferences appPreferences = new AppPreferences(MainActivity.this);
            appPreferences.setIsNotFirstTime(false);
            appPreferences.setUserId("");
            Intent intent = new Intent(this,SignupOrLogin.class);
            startActivity(intent);
            mAuth.signOut();
        }
        else if(id==R.id.nav_monthly_reports)
        {
            Intent intent = new Intent(this,MonthlyReports.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    @Override
    protected void onResume() {
        super.onResume();
//        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        viewPager.setCurrentItem(0);
        viewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(15);
            }
        },100);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

    }

    class  MyAdapter extends FragmentPagerAdapter
    {
        List<Map> mapList=null;
        public MyAdapter(FragmentManager fm) {
            super(fm);
            ExpensesDAO expensesDAO = new ExpensesDAO(MainActivity.this);

            Log.i(TAG,"result at"+expensesDAO.getCurrentDateLessTenAndGreaterTen());
            mapList = expensesDAO.getCurrentDateLessTenAndGreaterTen();
        }

        @Override
        public Fragment getItem(int position) {

            HashMap map=null;
            for(int i=0;i<mapList.size();i++)
            {
                map = (HashMap) mapList.get(i);
                map.put("date",map.get("date"));
                if(i==position)
                {
                    return FragmentOne.newInstance(i, "Page # 1",map);
                }

            }
            return FragmentOne.newInstance(0, "Page # 1",map);
        }

        @Override
        public int getCount() {
            return mapList!=null?mapList.size():0;
        }
        @Override
        public CharSequence getPageTitle(int position) {

            if(mapList!=null)
            {
                for (int i=0;i<mapList.size();i++)
                {
                    HashMap map = (HashMap) mapList.get(position);
                    String date = getCurrentTime(map.get("date").toString());
                 Log.i("tag","our date"+date);
                    return date;
                    //return (CharSequence) map.get("date");
                }
            }
        //    return "TAB " + (position + 1);
            return "";
        }
    }


    private String getCurrentTime(String date){

        String returnDate = date;
        Date c1 = new Date();
        Calendar today = Calendar.getInstance();
        today.setTime(c1);
        c1 = today.getTime();

        Date c2 = new Date();
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.setTime(c2);
        tomorrow.add(Calendar.DATE, 1);
        c2 = tomorrow.getTime();

        Date c3 = new Date();
        Calendar yesterday = Calendar.getInstance();
        yesterday.setTime(c3);
        yesterday.add(Calendar.DATE, -1);
        c3 = yesterday.getTime();


        SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate1 = df1.format(c1);
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate2 = df2.format(c2);
        SimpleDateFormat df3 = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate3 = df3.format(c3);
        Log.i("tag","today is"+today);
        Log.i("tag","today is one"+formattedDate1);
        if(formattedDate1.equalsIgnoreCase(""+date))
        {
            returnDate ="Today";
        }
       else if(formattedDate2.equalsIgnoreCase(""+date))
        {
            returnDate ="Tomorrow";
        }
        else if(formattedDate3.equalsIgnoreCase(""+date))
        {
            returnDate ="Yesterday";
        }

        return returnDate;
    }


    private String tomorrow(String date){

        Date c = new Date();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(c);
        c1.add(Calendar.DATE, 1);
        c = c1.getTime();

        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(c);
        Log.i("tag","value date one"+date);
        Log.i("tag","value date two"+formattedDate);

        if(date.toString().equalsIgnoreCase(""+formattedDate))
        {
            formattedDate ="Tomorrow";
        }

        return formattedDate;
    }

    private String yesterday(String date){
        Date c = new Date();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(c);
        c1.add(Calendar.DATE, -1);
        c = c1.getTime();


        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(c);
        Log.i("tag","value date one"+date);
        Log.i("tag","value date two"+formattedDate);
        if(date.toString().equalsIgnoreCase(""+formattedDate))
        {
            formattedDate ="Yesterday";
        }

        return formattedDate;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
