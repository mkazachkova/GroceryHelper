package com.example.grocery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Kiki on 4/20/17. //receipt
 */

public class ReceiptDBAdapter {


    private SQLiteDatabase db;
    private static ReceiptDBAdapter dbInstance = null;
    private ReceiptDBhelper dbHelper;
    private final Context context;

    private static final String DB_NAME =  "receipt.db";  //"dLog.db";
    private static int dbVersion = 1;

    private static final String RECEIPT_TABLE = "receipts";
    public static final String RECEIPT_ID = "receipt_id"; //column 0
    public static final String RECEIPT_DATE = "receipt_date"; //long
    public static final String RECEIPT_AMOUNT = "receipt_amount"; //float
    public static final String[] RECEIPT_COLS = {RECEIPT_ID, RECEIPT_AMOUNT, RECEIPT_DATE};

    public static synchronized ReceiptDBAdapter getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new ReceiptDBAdapter(context.getApplicationContext());
        }
        return dbInstance;
    }


    public ReceiptDBAdapter(Context ctx) {
        context = ctx;
        dbHelper = new ReceiptDBhelper(context, DB_NAME, null, dbVersion);
    }

    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        db.close();
    }

    public void clear() {
        dbHelper.onUpgrade(db, dbVersion, dbVersion+1);  // change version to dump old data
        dbVersion++;
    }

    // database update methods

    /** insert one row */
    public long insertReceipt(Receipt r) {
        // create a new row of values to insert
        ContentValues cvalues = new ContentValues();
        // assign values for each col
        //cvalues.put(DLOG_ID, dLog.getID());
        cvalues.put(RECEIPT_AMOUNT, r.getAmount());
        cvalues.put(RECEIPT_DATE, r.getDate());
        //add to course table in database
        return db.insert(RECEIPT_TABLE, null, cvalues);
    }

    /** remove selected row */
    public boolean removeReceipt(long rid) {
        return db.delete(RECEIPT_TABLE, "RECEIPT_ID="+rid, null) > 0;
    }

    /** update receipt */
    public boolean updateReceipt(long rid, long d, float a) {
        ContentValues cvalue = new ContentValues();
        cvalue.put(RECEIPT_AMOUNT, a);
        cvalue.put(RECEIPT_DATE, d);
        return db.update(RECEIPT_TABLE, cvalue, RECEIPT_ID+"="+rid, null) > 0;
    }

    // database query methods
    public Cursor getAllReceipts() {
        return db.query(RECEIPT_TABLE, RECEIPT_COLS, null, null, null, null, null);
    }

    public Cursor getReceiptCursor(long rid) throws SQLException {
        Cursor result = db.query(true, RECEIPT_TABLE, RECEIPT_COLS, RECEIPT_ID+"="+rid, null, null, null, null, null);
        if ((result.getCount() == 0) || !result.moveToFirst()) {
            throw new SQLException("No drive log item found for row: " + rid);
        }
        return result;
    }

    public Receipt getReceipt(long rid) throws SQLException {
        Cursor cursor = db.query(true, RECEIPT_TABLE, RECEIPT_COLS, RECEIPT_ID+"="+rid, null, null, null, null, null);
        if ((cursor.getCount() == 0) || !cursor.moveToFirst()) {
            throw new SQLException("No drive log item found for row: " + rid);
        }
        // Receipt(float amount,long date)
        return new Receipt(cursor.getFloat(1), cursor.getLong(2));
    }


    private static class ReceiptDBhelper extends SQLiteOpenHelper {
        // SQL statement to create a new database
        private static final String DB_CREATE = "CREATE TABLE " + RECEIPT_TABLE
                + " (" + RECEIPT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + RECEIPT_AMOUNT + " FLOAT,"
                + RECEIPT_DATE + " LONG);";

        public ReceiptDBhelper(Context context, String name, SQLiteDatabase.CursorFactory fct, int version) {
            super(context, name, fct, version);
        }

        @Override
        public void onCreate(SQLiteDatabase adb) {
            // TODO Auto-generated method stub
            adb.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase adb, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            Log.w("ReceiptDB", "upgrading from version " + oldVersion + " to "
                    + newVersion + ", destroying old data");
            // drop old table if it exists, create new one
            // better to migrate existing data into new table
            adb.execSQL("DROP TABLE IF EXISTS " + RECEIPT_TABLE);
            onCreate(adb);
        }
    } // ReceiptDBhelper

} // ReceiptDBAdapter
