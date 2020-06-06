package com.copy.lms.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.copy.lms.model.CartModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class CartDbAdapter extends BaseDatabaseAdapter {


    public CartDbAdapter(Context c) {
        super(c);
    }


    public void insUpdate(String id, String name, String img, String thumbImage,
                          String des, String type, String price, String category, String createdAt, String updatedAt) {
        ContentValues cv;

        cv = new ContentValues();
        cv.put(KEY_ID, id);
        cv.put(KEY_NAME, name);
        cv.put(KEY_IMGURL, img);
        cv.put(KEY_THUMBIMAGE, thumbImage);
        cv.put(KEY_DESCRIPTION, des);
        cv.put(KEY_TYPE, type);
        cv.put(KEY_PRICE, price);
        cv.put(KEY_CATEGORY, category);
        cv.put(KEY_CREATEDAT, createdAt);
        cv.put(KEY_UPDATEDAT, updatedAt);
        cv.put(KEY_PRICE, price);
        cv.put(KEY_STATUS, false);
//        cv.put(KEY_TOTAL, getTotalPrice());
//        cv.put(KEY_SUBTOTAL, getSubTotal());

        long rowCount = ourDatabase.update(TABLE_CART, cv, KEY_ID + " = '" + id + "'", null);
        if (rowCount == 0)
            ourDatabase.insert(TABLE_CART, null, cv);


    }

    public void update(String id, String type, String price, String total, String subTotal, boolean status) {

        final ArrayList<String> idList = getIds(TABLE_CART, KEY_ID, null);

        ContentValues cv;

        cv = new ContentValues();
        cv.put(KEY_ID, id);
        cv.put(KEY_TYPE, type);
        cv.put(KEY_PRICE, price);
        cv.put(KEY_STATUS, status);
        cv.put(KEY_TOTAL, total);
        cv.put(KEY_SUBTOTAL, subTotal);
        if ((idList.contains(id != null ? id : ""))) {
            cv.put(KEY_ID, id);
            long rowCount = ourDatabase.update(TABLE_CART, cv, KEY_ID + " = '" + id + "'", null);


//            long rowCount = ourDatabase.update(TABLE_CART, cv, KEY_ID + " = '" + id + "'", null);
            Log.e("rowCount", rowCount + "");
//           if (rowCount == 0)
//                ourDatabase.insert(TABLE_CART, null, cv);

        }
    }


    public boolean checkIsExist(String id,String type) {
        int count;
        Cursor cursor = ourDatabase.rawQuery("SELECT " + KEY_ID + " FROM " + TABLE_CART + " WHERE "
                        + KEY_ID + "=? "
                        + " AND " + KEY_TYPE + "=? ",
                new String[]{String.valueOf(id),String.valueOf(type)});
        count = cursor.getCount();
        cursor.close();
        return count == 1 ? true : false;
    }


    public int getCount() {
        int count;
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + TABLE_CART, null);
        count = cursor.getCount();
        cursor.close();
        return count;
    }


    public void deleteItem(String vid) {
        ourDatabase.execSQL("DELETE FROM " + TABLE_CART + " WHERE " + KEY_ID + "='" + vid + "'");
    }

    public String getTotalPrice() {
        String totalPrice;
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + TABLE_CART, null);
        double price = 0.0;
        cursor.moveToFirst();
        while (cursor.isAfterLast() != true) {
            price = price + Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_PRICE)));
            cursor.moveToNext();
        }
        totalPrice = String.valueOf(price);
        cursor.close();
        return totalPrice;

    }

    public String getFinalTotal() {
        String totalPrice = null;
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + TABLE_CART, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() != true) {
            totalPrice = cursor.getString(cursor.getColumnIndex(KEY_TOTAL));
            cursor.moveToNext();
        }
        cursor.close();
        return totalPrice;

    }

    public String getSubTotal() {
        String subtotal = null;
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + TABLE_CART, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() != true) {
            subtotal = cursor.getString(cursor.getColumnIndex(KEY_SUBTOTAL));
            cursor.moveToNext();
        }
        cursor.close();
        return subtotal;

    }


    /*get favourite item */
    public ArrayList<CartModel> getCartItem() {
        ArrayList<CartModel> arrayList = new ArrayList<>();
        CartModel CartModel;
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + TABLE_CART, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                CartModel = new CartModel();
                CartModel.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                CartModel.setTitle(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                CartModel.setCourse_image(cursor.getString(cursor.getColumnIndex(KEY_IMGURL)));
                CartModel.setImage(cursor.getString(cursor.getColumnIndex(KEY_IMGURL)));
                CartModel.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                CartModel.setPrice(cursor.getString(cursor.getColumnIndex(KEY_PRICE)));
                CartModel.setCreated_at(cursor.getString(cursor.getColumnIndex(KEY_CREATEDAT)));
                CartModel.setUpdated_at(cursor.getString(cursor.getColumnIndex(KEY_UPDATEDAT)));
                arrayList.add(CartModel);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }


    public JSONArray getCartData() {
        JSONArray array = new JSONArray();
        JSONObject object;
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + TABLE_CART, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() != true) {
            try {
                object = new JSONObject();
                object.put("id", cursor.getString(cursor.getColumnIndex(KEY_ID)));
                object.put("type", cursor.getString(cursor.getColumnIndex(KEY_TYPE)));
                object.put("price", cursor.getString(cursor.getColumnIndex(KEY_PRICE)));
                array.put(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cursor.moveToNext();
        }
        return array;
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
