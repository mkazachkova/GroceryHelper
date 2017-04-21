package com.example.grocery;

/**
 * Created by Kiki on 4/20/17. //receipt
 */

public class ReceiptDBAdapter {

    //TODO: create database
//    private SQLiteDatabase db;
//    private static ReceiptDBAdapter dbInstance = null;
//    private ReceiptDBhelper dbHelper;
//    private final Context context;
//
//
//    private static final String DB_NAME = "dLog.db";
//    private static int dbVersion = 1;
//
//    private static final String DLOGS_TABLE = "dLogs";
//    public static final String DLOG_ID = "dLog_id"; //column 0
//    public static final String DLOG_HOURS = "dLog_hours";
//    public static final String DLOG_DATE = "dLog_date";
//    public static final String DLOG_DAY = "dLog_day";
//    public static final String DLOG_ROADTYPE = "dLog_roadType";
//    public static final String DLOG_WEATHER = "dLog_weather";
//    public static final String[] DLOG_COLS = {DLOG_ID, DLOG_HOURS, DLOG_DATE, DLOG_DAY, DLOG_ROADTYPE, DLOG_WEATHER};
//
//    private static final String DB_NAME = "receipt.db";
//
//
//
//
//
//    public static synchronized DriveLogDBAdapter getInstance(Context context) {
//        if (dbInstance == null) {
//            dbInstance = new DriveLogDBAdapter(context.getApplicationContext());
//        }
//        return dbInstance;
//    }
//
//
//    public DriveLogDBAdapter(Context ctx) {
//        context = ctx;
//        dbHelper = new DriverLogDBhelper(context, DB_NAME, null, dbVersion);
//    }
//
//    public void open() throws SQLiteException {
//        try {
//            db = dbHelper.getWritableDatabase();
//        } catch (SQLiteException ex) {
//            db = dbHelper.getReadableDatabase();
//        }
//    }
//
//    public void close() {
//        db.close();
//    }
//
//    public void clear() {
//        dbHelper.onUpgrade(db, dbVersion, dbVersion+1);  // change version to dump old data
//        dbVersion++;
//    }
//
//    // database update methods
//
//    /** insert one row */
//    public long insertDriveLog(DriveLog dLog) {
//        // create a new row of values to insert
//        ContentValues cvalues = new ContentValues();
//        // assign values for each col
//        //cvalues.put(DLOG_ID, dLog.getID());
//        cvalues.put(DLOG_HOURS, dLog.getHours());
//        cvalues.put(DLOG_DATE, dLog.getDate());
//        cvalues.put(DLOG_DAY, dLog.getDay());
//        cvalues.put(DLOG_ROADTYPE, dLog.getRoadType());
//        cvalues.put(DLOG_WEATHER, dLog.getWeather());
//        //add to course table in database
//        return db.insert(DLOGS_TABLE, null, cvalues);
//    }
//
//    /** remove selected row */
//    public boolean removeDriveLog(long did) {
//        return db.delete(DLOGS_TABLE, "DLOG_ID="+did, null) > 0;
//    }
//
//    /** update log */
//    public boolean updateDriveLog(long did, String d, String r, String w) {
//        ContentValues cvalue = new ContentValues();
//        cvalue.put(DLOG_DAY, d);
//        cvalue.put(DLOG_ROADTYPE, r);
//        cvalue.put(DLOG_WEATHER, w);
//        return db.update(DLOGS_TABLE, cvalue, DLOG_ID+"="+did, null) > 0;
//    }
//
//    /** update string-type fields */
//    public boolean updateField(long did, int field, String wh) {
//        ContentValues cvalue = new ContentValues();
//        cvalue.put(DLOG_COLS[field], wh);
//        return db.update(DLOGS_TABLE, cvalue, DLOG_ID+"="+did, null) > 0;
//    }
//
//    /** update hour*/
////    public boolean updateHours(long did, float h) {
////        ContentValues cvalue = new ContentValues();
////        cvalue.put(DLOG_HOURS, h);
////        return db.update(DLOGS_TABLE, cvalue, DLOG_ID+"="+did, null) > 0;
////    }
//
//
//    // database query methods
//    public Cursor getAllDriveLogs() {
//        return db.query(DLOGS_TABLE, DLOG_COLS, null, null, null, null, null);
//    }
//
//    public Cursor getDriveLogCursor(long did) throws SQLException {
//        Cursor result = db.query(true, DLOGS_TABLE, DLOG_COLS, DLOG_ID+"="+did, null, null, null, null, null);
//        if ((result.getCount() == 0) || !result.moveToFirst()) {
//            throw new SQLException("No drive log item found for row: " + did);
//        }
//        return result;
//    }
//
//    public DriveLog getDriveLog(long did) throws SQLException {
//        Cursor cursor = db.query(true, DLOGS_TABLE, DLOG_COLS, DLOG_ID+"="+did, null, null, null, null, null);
//        if ((cursor.getCount() == 0) || !cursor.moveToFirst()) {
//            throw new SQLException("No drive log item found for row: " + did);
//        }
//        // must use column indices to get column values
//        //int idIndex = cursor.getColumnIndex(DLOG_ID);
//        //boolean paid = cursor.getColumnIndex(JOB_PAID) == 1;
//
//        //DriveLog(int id, float hours, String date, String day, String roadType, String weather)
//        return new DriveLog(cursor.getFloat(1), cursor.getLong(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
//    }
//
//
//    private static class ReceiptDBhelper extends SQLiteOpenHelper {
//        // SQL statement to create a new database
//        private static final String DB_CREATE = "CREATE TABLE " + DLOGS_TABLE
//                + " (" + DLOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DLOG_HOURS + " FLOAT,"
//                + DLOG_DATE + " LONG, " + DLOG_DAY + " TEXT, " + DLOG_ROADTYPE + " TEXT, " + DLOG_WEATHER + " TEXT);";
//
//        public ReceiptDBhelper(Context context, String name, SQLiteDatabase.CursorFactory fct, int version) {
//            super(context, name, fct, version);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase adb) {
//            // TODO Auto-generated method stub
//            adb.execSQL(DB_CREATE);
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase adb, int oldVersion, int newVersion) {
//            // TODO Auto-generated method stub
//            Log.w("DLogDB", "upgrading from version " + oldVersion + " to "
//                    + newVersion + ", destroying old data");
//            // drop old table if it exists, create new one
//            // better to migrate existing data into new table
//            adb.execSQL("DROP TABLE IF EXISTS " + DLOGS_TABLE);
//            onCreate(adb);
//        }
//    } // ReceiptDBhelper

} // ReceiptDBAdapter
