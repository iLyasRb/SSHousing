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

    public long insertBuilding(String address, int nbrOfUnits, int nbrOfFloors) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ADDRESS, address);
        contentValues.put(DatabaseHelper.NBROFUNITS, nbrOfUnits);
        contentValues.put(DatabaseHelper.NBROFFLOORS, nbrOfFloors);
        return sQLiteDatabase.insert(DatabaseHelper.Building, null, contentValues);
    }

    public Cursor listBuilding() {
        return sQLiteDatabase.query(DatabaseHelper.Building, new String[] {DatabaseHelper._ID, DatabaseHelper.ADDRESS, DatabaseHelper.NBROFUNITS, DatabaseHelper.NBROFFLOORS}, null, null, null, null, null, null);
    }

    public long insertUnit(int buildingId, String unitNumber, int unitFloor) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.BUILDINGID, buildingId);
        contentValues.put(DatabaseHelper.NUMBER, unitNumber);
        contentValues.put(DatabaseHelper.FLOOR, unitFloor);
        return sQLiteDatabase.insert(DatabaseHelper.Unit, null, contentValues);
    }

    public Cursor listUnit() {
        return sQLiteDatabase.rawQuery("SELECT "+ DatabaseHelper.Unit +"."+ DatabaseHelper._ID +", "+DatabaseHelper.Building+"."+ DatabaseHelper.ADDRESS +", "+ DatabaseHelper.NUMBER +", "+ DatabaseHelper.FLOOR +" from "+ DatabaseHelper.Unit +" inner join "+DatabaseHelper.Building+" on "+DatabaseHelper.Unit+"."+DatabaseHelper.BUILDINGID+" = "+DatabaseHelper.Building+"."+DatabaseHelper._ID + " order by " + DatabaseHelper.BUILDINGID , null);
//        DatabaseHelper.Unit, new String[] {DatabaseHelper._ID, DatabaseHelper.ADDRESS, DatabaseHelper.NUMBER, DatabaseHelper.FLOOR}, null, null, null, null, null, null
    }

    public Cursor listUnitByBuilding(int _id) {
        return sQLiteDatabase.query(DatabaseHelper.Unit, new String[] {DatabaseHelper._ID, DatabaseHelper.NUMBER, DatabaseHelper.FLOOR}, DatabaseHelper.BUILDINGID + " = " + _id, null, null, null, null);
    }

    public long insertTenant(String name, String phone) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.PHONE, phone);
        return sQLiteDatabase.insert(DatabaseHelper.Tenant, null, contentValues);
    }

    public Cursor listTenant() {
        return sQLiteDatabase.query(DatabaseHelper.Tenant, new String[] {DatabaseHelper._ID, DatabaseHelper.NAME, DatabaseHelper.PHONE}, null, null, null, null, null, null);
    }

    public boolean deleteTenant(int _id) {
        return sQLiteDatabase.delete(DatabaseHelper.Tenant, DatabaseHelper._ID + " = " + _id, null) > 0;
    }

    public long insertLease(int unitId, int tenantId, String leaseStart, String leaseEnd, double rentAmount, String rentDueDate, double rentDeposit, String LeaseNote) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.UNITID, unitId);
        contentValues.put(DatabaseHelper.TENANTEID, tenantId);
        contentValues.put(DatabaseHelper.LEASESTART, leaseStart);
        contentValues.put(DatabaseHelper.LEASEEND, leaseEnd);
        contentValues.put(DatabaseHelper.RENTAMOUNT, rentAmount);
        contentValues.put(DatabaseHelper.RENTDUEDATE, rentDueDate);
        contentValues.put(DatabaseHelper.RENTDEPOSIT, rentDeposit);
        contentValues.put(DatabaseHelper.LEASENOTE, LeaseNote);
        return sQLiteDatabase.insert(DatabaseHelper.Lease, null, contentValues);
    }

    public Cursor listLease() {
        return sQLiteDatabase.query(DatabaseHelper.Lease, new String[] {DatabaseHelper._ID, DatabaseHelper.TENANTEID, DatabaseHelper.UNITID, DatabaseHelper.LEASEID, DatabaseHelper.LEASESTART, DatabaseHelper.LEASEEND, DatabaseHelper.RENTDUEDATE, DatabaseHelper.RENTAMOUNT, DatabaseHelper.RENTDEPOSIT, DatabaseHelper.LEASENOTE}, null, null, null, null, null, null);
    }
}
