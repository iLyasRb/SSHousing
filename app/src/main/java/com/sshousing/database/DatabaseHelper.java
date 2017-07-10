package com.sshousing.database;

/**
 * Created by ilyas on 5/28/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    //-----------------------id for the tables, required for the cursor -_- ------------------------
    public static final String _ID = "_id";
    //-----------------------Building table-----------------------------
    public static final String Building = "Building";
    public static final String BUILDINGID = "BUILDINGID";
    public static final String ADDRESS = "ADDRESS";
    public static final String NBROFUNIT = "NBROFUNIT";

    //-----------------------Unit table-----------------------------
    public static final String Unit = "Unit";
    public static final String UNITID = "UNITID";
    public static final String NUMBER = "NUMBER";
    public static final String FLOOR = "FLOOR";

    //-----------------------Lease table-----------------------------
    public static final String Lease = "Lease";
    public static final String LEASEID = "LEASEID";
    public static final String LEASESTART = "LEASESTART";
    public static final String LEASEEND = "LEASEEND";
    public static final String RENTDUEDATE = "RENTDUEDATE";
    public static final String DEPOSIT = "DEPOSIT";
    public static final String NOTE = "NOTE";

    //-----------------------Tenant table-----------------------------
    public static final String Tenant = "Tenant";
    public static final String TENANTEID = "TENANTEID";
    public static final String NAME = "NAME";
    public static final String PHONE = "PHONE";

    //-----------------------Payment table-----------------------------
    public static final String Payment = "Payment";
    public static final String PAYMENTID = "PAYMENTID";
    public static final String AMOUNT = "AMOUNT";
    public static final String PAYMENTDATE = "PAYMENTDATE";
    public static final String DUETO = "DUETO";
    public static final String PAYMENTTYPE = "PAYMENTTYPE";

    //-----------------------Notification table-----------------------------
    public static final String Notification = "Notification";
    public static final String REMINDERID = "REMINDERID";
    public static final String NOTIFICATIONDATE = "NOTIFICATIONDATE";

    //-----------------------Message table-----------------------------
    public static final String Message = "Message";
    public static final String MESSAGEID = "MESSAGEID";
    public static final String MESSAGETEXT = "TEXT";
    public static final String MESSAGETYPE = "MESSAGETYPE";

    private static final String DATABASE_NAME = "SSHOUSING";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.w("DatabaseHelper", "creating database " + sqLiteDatabase.toString());
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Building + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ADDRESS + " TEXT, " + NBROFUNIT + " INTEGER);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Unit + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BUILDINGID + " INTEGER, " + NUMBER + " TEXT, " + FLOOR + " INTEGER, FOREIGN KEY(" + BUILDINGID + ") REFERENCES " + Building + " (" + _ID + "));");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Lease + " (" + TENANTEID + " INTEGER, " + UNITID + " INTEGER, " + LEASEID + " INTEGER, " + LEASESTART + " TEXT, " + LEASEEND + " TEXT, " + RENTDUEDATE + " TEXT, " + DEPOSIT + " REAL, " + NOTE + " TEXT, PRIMARY KEY (" + TENANTEID + ", " + UNITID + ",  " + LEASEID + " ), FOREIGN KEY(" + TENANTEID + ") REFERENCES " + Tenant + " (" + _ID + "), FOREIGN KEY(" + UNITID + ") REFERENCES " + Unit + " (" + _ID + "));");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Tenant + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, " + PHONE + " TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Payment + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TENANTEID + " INTEGER, " + AMOUNT + " REAL, " + PAYMENTDATE + " TEXT, " + DUETO + " REAL, " + PAYMENTTYPE + " TEXT,  FOREIGN KEY(" + TENANTEID + ") REFERENCES " + Tenant + " (" + _ID + "));");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Notification + " (" + TENANTEID + " INTEGER, " + MESSAGEID + " INTEGER, " + REMINDERID + " INTEGER, " + NOTIFICATIONDATE + " TEXT, PRIMARY KEY (" + TENANTEID + ", " + MESSAGEID + ",  " + REMINDERID + "), FOREIGN KEY(" + TENANTEID + ") REFERENCES " + Tenant + " (" + _ID + "), FOREIGN KEY(" + MESSAGEID + ") REFERENCES " + Message + " (" + _ID + "));");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Message + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MESSAGETEXT + " TEXT, " + MESSAGETYPE + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w("database", "Upgrading database from version " + oldVersion + " to " + newVersion);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Building);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Unit);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Lease);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Tenant);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Payment);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Notification);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Message);
        onCreate(sqLiteDatabase);
    }

}
