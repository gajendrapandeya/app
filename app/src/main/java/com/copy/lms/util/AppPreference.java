package com.copy.lms.util;

import android.content.Context;


public class AppPreference {

    private static String SAVE_USER_DATA = "saveUserID";
    private static String TOKENDATA = "tokenData";
    private static String USER_DATA = "userData";
    private static String USER_TOKEN = "userToken";
    private static String IS_USER_TOKEN = "isUserToken";


    public static String disclaimer = "isdisclaimer";
    public static String noti = "noti";
    public static String isRatting = "isRatting";
    public static String tokenDate = "tokenDate";
    public static String LANGUAGECODE = "languageCode";

    private static String INTRO_PREF = "Intro";
    public static String isIntroScreen = "isIntroScreen";
    public static String PROFILEDATA = "profiledata";
    private Context context;

    public static final String USER_ID = "userId";
    public static final String USER_MOBILE = "mobile";
    public static final String USER_EMAIL = "email";
    public static final String USER_NAME = "userName";
    public static final String USER_FIRST_NAME = "firstName";
    public static final String USER_LAST_NAME = "lastName";
    public static final String USER_LINE1 = "line1";
    public static final String USER_IMAGE = "userimage";
    public static final String USER_PINCODE = "pincode";
    public static final String USER_CITY = "city";
    public static final String USER_STATE = "state";
    public static final String USER_COUNTRY = "country";


    public static final String USER_CURRANCY = "currancy";
    public static final String USER_CURRANCY_CODE = "countryCode";
    //Master Sync Data Pref

    private static String ARN_PREF = "arnData";
    public static String token = "token";

    public AppPreference(Context context) {
        this.context = context;
    }

    public boolean getIsIntro() {
        return context.getSharedPreferences(INTRO_PREF, Context.MODE_PRIVATE).getBoolean(isIntroScreen, true);
    }

    public void saveIsIntro(boolean isIntro) {
        (context.getSharedPreferences(INTRO_PREF, Context.MODE_PRIVATE)).edit().putBoolean(isIntroScreen, isIntro).commit();

    }


    public boolean getIsDisclaimer() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getBoolean(disclaimer, true);
    }

    public void saveIsDisclaimer(boolean isIntro) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putBoolean(disclaimer, isIntro).commit();

    }


    public boolean getIsNotification() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getBoolean(noti, true);
    }

    public void saveIsNotification(boolean isIntro) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putBoolean(noti, isIntro).commit();

    }

    public boolean isTokenAvailable() {
        return context.getSharedPreferences(TOKENDATA, Context.MODE_PRIVATE).getBoolean(IS_USER_TOKEN, false);
    }

    public void saveIsTokenAvailable(boolean isToken) {
        (context.getSharedPreferences(TOKENDATA, Context.MODE_PRIVATE)).edit().putBoolean(IS_USER_TOKEN, isToken).commit();

    }

    public void saveToken(String token) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putString(USER_TOKEN, token).commit();
    }

    public String getToken() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getString(USER_TOKEN, "");
    }

    public boolean isUserLoggedIn() {
        if (getToken() == null || getToken().length() == 0)
            return false;
        else
            return true;
    }

    public static void saveFCMToken(Context context, String strEndArn) {
        context.getSharedPreferences(ARN_PREF, Context.MODE_PRIVATE).edit().putString(token, strEndArn).commit();
    }

    public static String getFCMToken(Context context) {
        return context.getSharedPreferences(ARN_PREF, Context.MODE_PRIVATE).getString(token, null);
    }


    public boolean ishShowRatting() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getBoolean(isRatting, true);
    }

    public void saveSHowRatting(boolean ratting) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putBoolean(isRatting, ratting).commit();

    }

    public static void saveTokenDate(Context context, long token) {
        context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).edit().putLong(tokenDate, token).commit();
    }

    public static long getTokenDate(Context context) {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getLong(tokenDate, 0);
    }

    public void saveUserLanguageCode(int token) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putInt(LANGUAGECODE, token).commit();
    }

    public int getUserLanguageCode() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getInt(LANGUAGECODE, Constants.ENGLISH);
    }


    public void saveUserData(String userProfileResult) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putString(PROFILEDATA, userProfileResult).commit();

    }


    public void saveUserId(String id) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putString(USER_ID, id).commit();
    }

    public String getUserId() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getString(USER_ID, null);
    }

    public void saveUserName(String userName) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putString(USER_NAME, userName).commit();
    }

    public String getUserName() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getString(USER_NAME, "");
    }

    public void saveUserFirstName(String frstName) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putString(USER_FIRST_NAME, frstName).commit();
    }

    public String getUserFirstName() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getString(USER_FIRST_NAME, "");
    }

    public void saveUserLastName(String lastName) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putString(USER_LAST_NAME, lastName).commit();
    }

    public String getUserLastName() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getString(USER_LAST_NAME, "");
    }

    public void saveUserEmail(String email) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putString(USER_EMAIL, email).commit();
    }

    public String getUserEmail() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getString(USER_EMAIL, "");
    }

    public void saveUserMobile(String mobile) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putString(USER_MOBILE, mobile).commit();
    }

    public String getUserMobile() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getString(USER_MOBILE, "");

    }

    public void saveUserLine1(String line1) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putString(USER_LINE1, line1).commit();
    }

    public String getUserLine1() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getString(USER_LINE1, "");

    }

    public void saveUserImage(String line2) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putString(USER_IMAGE, line2).commit();
    }

    public String getUserImage() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getString(USER_IMAGE, "");

    }

    public void savePincode(String pincode) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putString(USER_PINCODE, pincode).commit();
    }

    public String getPincode() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getString(USER_PINCODE, "");

    }

    public void saveUserCity(String city) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putString(USER_CITY, city).commit();
    }

    public String getUserCity() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getString(USER_CITY, "");

    }

    public void saveUserState(String state) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putString(USER_STATE, state).commit();
    }

    public String getUserState() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getString(USER_STATE, "");

    }

    public void saveUserCountry(String state) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putString(USER_COUNTRY, state).commit();
    }

    public String getUserCountry() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getString(USER_COUNTRY, "");

    }

    public void saveCurrancyCode(String state) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putString(USER_CURRANCY_CODE, state).commit();
    }

    public String getCurrancyCode() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getString(USER_CURRANCY_CODE, "");

    } public void saveCurrancy(String state) {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().putString(USER_CURRANCY, state).commit();
    }

    public String getCurrancy() {
        return context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE).getString(USER_CURRANCY, "");

    }

    /**
     * Clears the User's Data Stored in Preferences
     */
    public void clearUserPrefs() {
        clearUserData();

    }


    public void clearUserData() {
        (context.getSharedPreferences(SAVE_USER_DATA, Context.MODE_PRIVATE)).edit().clear().commit();
    }


}
