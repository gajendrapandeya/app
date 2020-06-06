package com.copy.lms.Payment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.copy.lms.Config;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetSuccess;
import com.copy.lms.activity.MainActivity;
import com.copy.lms.db.BaseDatabaseAdapter;
import com.copy.lms.db.CartDbAdapter;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;
import com.copy.lms.util.CustomProgressDialog;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PayActivity extends AppCompatActivity {

    Stripe stripe;
    Integer amount;
    String orderId;
    Card card;
    Token tok;
    public static String PAY_PRICE = "plan_price";
    public static String PAY_NAME = "plan_name";
    CustomProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        Bundle extras = getIntent().getExtras();
        amount = extras.getInt(PAY_PRICE);
        orderId = extras.getString(PAY_NAME);
        dialog = new CustomProgressDialog(Constants.PROGRESS_IMAGE, PayActivity.this).createProgressBar();
        stripe = new Stripe(PayActivity.this, Config.STRIPE_PUBLISHER_KEY);


    }

    public void submitCard(View view) {
        // TODO: replace with your own test key
        CardMultilineWidget cardInputWidget = (CardMultilineWidget) findViewById(R.id.card_input_widget);
        card = cardInputWidget.getCard();

        if (card == null) {
            Toast.makeText(this, "Invalid Card Data", Toast.LENGTH_SHORT).show();
            return;
        }
//        card.setCurrency("usd");
//        card.setName("Theodhor Pandeli");
//        card.setAddressZip("1000");
//        card.setNumber(4242424242424242);
//        card.setExpMonth(12);
//        card.setExpYear(19);
//        card.setCVC("123");

        stripe.createToken(
                card,
                new ApiResultCallback<Token>() {
                    public void onSuccess(Token token) {
                        // Send token to your own web service
                        new StripeCharge(token.getId()).execute();
                    }

                    public void onError(Exception error) {
                        Toast.makeText(PayActivity.this,
                                error.getLocalizedMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        );


    }

    public class StripeCharge extends AsyncTask<String, Void, String> {
        String token;

        public StripeCharge(String token) {
            this.token = token;
        }

        @Override
        protected String doInBackground(String... params) {
            new Thread() {
                @Override
                public void run() {
                    postData(orderId, token, "" + amount);
                }
            }.start();
            return "Done";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setPaymentStatus("success", "1", orderId, "");

            Log.e("Result", s);
        }
    }

    public void postData(String description, String token, String amount) {
        // Create a new HttpClient and Post Header
        try {
            URL url = new URL("[YOUR_SERVER_CHARGE_SCRIPT_URL]");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new NameValuePair("method", "charge"));
            params.add(new NameValuePair("description", description));
            params.add(new NameValuePair("source", token));
            params.add(new NameValuePair("amount", amount));

            OutputStream os = null;

            os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(params));
            writer.flush();
            writer.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }
        Log.e("Query", result.toString());
        return result.toString();
    }

    public class NameValuePair {
        String name, value;

        public NameValuePair(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


    public void setPaymentStatus(String status, String paymentType, String orderId, String transactionId) {
        dialog.setCancelable(false);
        dialog.show();
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    paymentStatus(status,
                            paymentType,
                            orderId,
                            transactionId,
                            "",
                            currancyCallback);
        } else {
            dialog.hide();
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback currancyCallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            dialog.hide();
            NetSuccess netSuccess = (NetSuccess) object;

            Toast.makeText(PayActivity.this, getString(R.string.orderDone), Toast.LENGTH_SHORT).show();

            CartDbAdapter cartDbAdapter = new CartDbAdapter(PayActivity.this);
            try {
                cartDbAdapter.open();
                cartDbAdapter.resetTable(BaseDatabaseAdapter.TABLE_CART);
                cartDbAdapter.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(PayActivity.this, MainActivity.class);
            startActivity(intent);
            finish();


        }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();
        }
    };

}


