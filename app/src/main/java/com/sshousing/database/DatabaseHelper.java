package com.sshousing.database;

/**
 * Created by ilyas on 5/28/17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    //-----------------------id for the tables, -_- required for the cursor -----------
    public static final String _ID = "_id";
    //-----------------------Building table-----------------------------
    public static final String Building = "Building";
    public static final String BUILDINGID = "BUILDINGID";
    public static final String BUILDINGADDRESS = "BUILDINGADDRESS";
    public static final String BUILDINGNBROFUNITS = "BUILDINGNBROFUNITS";
    public static final String BUILDINGNBROFFLOORS = "BUILDINGNBROFFLOORS";

    //-----------------------Unit table-----------------------------
    public static final String Unit = "Unit";
    public static final String UNITID = "UNITID";
    public static final String UNITNUMBER = "UNITNUMBER";
    public static final String UNITFLOORNUMBER = "UNITFLOORNUMBER";

    //-----------------------Lease table-----------------------------
    public static final String Lease = "Lease";
    public static final String LEASEID = "LEASEID";
    public static final String LEASESTART = "LEASESTART";
    public static final String LEASEEND = "LEASEEND";
    public static final String LEASEAMOUNT = "LEASEAMOUNT";
    public static final String LEASEDUEDATE = "LEASEDUEDATE";
    public static final String LEASEDEPOSIT = "LEASEDEPOSIT";
    public static final String LEASENOTE = "LEASENOTE";

    //-----------------------Tenant table-----------------------------
    public static final String Tenant = "Tenant";
    public static final String TENANTEID = "TENANTEID";
    public static final String TENANTNAME = "TENANTNAME";
    public static final String TENANTPHONE = "TENANTPHONE";

    //-----------------------Payment table-----------------------------
    public static final String Payment = "Payment";
    public static final String PAYMENTID = "PAYMENTID";
    public static final String PAYMENTAMOUNT = "AMOUNT";
    public static final String PAYMENTDATE = "PAYMENTDATE";
    public static final String PAYMENTDUETO = "PAYMENTDUETO";//???
    public static final String PAYMENTTYPE = "PAYMENTTYPE";

    //-----------------------Notification table-----------------------------
    public static final String Notification = "Notification";
    public static final String NOTIFICATIONDATE = "NOTIFICATIONDATE";

    //-----------------------Reminder table-----------------------------
    public static final String Reminder = "REMINDER";
    public static final String REMINDERID = "REMINDERID";
    public static final String REMINDERDATE = "REMINDERDATE";
    public static final String REMINDERTYPE = "REMINDERTYPE";

    //-----------------------Message table-----------------------------
    public static final String Message = "Message";
    public static final String MESSAGEID = "MESSAGEID";
    public static final String MESSAGETEXT = "MESSAGETEXT";
    public static final String MESSAGETYPE = "MESSAGETYPE";

    //-----------------------Database data-----------------------------
    private static final String DATABASE_NAME = "SSHOUSING";
    private static final int DATABASE_VERSION = 4;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("DatabaseHelper", "creating database " + sqLiteDatabase.toString());
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Building + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BUILDINGADDRESS + " TEXT, " + BUILDINGNBROFUNITS + " INTEGER, " + BUILDINGNBROFFLOORS + " INTEGER);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Unit + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BUILDINGID + " INTEGER, " + UNITNUMBER + " TEXT, " + UNITFLOORNUMBER + " INTEGER, FOREIGN KEY(" + BUILDINGID + ") REFERENCES " + Building + " (" + _ID + "));");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Lease + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TENANTEID + " INTEGER, " + BUILDINGID + " INTEGER, " + UNITID + " INTEGER, " + LEASESTART + " TEXT, " + LEASEEND + " TEXT, " + LEASEDUEDATE + " INTEGER, " + LEASEAMOUNT + " REAL," + LEASEDEPOSIT + " REAL, " + LEASENOTE + " TEXT, FOREIGN KEY(" + TENANTEID + ") REFERENCES " + Tenant + " (" + _ID + "), FOREIGN KEY(" + UNITID + ") REFERENCES " + Unit + " (" + _ID + "));");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Tenant + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TENANTNAME + " TEXT, " + TENANTPHONE + " TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Payment + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LEASEID + " INTEGER, " + PAYMENTAMOUNT + " REAL, " + PAYMENTDATE + " TEXT, " + PAYMENTDUETO + " INTEGER, " + PAYMENTTYPE + " TEXT,  FOREIGN KEY(" + LEASEID + ") REFERENCES " + Lease + " (" + _ID + "));");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Notification + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LEASEID + " INTEGER, " + MESSAGEID + " INTEGER," + PAYMENTID + " INTEGER," + REMINDERID + " INTEGER, " + NOTIFICATIONDATE + " TEXT, FOREIGN KEY(" + LEASEID + ") REFERENCES " + Lease + " (" + _ID + "), FOREIGN KEY(" + MESSAGEID + ") REFERENCES " + Message + " (" + _ID + "), FOREIGN KEY(" + PAYMENTID + ") REFERENCES " + Payment + " (" + _ID + "), FOREIGN KEY(" + REMINDERID + ") REFERENCES " + Reminder + " (" + _ID + "));");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Reminder + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + REMINDERDATE + " TEXT, " + REMINDERTYPE + " TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Message + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MESSAGETEXT + " TEXT, " + MESSAGETYPE + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d("database", "Upgrading database from version " + oldVersion + " to " + newVersion);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Building);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Unit);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Lease);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Tenant);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Payment);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Notification);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Reminder);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Message);
        onCreate(sqLiteDatabase);
    }

}
