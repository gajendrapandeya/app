package com.copy.lms.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.ArrayList;


public class BaseDatabaseAdapter {


    private static final int DATABASE_VERSION = 2;
    public static Context ourContext;
    public SQLiteDatabase ourDatabase;
    public DbHelper ourHelper;


    public static final String DATABASE_NAME = "LMS";

    //Table Names
    public static final String TABLE_CART = "cart";
    public static final String TABLE_ANSWER = "answer";


    //Column Names
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_IMGURL = "imgurl";
    public static final String KEY_THUMBIMAGE = "thumbImage";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_CREATEDAT = "created_at";
    public static final String KEY_UPDATEDAT = "updated_at";
    public static final String KEY_TYPE = "type";
    public static final String KEY_PRICE = "price";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_STATUS = "status";
    public static final String KEY_TOTAL = "total";
    public static final String KEY_SUBTOTAL = "subtotal";

    public static final String KEY_TEST_ID = "testId";
    public static final String KEY_RESULT_ID = "resultId";





    public static final int TRUE = 1;
    public static final int FALSE = 0;


    public BaseDatabaseAdapter(Context c) {
        ourContext = c;
    }

    /**
     * function to delete database
     */
    public final static void deleteDB() {
        ourContext.deleteDatabase(DATABASE_NAME);
    }

    public BaseDatabaseAdapter open() throws SQLException {
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
    }

    /**
     * function to reset tables selected by user
     *
     * @param strTableName
     */
    public void resetTable(String strTableName) {
        ourDatabase.execSQL("DELETE FROM " + strTableName);
    }


    public static class DbHelper extends SQLiteOpenHelper {


        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(final SQLiteDatabase db) {
            createTableCart(db);
            createAnswerCart(db);

        }

        private void createTableCart(SQLiteDatabase db) {

            StringBuilder strBulder = new StringBuilder();
            strBulder.append("CREATE TABLE ");
            strBulder.append(TABLE_CART);
            strBulder.append('(');
            strBulder.append(KEY_ID + " TEXT, ");
            strBulder.append(KEY_NAME + " TEXT, ");
            strBulder.append(KEY_IMGURL + " TEXT, ");
            strBulder.append(KEY_THUMBIMAGE + " TEXT, ");
            strBulder.append(KEY_DESCRIPTION + " TEXT, ");
            strBulder.append(KEY_TYPE + " TEXT, ");
            strBulder.append(KEY_PRICE + " TEXT, ");
            strBulder.append(KEY_TOTAL + " TEXT, ");
            strBulder.append(KEY_SUBTOTAL + " TEXT, ");
            strBulder.append(KEY_CATEGORY + " TEXT, ");
            strBulder.append(KEY_CREATEDAT + " TEXT, ");
            strBulder.append(KEY_UPDATEDAT + " TEXT, ");
            strBulder.append(KEY_STATUS + " INTEGER ");
            strBulder.append(')');
            db.execSQL(strBulder.toString());

        }

        private void createAnswerCart(SQLiteDatabase db) {

            StringBuilder strBulder = new StringBuilder();
            strBulder.append("CREATE TABLE ");
            strBulder.append(TABLE_ANSWER);
            strBulder.append('(');
            strBulder.append(KEY_TEST_ID + " TEXT, ");
            strBulder.append(KEY_RESULT_ID + " TEXT ");
            strBulder.append(')');
            db.execSQL(strBulder.toString());

        }




        @Override
        public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
                              final int newVersion) {
        }
    }

    /**
     * Clears Data from the Database(All Tables including Chat Database and Tables)
     */
    public void clearTableData() {
        try {
            open();
            resetTable(TABLE_CART);
            close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }


    }


    @SuppressWarnings("unused")
    public static void exportDB(Context ctx) {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + ctx.getPackageName()
                + "/databases/" + DATABASE_NAME;
        String backupDBPath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/DiamondDiary/DatabaseBackup";
        String dbName = DATABASE_NAME;
        File dir = null;

        dir = new File(backupDBPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(dir, dbName);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<String> getIds(String strTableName, String columnName, String whereClause) {
        ArrayList<String> ids = new ArrayList<>();
        String query = "Select " + columnName + " from " + strTableName;
        if (whereClause != null)
            query += " " + whereClause;

        Cursor c = ourDatabase.rawQuery(query, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {

                if (c.getString(0) != null && c.getString(0).trim().length() > 0)
                    ids.add(c.getString(0));

                c.moveToNext();
            }

        }
        c.close();
        return ids;
    }


}