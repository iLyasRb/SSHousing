package com.sshousing.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

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

    //-----------------------Building table ---------------------------------------------------------------------------------------
    public long insertBuilding(String address, int nbrOfUnits, int nbrOfFloors) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.BUILDINGADDRESS, address);
        contentValues.put(DatabaseHelper.BUILDINGNBROFUNITS, nbrOfUnits);
        contentValues.put(DatabaseHelper.BUILDINGNBROFFLOORS, nbrOfFloors);
        return sQLiteDatabase.insert(DatabaseHelper.Building, null, contentValues);
    }

    public Cursor listBuilding() {
        return sQLiteDatabase.query(DatabaseHelper.Building, new String[] {DatabaseHelper._ID, DatabaseHelper.BUILDINGADDRESS, DatabaseHelper.BUILDINGNBROFUNITS, DatabaseHelper.BUILDINGNBROFFLOORS}, null, null, null, null, null, null);
    }

    //-----------------------Unit table ---------------------------------------------------------------------------------------
    public long insertUnit(int buildingId, String unitNumber, int unitFloor) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.BUILDINGID, buildingId);
        contentValues.put(DatabaseHelper.UNITNUMBER, unitNumber);
        contentValues.put(DatabaseHelper.UNITFLOORNUMBER, unitFloor);
        return sQLiteDatabase.insert(DatabaseHelper.Unit, null, contentValues);
    }

    public Cursor listUnit() {
        return sQLiteDatabase.rawQuery("SELECT "+ DatabaseHelper.Unit +"."+ DatabaseHelper._ID +", "+DatabaseHelper.Building+"."+ DatabaseHelper.BUILDINGADDRESS +", "+ DatabaseHelper.UNITNUMBER +", "+ DatabaseHelper.UNITFLOORNUMBER +" from "+ DatabaseHelper.Unit +" inner join "+DatabaseHelper.Building+" on "+DatabaseHelper.Unit+"."+DatabaseHelper.BUILDINGID+" = "+DatabaseHelper.Building+"."+DatabaseHelper._ID + " order by " + DatabaseHelper.BUILDINGID , null);
//        DatabaseHelper.Unit, new String[] {DatabaseHelper._ID, DatabaseHelper.ADDRESS, DatabaseHelper.NUMBER, DatabaseHelper.FLOOR}, null, null, null, null, null, null
    }

    public Cursor listUnitByBuilding(int _id) {
        return sQLiteDatabase.query(DatabaseHelper.Unit, new String[] {DatabaseHelper._ID, DatabaseHelper.UNITNUMBER, DatabaseHelper.UNITFLOORNUMBER}, DatabaseHelper.BUILDINGID + " = " + _id, null, null, null, null);
    }

    //-----------------------Tenant table ---------------------------------------------------------------------------------------
    public long insertTenant(String name, String phone) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TENANTNAME, name);
        contentValues.put(DatabaseHelper.TENANTPHONE, phone);
        return sQLiteDatabase.insert(DatabaseHelper.Tenant, null, contentValues);
    }

    public Cursor listTenant() {
        return sQLiteDatabase.query(DatabaseHelper.Tenant, new String[] {DatabaseHelper._ID, DatabaseHelper.TENANTNAME, DatabaseHelper.TENANTPHONE}, null, null, null, null, null, null);
    }

    public boolean deleteTenant(int _id) {
        return sQLiteDatabase.delete(DatabaseHelper.Tenant, DatabaseHelper._ID + " = " + _id, null) > 0;
    }

    //-----------------------Lease table ---------------------------------------------------------------------------------------
    public long insertLease(int buildingId, int unitId, int tenantId, String leaseStart, String leaseEnd, double rentAmount, String rentDueDate, double rentDeposit, String LeaseNote) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.BUILDINGID, buildingId);
        contentValues.put(DatabaseHelper.UNITID, unitId);
        contentValues.put(DatabaseHelper.TENANTEID, tenantId);
        contentValues.put(DatabaseHelper.LEASESTART, leaseStart);
        contentValues.put(DatabaseHelper.LEASEEND, leaseEnd);
        contentValues.put(DatabaseHelper.LEASEAMOUNT, rentAmount);
        contentValues.put(DatabaseHelper.LEASEDUEDATE, rentDueDate);
        contentValues.put(DatabaseHelper.LEASEDEPOSIT, rentDeposit);
        contentValues.put(DatabaseHelper.LEASENOTE, LeaseNote);
        return sQLiteDatabase.insert(DatabaseHelper.Lease, null, contentValues);
    }

    public Cursor listLease() {
//        return sQLiteDatabase.query(DatabaseHelper.Lease, new String[] {DatabaseHelper._ID, DatabaseHelper.TENANTEID, DatabaseHelper.UNITID, DatabaseHelper.LEASESTART, DatabaseHelper.LEASEEND, DatabaseHelper.LEASEDUEDATE, DatabaseHelper.LEASEAMOUNT, DatabaseHelper.LEASEDEPOSIT, DatabaseHelper.LEASENOTE}, null, null, null, null, null, null);
        return sQLiteDatabase.rawQuery("SELECT " + DatabaseHelper.Lease +"."+ DatabaseHelper._ID + ", " + DatabaseHelper.Tenant + "." + DatabaseHelper.TENANTNAME + ", " + DatabaseHelper.Building + "." + DatabaseHelper.BUILDINGADDRESS + ", " + DatabaseHelper.Unit + "." + DatabaseHelper.UNITNUMBER + ", " + DatabaseHelper.LEASESTART + ", " + DatabaseHelper.LEASEEND + ", " + DatabaseHelper.LEASEDUEDATE + ", " + DatabaseHelper.LEASEAMOUNT + ", " + DatabaseHelper.LEASEDEPOSIT + ", " + DatabaseHelper.LEASENOTE +" from "+ DatabaseHelper.Lease +" inner join " + DatabaseHelper.Building + " on "+DatabaseHelper.Lease+"."+DatabaseHelper.BUILDINGID+" = "+DatabaseHelper.Building+"."+DatabaseHelper._ID
                                               + " inner join " + DatabaseHelper.Unit + " on " + DatabaseHelper.Lease + "." + DatabaseHelper.UNITID + " = "+DatabaseHelper.Unit + "." + DatabaseHelper._ID
                                               + " inner join " + DatabaseHelper.Tenant + " on " + DatabaseHelper.Lease + "." + DatabaseHelper.TENANTEID + " = "+DatabaseHelper.Tenant + "." + DatabaseHelper._ID
                                               + " order by " + DatabaseHelper.Lease +"." + DatabaseHelper._ID , null);
//        return sQLiteDatabase.rawQuery("SELECT "+ DatabaseHelper.Lease +"."+ DatabaseHelper._ID +", "+DatabaseHelper.Building+"."+ DatabaseHelper.BUILDINGADDRESS +", "+ DatabaseHelper.UNITNUMBER +", "+ DatabaseHelper.UNITFLOORNUMBER +" from "+ DatabaseHelper.Unit +" inner join "+DatabaseHelper.Building+" on "+DatabaseHelper.Unit+"."+DatabaseHelper.BUILDINGID+" = "+DatabaseHelper.Building+"."+DatabaseHelper._ID + " order by " + DatabaseHelper.BUILDINGID , null);
    }

    //-----------------------Payement table ---------------------------------------------------------------------------------------
    public long insertPayement(int leasId, double amount, int dueTo, String type) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.LEASEID, leasId);
        contentValues.put(DatabaseHelper.PAYMENTAMOUNT, amount);
        contentValues.put(DatabaseHelper.PAYMENTDATE, new Date().toString());
        contentValues.put(DatabaseHelper.PAYMENTDUETO, dueTo);
        contentValues.put(DatabaseHelper.PAYMENTTYPE, type);
        return sQLiteDatabase.insert(DatabaseHelper.Payment, null, contentValues);
    }

    public Cursor listPayement() {
        return sQLiteDatabase.query(DatabaseHelper.Payment, new String[] {DatabaseHelper._ID, DatabaseHelper.LEASEID, DatabaseHelper.PAYMENTAMOUNT, DatabaseHelper.PAYMENTDATE}, null, null, null, null, null, null);
    }
}
