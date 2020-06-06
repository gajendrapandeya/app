package com.copy.lms.util;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetSuccess;
import com.copy.lms.callBack.TwoButtonListener;
import com.copy.lms.net.RetrofitClient;

import java.net.MalformedURLException;
import java.net.URL;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class AlertDialogAndIntents {


    public static void showShortToast(Context context, String strToast) {
        Toast.makeText(context, strToast, Toast.LENGTH_SHORT).show();
    }


    //open webview
    public static void openUrl(Context context, String strUrl) {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(strUrl));
            context.startActivity(browserIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void shareApp(Context context, String strUrl) {
        String s1 = "http://play.google.com/store/apps/details?id="
                + AppConstant.getPackageName(context);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, s1);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }


    public static void openPlayStore(Context context) {
        final String appPackageName = AppConstant.getPackageName(context);
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appPackageName)));
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id="
                            + appPackageName)));
        }
    }


    public static void closeDialog(final Context context, TwoButtonListener btnListener) {
        final TwoButtonListener listener = btnListener;
        final Dialog alertPopup = new Dialog(context);
        alertPopup.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertPopup.setContentView(R.layout.close_popup);
        alertPopup.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        ImageView imgClose = (ImageView) alertPopup.findViewById(R.id.imgClose);
        TextView txtTitle = (TextView) alertPopup.findViewById(R.id.txtTitle);
        LinearLayout llYes = (LinearLayout) alertPopup.findViewById(R.id.llYes);
        LinearLayout llNo = (LinearLayout) alertPopup.findViewById(R.id.llNo);
        FrameLayout adHolder = (FrameLayout) alertPopup.findViewById(R.id.adHolder);
//        if (BaseAppClass.getPreferences().isAdShow())
//            loadNativeAd(context, adHolder);

        llYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.positiveClick();
                alertPopup.dismiss();
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.negativeClick();
                alertPopup.dismiss();

            }
        });
        llNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertPopup.dismiss();
            }
        });


        alertPopup.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

        alertPopup.show();
    }


    public static void forgotDialog(final Context context, TwoButtonListener btnListener) {
        final TwoButtonListener listener = btnListener;
        final Dialog alertPopup = new Dialog(context);
        alertPopup.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertPopup.setContentView(R.layout.popup_forgot);
        alertPopup.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        LinearLayout reset = (LinearLayout) alertPopup.findViewById(R.id.reset);
        TextView ctSignUp = (TextView) alertPopup.findViewById(R.id.ctSignUp);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.positiveClick();
                alertPopup.dismiss();
            }
        });
        ctSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.negativeClick();
                alertPopup.dismiss();
            }
        });


        alertPopup.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

        alertPopup.show();
    }

    public static void subscribeNow(final Context context, TwoButtonListener btnListener) {
        final TwoButtonListener listener = btnListener;
        final Dialog alertPopup = new Dialog(context);
        alertPopup.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertPopup.setContentView(R.layout.subscribe_popup);
        alertPopup.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        final EditText email = (EditText) alertPopup.findViewById(R.id.email);
        LinearLayout llSubScribe = (LinearLayout) alertPopup.findViewById(R.id.llSubScribe);
        llSubScribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    if (Validation.isEmailValid(email.getText().toString()))
                    subscibeAPi(email.getText().toString(),context);
                    else
                        Toast.makeText(context, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                    listener.positiveClick();
                alertPopup.dismiss();
            }
        });


        alertPopup.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

        alertPopup.show();
    }


    //     Login Api Codeall
    public static void subscibeAPi(String email,Context context) {
        RetrofitClient.getInstance().getRestOkClient().
                getSubscribe(email,

                        callback);

    }

    private static final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetSuccess netSuccess = (NetSuccess) object;
            if (netSuccess != null) {

            } else {
            }

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    public static String extractYoutubeId(String url) throws MalformedURLException {
        String query = new URL(url).getQuery();
        String[] param = query.split("&");
        String id = null;
        for (String row : param) {
            String[] param1 = row.split("=");
            if (param1[0].equals("v")) {
                id = param1[1];
            }
        }
        return id;
    }


}