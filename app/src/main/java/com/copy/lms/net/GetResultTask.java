package com.copy.lms.net;

import android.content.Context;
import android.os.AsyncTask;

import com.copy.lms.Config;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;
import com.copy.lms.util.CustomProgressDialog;
import com.copy.lms.util.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GetResultTask {

    private Context context;
    private String coupon;
    private OnApiCalled apiCalled;
    JSONObject jsonObject;
    private boolean isProgress = false, isCancelable;
    private AsyncTask<String, Integer, String> asyncTask;

    public GetResultTask(Context context, JSONObject jsonObject, boolean isProgress, boolean isCancelable, OnApiCalled apiCalled) {
        this.context = context;
        this.coupon = coupon;
        this.apiCalled = apiCalled;
        this.context = context;
        this.isProgress = isProgress;
        this.isCancelable = isCancelable;
        this.jsonObject = jsonObject;
        startTask();
    }

    public void cancelTask() {
        if (asyncTask != null && asyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            asyncTask.cancel(true);
        }
    }

    private void startTask() {
        if (AppConstant.isOnline(context)) {
            if (asyncTask != null) {
                asyncTask = null;
            }
            asyncTask = new SyncTask().execute();
        }
    }


    class SyncTask extends AsyncTask<String, Integer, String> {
        CustomProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (isProgress) {
                dialog = new CustomProgressDialog(Constants.PROGRESS_IMAGE, context).createProgressBar();
                dialog.setCancelable(isCancelable);
                dialog.show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }


        @Override
        protected String doInBackground(String... strings) {

            JSONParser jParser = new JSONParser();
            try {
                String[] data = jParser.sendPostReq(Config.BASE_URL + Config.API_VERSION + "/save-test", jsonObject.toString());
                if (Integer.parseInt(data[0]) == 200) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(data[1]);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return data[1];
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

            if (null != result && result.length() > 0) {
                JSONObject jObj = null;
                try {
                    jObj = new JSONObject(result);
                    apiCalled.onSuccess(jObj.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else
                apiCalled.onError(result);
        }

    }


}