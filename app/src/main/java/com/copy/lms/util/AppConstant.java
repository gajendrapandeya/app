package com.copy.lms.util;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.appcompat.widget.SearchView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.copy.lms.BaseAppClass;

import org.joda.time.DateTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class AppConstant {


    public static final String FOLDER_NAME = "LMS";
    public static final File imageDir = new File(Environment.getExternalStorageDirectory() + "/" + FOLDER_NAME + "/Images/");
    public static final File videoDir = new File(Environment.getExternalStorageDirectory() + "/" + FOLDER_NAME + "/Videos/");
    public static SimpleDateFormat sdfFormateDate = new SimpleDateFormat("dd MMM yyyy");
    public static SimpleDateFormat dd_MMM = new SimpleDateFormat("HH:mm a");


    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isAndroid4() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isAndroid6() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }


    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
    public static void chkDir() {

        if (!videoDir.exists())
            videoDir.mkdirs();

    }

//Simple String Empty Validation

    public static boolean isUrl(String strUrl) {
        return Patterns.WEB_URL.matcher(strUrl).matches();
    }
    public static void hideKeyboard(Context context, View view) {
        if (context == null) return;
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int convertDPtoPixels(Context context, float dps) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    public static String getDate(String time, SimpleDateFormat dateFormate) {
        Calendar cal = Calendar.getInstance();
        if (time != null && time.length() > 0) {
            try {
                cal.setTimeInMillis(getTimeStamp(time));
                long date = cal.getTimeInMillis();
                return dateFormate.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "";
    }

    public static long getTimeStamp(String strIsoDate) {
        try {
            DateTime dt = new DateTime(strIsoDate);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(dt.getMillis());
            return cal.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return 0;
    }

    public static String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MMM-yyyy ";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public static void broadCastToMediaScanner(Context context, File file) {

        Uri contentUri = Uri.fromFile(file);
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }
    public static void setSearchtextSize(SearchView searchView, int textSize) {
        LinearLayout linearLayout1 = (LinearLayout) searchView.getChildAt(0);
        LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(2);
        LinearLayout linearLayout3 = (LinearLayout) linearLayout2.getChildAt(1);
        AutoCompleteTextView autoComplete = (AutoCompleteTextView) linearLayout3.getChildAt(0);
        autoComplete.setTextSize(textSize);
    }


    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



    public static void closeNotification(Context context) {
        NotificationManager nMgr = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancel(Constants.interruptNotificationID);
    }

    public static String getTwoDecimalDouble(Double toBeTruncated) {
        Double truncatedDouble = new BigDecimal(toBeTruncated)
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        return String.valueOf(truncatedDouble);
    }


    public static void setUnderLine(TextView txtView, String strText) {
        SpannableString content = new SpannableString(strText);
        content.setSpan(new UnderlineSpan(), 0, strText.length(), 0);
        txtView.setText(content);
    }



    public static String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    public static boolean checkFileExists(File target) {
        return target.exists();
    }




    public static void clearData(Activity context, boolean isUserClear) {
        BaseAppClass.getPreferences().clearUserPrefs();
    }



    public static String generateAttachId() {
        return "Attach_" + getCurrentTimeStamp();
    }

    public static long getCurrentTimeStamp() {
        Calendar c = Calendar.getInstance();
        return c.getTimeInMillis();
    }

    //SERVICES constant
    public static final String ALL = "All";




    public static int dpToPx(int dp, Context context) {
        return Math.round(dp * (context.getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static String getRound(Double value, String toConcate) {
        if (value > 0) {
//            return new DecimalFormat("##,##,##,##,##,###").format(new BigDecimal(value).setScale(0, BigDecimal.ROUND_HALF_UP).intValue()) + toConcate;
            return new DecimalFormat("#############").format(new BigDecimal(value).setScale(0, BigDecimal.ROUND_HALF_UP).intValue()) + toConcate;
        } else {
            return 0 + toConcate;
        }
    }



    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }





    // setting edit text focus
    public static void openKeyboard(Context context, EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        int pos = editText.getText().length();
        editText.setSelection(pos);
    }




    public static String getStringAfterSymbol(String strMain, String str, String symbol) {
        if (str != null && symbol != null && str.length() > 0) {
            if (strMain != null && strMain.length() > 0)
                return symbol + str;
            else
                return str;
        } else
            return "";
    }



    public static String getPackageName(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.packageName;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateUUID() {
        return Settings.Secure.getString(BaseAppClass.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    public static boolean isAndroid8() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }
    public static final int VIDEO = 1;
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


    //copy file from original location to app folder
    public static File savefile(Activity context, Uri sourceuri, int type) {
        File destination = null;
        String sourceFilename = null;
        switch (type) {


            case VIDEO:
                sourceFilename = getPath(context, sourceuri);
                destination = new File(videoDir.toString());
                break;

        }
        // String sourceFilename = isVideo ? getPath(sourceuri) : sourceuri.getPath();
        // File destination = new File(isVideo ? AppConstant.videoDir.toString() : AppConstant.documentDir.toString());
        if (!destination.exists()) {
            destination.mkdirs();
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            String extension = "." + getFileExt(sourceFilename);
            File videoDestination = null;
            switch (type) {
                case VIDEO:
                    videoDestination = new File(destination, "Vid_" + System.currentTimeMillis() + ".mp4");
                    break;

            }
            // File videoDestination = new File(destination, isVideo ? "vid_" + System.currentTimeMillis() + ".mp4" : "Doc_" + System.currentTimeMillis() + extension);
            fis = new FileInputStream(sourceFilename);
            fos = new FileOutputStream(videoDestination);
            byte[] buf = new byte[1024];
            fis.read(buf);
            do {
                fos.write(buf);
            } while (fis.read(buf) != -1);
            return videoDestination;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    //Check If SD Card is present or not method
    public static boolean isSDCardPresent() {
        if (Environment.getExternalStorageState().equals(

                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
    public static double mapValueFromRangeToRange(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        return toLow + ((value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow));
    }

    public static double clamp(double value, double low, double high) {
        return Math.min(Math.max(value, low), high);
    }

    public static int darkenColor(int color, float multiplier){
        float[] hsv = new float[3];

        Color.colorToHSV(color, hsv);
        hsv[2] *= multiplier; // value component
        int darkColor = Color.HSVToColor(hsv);
        return darkColor;
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    /** Returns the consumer friendly device name */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }

    public static float dayCount(long oldDate ,long newDate) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        String dateBeforeString = dateFormate(oldDate);
        String dateAfterString = dateFormate(newDate);
        float daysBetween = 0;
        try {
            Date dateBefore = myFormat.parse(dateBeforeString);
            Date dateAfter = myFormat.parse(dateAfterString);
            long difference = dateAfter.getTime() - dateBefore.getTime();
            daysBetween = (difference / (1000 * 60 * 60 * 24));
            /* You can also convert the milliseconds to days using this method
             * float daysBetween =
             *         TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
             */
            System.out.println("Number of Days between dates: " + daysBetween);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return daysBetween;

    }

    private static String dateFormate(long date){
        String longV =String.valueOf(date);
        long millisecond = date;
        // or you already have long value of date, use this instead of milliseconds variable.
        String dateString = android.text.format.DateFormat.format("dd MM yyyy", new Date(millisecond)).toString();
        return dateString;
    }
}