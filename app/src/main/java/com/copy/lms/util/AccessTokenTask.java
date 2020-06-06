package com.copy.lms.util;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Scopes;
import com.copy.lms.activity.SignInActivity;

import static com.facebook.FacebookSdk.getApplicationContext;

public  class AccessTokenTask extends AsyncTask<String, Void, String> {
        private GoogleSignInAccount acct;
 
        public AccessTokenTask(GoogleSignInAccount acct) {
            this.acct = acct;
        }
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
 
        @Override
        protected String doInBackground(String... strings) {
            try {
                String scope = "oauth2:" + Scopes.EMAIL + " " + Scopes.PROFILE;

                Log.e("EMAIL",acct.getAccount().name);
                String accessToken = GoogleAuthUtil.getToken(getApplicationContext(), acct.getAccount(), scope, new Bundle());
                Log.d("TOKEN", "accessToken:" + accessToken); //accessToken:ya29.Gl...
                return accessToken;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
 
        @Override
        protected void onPostExecute(String accessToken) {
            super.onPostExecute(accessToken);
            try {
                SignInActivity.getInstance().gotoProfile(accessToken);

                //here you will get accessToken
            } catch (Exception e) {
                e.printStackTrace();
            }
 
        }
    }