package com.copy.lms.activity;

import android.app.Activity;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.copy.lms.BaseAppClass;
import com.copy.lms.Config;
import com.copy.lms.Payment.PayActivity;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetSuccess;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.databinding.ActivityPaymentoptionBinding;
import com.copy.lms.db.BaseDatabaseAdapter;
import com.copy.lms.db.CartDbAdapter;
import com.copy.lms.model.PaymentOptionModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;
import com.copy.lms.util.CustomProgressDialog;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.sql.SQLException;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class PaymentOptionActivity extends BaseActivity implements View.OnClickListener {
    private PaymentOptionModel model;
    private ActivityPaymentoptionBinding binding;


    public static final int PAYPAL_REQUEST_CODE = 123;

    private String paymentAmount;
    PayPalPayment thingToBuy;

    String orderId;
    CustomProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    protected void onResume() {
        BaseAppClass.changeLang(this, BaseAppClass.getPreferences().getUserLanguageCode());
        super.onResume();
    }
    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_paymentoption);
        model = new PaymentOptionModel();

    }

    @Override
    public void setToolBar() {
        binding.included.txtTitle.setText(getString(R.string.payment));
        binding.included.imgBack.setOnClickListener(this);

    }

    @Override
    public void initViews() {
        dialog = new CustomProgressDialog(Constants.PROGRESS_IMAGE, PaymentOptionActivity.this).createProgressBar();
        orderId = getIntent().getStringExtra("orderId");
        PayPalConfiguration config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(Config.PAYPAL_CLIENT_ID)
                .merchantName("LMS")
                .merchantPrivacyPolicyUri(
                        Uri.parse("https://www.example.com/privacy"))
                .merchantUserAgreementUri(
                        Uri.parse("https://www.example.com/legal"));
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);


        // Create the PaymentIntent on your backend
        //        myBackendApiClient.createPaymentIntent(100, "usd", "off_session",
        //                new ApiResultCallback<String>() {
        //                    @Override
        //                    public void onSuccess(@NonNull String clientSecret) {
        //                        // Hold onto the clientSecret for Step 4
        //                    }
        //
        //                    @Override
        //                    public void onError(@NonNull Exception e) {
        //                    }
        //                });

        paymentAmount = getIntent().getStringExtra("total");
        binding.txtTotal.setText(BaseAppClass.getPreferences().getCurrancy() + " " + String.format("%.2f", Double.parseDouble(paymentAmount)));
        binding.imgPaypal.setOnClickListener(this);
        binding.imgOffLine.setOnClickListener(this);
        binding.imgStripe.setOnClickListener(this);
    }

    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                closeActivity();
                break;
            case R.id.imgPaypal:
                getPayment();
                break;
            case R.id.imgStripe:

                Intent intent = new Intent(PaymentOptionActivity.this, PayActivity.class);
                intent.putExtra(PayActivity.PAY_PRICE, paymentAmount);
                intent.putExtra(PayActivity.PAY_NAME, orderId);
                startActivity(intent);
                //                callorderConformation();
                break;

            case R.id.imgOffLine:
                setPaymentStatus("success", "3", orderId, "");

                break;

        }
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }


    /*paypal payment*/

    private void getPayment() {
        //Getting the amount from editText
        thingToBuy = new PayPalPayment(new BigDecimal(paymentAmount), "USD",
                "HeadSet", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(PaymentOptionActivity.this,
                PaymentActivity.class);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);
                        startActivity(new Intent(this, PaymentOptionActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount));

                        setPaymentStatus("success", "2", orderId, "");
                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

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

            Toast.makeText(PaymentOptionActivity.this, getString(R.string.orderDone), Toast.LENGTH_SHORT).show();

            CartDbAdapter cartDbAdapter = new CartDbAdapter(PaymentOptionActivity.this);
            try {
                cartDbAdapter.open();
                cartDbAdapter.resetTable(BaseDatabaseAdapter.TABLE_CART);
                cartDbAdapter.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(PaymentOptionActivity.this, MainActivity.class);
            startActivity(intent);
            finish();


        }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();
        }
    };

}
