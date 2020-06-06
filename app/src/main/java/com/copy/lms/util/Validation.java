package com.copy.lms.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class Validation {
    private Context context;


    private static Validation mInstance;

    public static Validation getInstance(Context context) {
        if (mInstance == null)
            mInstance = new Validation(context);
        return mInstance;
    }

    public Validation(Context context) {
        this.context = context;
    }


    public static boolean isStringEmpty(String editText) {
        if (null == editText)
            return true;
        else
            return editText.trim().isEmpty();
    }

    public boolean isChecked(boolean check) {
        return check;
    }


    public static boolean isEmailValid(String strEmail) {
        if (null != strEmail && !isStringEmpty(strEmail) && checkEmail(strEmail)) {
            return true;
        } else
            return false;
    }

    public static boolean isPasswordValid(String strEmail) {
        if (null != strEmail && !isStringEmpty(strEmail) && checkPasswordLength(strEmail)) {
            return true;
        } else
            return false;
    }

    public void showMessageAndSetCursor(EditText editText, String messageToDisplay) {

        if (null != messageToDisplay && messageToDisplay.length() > 0) {
            AlertDialogAndIntents.showShortToast(context, messageToDisplay);
        }
        requestFocusAndSetCursors(editText);

    }

    public void showMessageAndSetCursorText(TextView editText, String messageToDisplay) {

        if (null != messageToDisplay && messageToDisplay.length() > 0) {
            AlertDialogAndIntents.showShortToast(context, messageToDisplay);
        }
        requestFocusAndSetCursorsText(editText);

    }

    public void showMessageAndSetCursorForChech(CheckBox checkBox, String messageToDisplay) {

        if (null != messageToDisplay && messageToDisplay.length() > 0) {
            AlertDialogAndIntents.showShortToast(context, messageToDisplay);
        }
        requestFocusAndSetCursorsForCheck(checkBox);

    }

    public boolean isMobileValid(String strMobile) {
        String mobile = strMobile.trim();
//        if (null != mobile && mobile.length() >= 6 && mobile.length() <= 14) {
        if (null != mobile && isStringMobile(mobile)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isStringMobile(String isMobile) {
        if (isMobile.matches("^[0-9]{6,14}")) {
            return true;
        } else {
            return false;
        }

    }
    public static boolean validateString(String object) {
        boolean flag = false;
        if (object != null && object.equalsIgnoreCase("false") != true &&
                object.equalsIgnoreCase("null") != true &&
                object.trim().length() > 0 &&
                object.equalsIgnoreCase("NV") != true) {
            flag = true;
        }
        return flag;
    }


    //    public Validation(Context context) {
//        this.context = context;
//    }
//
//
//    //EditText Empty Validation
//    public boolean isEditTextStringEmpty(EditText editText) {
//        boolean isEmpty = editText.getText().toString().isEmpty();
//        if (isEmpty) {
//            AlertDialogAndIntents.showShortToast(context, context.getString(R.string.empty_msg,
//                    editText.getTag().toString(), editText.getTag().toString()));
//            requestFocusAndSetCursors(editText);
//        }
//        return isEmpty;
//    }
//
//    //Email Validation
//    public boolean isEmailValid(EditText editText) {
//        if (isEditTextStringEmpty(editText)) {
//            return false;
//        } else if (!checkEmail(editText.getText().toString())) {
//            AlertDialogAndIntents.showShortToast(context, context.getString(R.string.invalid_email));
//            requestFocusAndSetCursors(editText);
//            return false;
//        } else
//            return true;
//    }
//
    //Email Format Validation
    public static boolean checkEmail(String strEmail) {
        return Patterns.EMAIL_ADDRESS.matcher(strEmail).matches();
    }

    //
    //Setting Focus and setting Cursor
    public void requestFocusAndSetCursors(final EditText editText) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
                editText.setSelection(editText.getText().length());
            }
        }, 100);
    }

    //Setting Focus and setting Cursor
    public void requestFocusAndSetCursorsText(final TextView editText) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
            }
        }, 100);
    }

    public void requestFocusAndSetCursorsForCheck(final CheckBox checkBox) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                checkBox.requestFocus();
                checkBox.setChecked(checkBox.isChecked());
            }
        }, 100);
    }

    //
//    //Password Validation
//    public boolean isPasswordValid(EditText editText) {
//        if (isEditTextStringEmpty(editText)) {
//            return false;
//        } else if (!checkPasswordLength(editText.getText().toString())) {
//            AlertDialogAndIntents.showShortToast(context, context.getString(R.string.invalid_password,
//                    context.getResources().getInteger(R.integer.password_length)));
//            requestFocusAndSetCursors(editText);
//            return false;
//        } else if (!checkPasswordFormat(editText.getText().toString())) {
//            AlertDialogAndIntents.showShortToast(context, "Password");
//            requestFocusAndSetCursors(editText);
//            return false;
//        } else
//            return true;
//    }
//
    //Password length validation
    private static boolean checkPasswordLength(String strPass) {
        return strPass.trim().length() >= 6;
    }

    //
//    private boolean checkPasswordFormat(String strPass) {
//        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
//        Log.d("Validation", "Pattern.compile(PASSWORD_PATTERN).matcher(strPass).matches():" + Pattern.compile(PASSWORD_PATTERN).matcher(strPass).matches());
//        return Pattern.compile(PASSWORD_PATTERN).matcher(strPass).matches();
//    }
//
//
    //Simple String Empty Validation

    public static boolean isUrl(String strUrl) {
        return Patterns.WEB_URL.matcher(strUrl).matches();
    }

}
