package com.firebase.dailyexpenses;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 23-10-2016.
 */
public class AppPreferences {
    SharedPreferences pref;
    SharedPreferences.Editor    editor;
    Context context;
    int PRIVATE_MODE=0;
    private static final String PREF_NAME="app_flags";

    private static String IsNotFirstTime ="is_not_first_time";
    private String userId = "userid";

    private String dependentUserId = "dependentuserid";

    // Constructor
    public AppPreferences(Context context)
    {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setIsNotFirstTime(Boolean value)
    {
        String val=value.toString().trim();
        editor.putString(IsNotFirstTime,val);
        editor.commit();
    }

    public String getIsNotFirstTime()
    {
        String flag = pref.getString(IsNotFirstTime, "false");
        return flag;
    }

    public void setUserId(String userid)
    {
        String val = userid.toString().trim();
        editor.putString(userId,val);
        editor.commit();
    }

    public String getUserId()
    {
        String flag = pref.getString(userId,"");
        return flag;
    }




    public void clear()
    {
        editor.clear().commit();
    }

}
