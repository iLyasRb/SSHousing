package com.sshousing.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ilyas on 5/27/17.
 */

public class DataProvider {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sQLiteDatabase;

    public DataProvider(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public SQLiteDatabase open() {
       return sQLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
    }

    public long insertBuilding(String address, int nbrOfUnit) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ADDRESS, address);
        contentValues.put(DatabaseHelper.NBROFUNIT, nbrOfUnit);
        return sQLiteDatabase.insert(DatabaseHelper.Building, null, contentValues);
    }

    public Cursor listBuilding() {
        return sQLiteDatabase.query(DatabaseHelper.Building, new String[] {DatabaseHelper._ID, DatabaseHelper.ADDRESS, DatabaseHelper.NBROFUNIT}, null, null, null, null, null, null);
    }

    public long insertUnit(int buildingId, String unitNumber, int unitFloor) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.BUILDINGID, buildingId);
        contentValues.put(DatabaseHelper.NUMBER, unitNumber);
        contentValues.put(DatabaseHelper.FLOOR, unitFloor);
        return sQLiteDatabase.insert(DatabaseHelper.Unit, null, contentValues);
    }

    public Cursor listUnit() {
        return sQLiteDatabase.rawQuery("SELECT "+ DatabaseHelper.Unit +"."+ DatabaseHelper._ID +", "+DatabaseHelper.Building+"."+ DatabaseHelper.ADDRESS +", "+ DatabaseHelper.NUMBER +", "+ DatabaseHelper.FLOOR +" from "+ DatabaseHelper.Unit +" inner join "+DatabaseHelper.Building+" on "+DatabaseHelper.Unit+"."+DatabaseHelper._ID+" = "+DatabaseHelper.Building+"."+DatabaseHelper._ID, null);
//        DatabaseHelper.Unit, new String[] {DatabaseHelper._ID, DatabaseHelper.ADDRESS, DatabaseHelper.NUMBER, DatabaseHelper.FLOOR}, null, null, null, null, null, null
    }
}
