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
 * Created by mariyakazachkova on 4/27/17.
 */

public class MyShoppingListDBAdapter {
    private SQLiteDatabase db;
    private static MyShoppingListDBAdapter dbInstance = null;
    private MyDBhelper dbHelper;
    private final Context context;

    private static final String DB_NAME = "item.db";
    private static int dbVersion = 1;

    private static final String ITEMS_TABLE = "items";
    public static final String ITEM_ID = "item_id";   // column 0
    public static final String ITEM_NAME = "item_name";
    public static final String ITEM_REMINDER_DAYS = "item_reminder_days";
    public static final String ITEM_QUANTITY = "item_quantity";
    public static final String ITEM_STRING_ID = "item_string_id";
    public static final String ITEM_IN_LIST = "item_in_list";

    public static final String[] ITEM_COLS = {ITEM_ID, ITEM_NAME, ITEM_QUANTITY, ITEM_REMINDER_DAYS, ITEM_STRING_ID, ITEM_IN_LIST};

    public static synchronized MyShoppingListDBAdapter getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new MyShoppingListDBAdapter(context.getApplicationContext());
        }
        return dbInstance;
    }

    private MyShoppingListDBAdapter(Context ctx) {
        context = ctx;
        dbHelper = new MyDBhelper(context, DB_NAME, null, dbVersion);
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

    public long insertItem(ShoppingItem it) {
        // create a new row of values to insert
        ContentValues cvalues = new ContentValues();
        // assign values for each col
        cvalues.put(ITEM_NAME, it.getName());
        cvalues.put(ITEM_REMINDER_DAYS, it.getDays());
        cvalues.put(ITEM_QUANTITY, it.getQuantity());
        cvalues.put(ITEM_STRING_ID, it.getID());
        cvalues.put(ITEM_IN_LIST, it.inList()+"");
        // add to course table in database
        return db.insert(ITEMS_TABLE, null, cvalues);
    }

    public boolean removeItem(long id) {
        return db.delete(ITEMS_TABLE, "ITEM_ID="+id, null) > 0;
    }

    public boolean updateField(long id, int field, String wh) {
        ContentValues cvalue = new ContentValues();
        cvalue.put(ITEM_COLS[field], wh);
        return db.update(ITEMS_TABLE, cvalue, ITEM_ID +"="+id, null) > 0;
    }

    // database query methods
    public Cursor getAllItems() {
        return db.query(ITEMS_TABLE, ITEM_COLS, null, null, null, null, null);
    }

    public Cursor getItemCursor(long id) throws SQLException {
        Cursor result = db.query(true, ITEMS_TABLE, ITEM_COLS, ITEM_ID +"="+id, null, null, null, null, null);
        if ((result.getCount() == 0) || !result.moveToFirst()) {
            throw new SQLException("No items found for row: " + id);
        }
        return result;
    }

    public ShoppingItem getItem(long id) throws SQLException {
        Cursor cursor = db.query(true, ITEMS_TABLE, ITEM_COLS, ITEM_ID +"="+id, null, null, null, null, null);
        if ((cursor.getCount() == 0) || !cursor.moveToFirst()) {
            throw new SQLException("No items found for row: " + id);
        }
        // must use column indices to get column values
        int index1 = cursor.getColumnIndex(ITEM_NAME);
        int index2 = cursor.getColumnIndex(ITEM_REMINDER_DAYS);
        int index3 = cursor.getColumnIndex(ITEM_QUANTITY);
        int index4 = cursor.getColumnIndex(ITEM_STRING_ID);
        int index5 = cursor.getColumnIndex(ITEM_IN_LIST);
        return new ShoppingItem(cursor.getString(index1), Integer.parseInt(cursor.getString(index3)), Integer.parseInt(cursor.getString(index2)), cursor.getString(index4), Boolean.parseBoolean(cursor.getString(index5)));
    }





    private static class MyDBhelper extends SQLiteOpenHelper {

        // SQL statement to create a new database
        private static final String DB_CREATE = "CREATE TABLE " + ITEMS_TABLE
                + " (" + ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM_NAME + " TEXT, " +  ITEM_QUANTITY + " TEXT, " +  ITEM_REMINDER_DAYS + " TEXT, " + ITEM_STRING_ID + " Text, " + ITEM_IN_LIST + " Text);";

        public MyDBhelper(Context context, String name, SQLiteDatabase.CursorFactory fct, int version) {
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
            Log.w("ItemDB", "upgrading from version " + oldVersion + " to "
                    + newVersion + ", destroying old data");
            // drop old table if it exists, create new one
            // better to migrate existing data into new table
            adb.execSQL("DROP TABLE IF EXISTS " + ITEMS_TABLE);
            onCreate(adb);
        }
    } // DBhelper class
}

