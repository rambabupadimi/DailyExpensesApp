package com.firebase.dailyexpenses;

/**
 * Created by eunoiatechnologies on 15/09/16.
 */

public interface Constants {

    public final String DATABASE_NAME ="daily_expences_db_one";
    public final int DATABASE_VERSION = 1;


    public final String TABLE_NAME_EXPENSES ="expenses";
    public final  String COLUMN_EXPENSES_ID = "expenses_id";
    public final String COLUMN_AMOUNT  = "amount";
    public final String COLUMN_DESCRIPTION = "description";
    public final String FLAG ="flag";
    public final String COLUMN_DATE_TO_STORE = "datetostore";
    public final String COLUMN_USER_ID  =   "userid";
    public final String COLUMN_MAIN_USERID ="main_user_id";

    public final String COLUMN_DEPENDENT_EMAIL ="dependent_email";
    public final String COLUMN_DEPENDENT_PASSWORD = "dependent_password";

    public final String TABLE_NAME_DATES = "dates";
    public final String COLUMN_DATE_ID ="date_id";
    public final String COLUMN_DATE      =   "date";

    public final String TABLE_NAME_REGISTER = "register";
    public final String COLUMN_NAME =   "name";
    public final String COLUMN_PHONE    =   "phone";
    public final String COLUMN_PASSWORD =   "password";
    public final String COLUMN_REGISTER_ID  =   "register_id";


    public final String TABLE_NAME_REGISTER_MEMBER ="register_member";
    public final String COLUMN_REGISTER_MEMBER_ID = "register_member_id";
    public final String COLUMN_REGISTER_MEMBER_NAME = "register_member_name";
    public final String COLUMN_REGISTER_MEMBER_USER_NAME = "register_member_user_name";
    public final String COLUMN_REGISTER_MEMBER_READ_ACCESS = "register_member_read_access";
    public final String COLUMN_REGISTER_MEMBER_WRITE_ACCESS = "register_member_write_access";

    public final String COLUMN_CREATE_TIME ="created_time";
    public final String COLUMN_UPDATED_TIME = "updated_time";
    public final String COLUMN_MODIFIED_TIME ="modified_time";


    public final String COLUMN_IS_MAIN  =   "is_main";
    public final String COLUMN_PARENT_ID    =   "parent_id";
    public final String COLUMN_MEMBER_USER_NAME = "member_user_name";
    public final String COLUMN_READ_ACCESS  =   "read_access";
    public final String COLUMN_WRITE_ACCESS =   "write_access";


    public final String TABLE_DB_SYNC ="db_sync";
    public final String COLUMN_DB_SYNC_ID = "db_sync_id";
    public final String COLUMN_DB_USER_ID ="db_user_id";
    public final String COLUMN_DB_SYNC_UPDATED_TIME ="updated_time";



}
