package com.copy.lms.net;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.copy.lms.Config;
import com.copy.lms.R;
import com.copy.lms.db.CartDbAdapter;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;
import com.copy.lms.util.CustomProgressDialog;
import com.copy.lms.util.JSONData;
import com.copy.lms.util.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GetPromoSyncTask {

    private Context context;
    private String coupon;
    private OnApiCalled apiCalled;
    private boolean isProgress = false, isCancelable;
    private AsyncTask<String, Integer, String> asyncTask;
    CartDbAdapter dbAdapter;

    public GetPromoSyncTask(Context context, String coupon, boolean isProgress, boolean isCancelable, OnApiCalled apiCalled) {
        this.context = context;
        this.coupon = coupon;
        this.apiCalled = apiCalled;
        this.context = context;
        this.isProgress = isProgress;
        this.isCancelable = isCancelable;
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
                String[] data = jParser.sendPostReq(Config.BASE_URL + Config.API_VERSION + "/apply-coupon", getReqObj().toString());
                if (Integer.parseInt(data[0]) == 200) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(data[1]);

                        if (JSONData.getString(jsonObject, "status").equals("success")) {
                            JSONObject obj = JSONData.getJSONObject(jsonObject, "result");
                            JSONArray jsonArray =obj.getJSONArray("data");

                            if (dbAdapter == null)
                                dbAdapter = new CartDbAdapter(context);
                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONObject dataObj =jsonArray.getJSONObject(i);
                                dbAdapter.update(dataObj.getString("id"),dataObj.getString("type"),
                                        dataObj.getString("price"), obj.getString("final_total"),
                                        obj.getString("subtotal"),dataObj.getBoolean("status"));

                            }
                            dbAdapter.open();

                        }
                        else {
                            Toast.makeText(context, context.getString(R.string.validCoupon), Toast.LENGTH_SHORT).show();
                        }

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
                    if (JSONData.getString(jObj, "status").equals("success")) {
                        JSONObject obj = JSONData.getJSONObject(jObj, "result");
                        apiCalled.onSuccess(obj.toString());



                    } else
                        Toast.makeText(context, JSONData.getString(jObj, "message"), Toast.LENGTH_SHORT).show();
                        apiCalled.onError(result);

//                    JSONObject obj = JSONData.getJSONObject(jObj, "data");
//                    if (JSONData.getInt(obj, "count") > JSONData.getJSONArray(obj, "list").length()) {
//                        isProgress = false;
//                        startTask();
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else
                apiCalled.onError(result);
        }

    }

    private JSONObject getReqObj() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (dbAdapter == null)
                dbAdapter = new CartDbAdapter(context);
            dbAdapter.open();
            jsonObject.put("total", dbAdapter.getTotalPrice());
            jsonObject.put("coupon", coupon);
            jsonObject.put("data", dbAdapter.getCartData());

            Log.e("dbAdapter", jsonObject.toString());


            dbAdapter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}