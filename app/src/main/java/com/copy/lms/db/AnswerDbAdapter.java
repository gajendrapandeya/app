package com.copy.lms.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class AnswerDbAdapter extends BaseDatabaseAdapter {


    public AnswerDbAdapter(Context c) {
        super(c);
    }


    public void insUpdate(String testId, String resultId) {
        ContentValues cv;

        cv = new ContentValues();
        cv.put(KEY_TEST_ID, testId);
        cv.put(KEY_RESULT_ID, resultId);
//        cv.put(KEY_TOTAL, getTotalPrice());
//        cv.put(KEY_SUBTOTAL, getSubTotal());

        long rowCount = ourDatabase.update(TABLE_ANSWER, cv, KEY_TEST_ID + " = '" + testId + "'", null);
        if (rowCount == 0)
            ourDatabase.insert(TABLE_ANSWER, null, cv);


    }
    public String  getResultId(String testId) {
        String resultId = null;
        Cursor c = ourDatabase.rawQuery("SELECT * FROM "
                        + TABLE_ANSWER + " WHERE " + KEY_TEST_ID + "=?",
                new String[]{String.valueOf(testId)});

        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                resultId =c.getString(c.getColumnIndex(KEY_RESULT_ID));
                c.moveToNext();
            }
        }

        c.close();
        return resultId;
    }


    public boolean checkIsExist(String id, String type) {
        int count;
        Cursor cursor = ourDatabase.rawQuery("SELECT " + KEY_TEST_ID + " FROM " + TABLE_ANSWER + " WHERE "
                        + KEY_TEST_ID + "=? "
                        + " AND " + KEY_TYPE + "=? ",
                new String[]{String.valueOf(id), String.valueOf(type)});
        count = cursor.getCount();
        cursor.close();
        return count == 1 ? true : false;
    }


    public int getCount() {
        int count;
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + TABLE_ANSWER, null);
        count = cursor.getCount();
        cursor.close();
        return count;
    }


    public void deleteItem(String vid) {
        ourDatabase.execSQL("DELETE FROM " + TABLE_ANSWER + " WHERE " + KEY_TEST_ID + "='" + vid + "'");
    }


}
