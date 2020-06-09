package com.copy.lms.util;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class Constants {

    //Broadcast CustomConstants
    public static final int CONNECTION_TIMEOUT = 30000;

    public static final int PROGRESS_IMAGE = 1;
    public static final int PROGRESS_TEXT = 2;

    public static final int LIMIT = 20;

    public static final int MULTI_SELCETION = 9;
    public static final int ROW_CLICK = 0;
    public static final int ROW_BTN_CLICK = 1;
    public static final int DELETE = 3;
    public static final int BACK = 4;
    public static final int APPLY = 5;
    public static final int CLICK_ENGLISH = 6;
    public static final int CLICK_SPANISH = 7;
    public static final int CLICK_FRANCH = 8;
    public static final int CLICK_AREBIC = 9;
    public static final int CLICK_TEST = 10;
    public static final int CLICK_RETEST = 11;
    //   public static final int CLICK_NEPALI = 12;


    public static final int LANGUAGE_REQUEST_CODE = 0x1;

    //Drawer Type
    public static final int ACCOUNT = 10;
    public static final int HOME = 0;
    public static final int BLOG = 1;
    public static final int COURSE = 2;
    public static final int FORUMS = 3;
    public static final int FAQ = 4;
    public static final int CONTACT = 5;
    public static final int ABOUTUS = 6;
    public static final int LOGIN = 7;
    public static final int FEEDBACK = 8;
    public static final int LANGUAGE = 9;
    public static final int MYPURCHASE = 11;
    public static final int LOGOUT = 12;
    public static final int CART = 13;
    public static final int CERTIFICATE = 14;
    public static final int BUNDLE = 15;
    public static final int MYMESSAGE = 16;



    /*Home Page Constant*/
    public static final String COURSES = "2";
    public static final String TESTIMONIAL = "4";
    public static final String TEACHER = "5";
    public static final String QUESTION = "6";
    public static final String FAQ_QUESTION = "7";
    public static final String BROWSE_COURSE = "8";
    public static final String WHY_US = "9";
    public static final String SPONSORS = "10";
    public static final String CONTACT_US = "11";
    public static final String MESSAGE = "12";
    public static final String BUNDLES = "13";
    public static final String ABOUT = "14";


    public static final int ENGLISH = 1;
    public static final int AREBIC = 2;
    public static final int SPANISH = 3;
    public static final int FRANCH = 4;
//    public static final int NEPALI = 5;


    //Notification
    public static final int interruptNotificationID = 1;


    public static String getPath(Activity context, Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = context.managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }


}
