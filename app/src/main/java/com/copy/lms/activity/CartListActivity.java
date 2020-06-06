package com.copy.lms.activity;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.adapter.CartListAdapter;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.ActivityCartlistBinding;
import com.copy.lms.db.CartDbAdapter;
import com.copy.lms.model.CartListModel;
import com.copy.lms.model.CartModel;
import com.copy.lms.net.GetOrderConformationTask;
import com.copy.lms.net.GetPromoSyncTask;
import com.copy.lms.net.OnApiCalled;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;
import com.copy.lms.util.CustomProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

public class CartListActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private CartListModel model;
    private ActivityCartlistBinding binding;

    CartDbAdapter dbAdapter;
    private GetPromoSyncTask getPromoSyncTask;
    String total;
    private GetOrderConformationTask orderConformationTask;

    CustomProgressDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onResume() {
        BaseAppClass.changeLang(this, BaseAppClass.getPreferences().getUserLanguageCode());
        super.onResume();
    }
    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cartlist);
        model = new CartListModel();
        model.setArrayList(new ArrayList<CartModel>());
        binding.setCartListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.cart));

            binding.included.toolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(binding.included.toolbar);
            binding.included.imgBack.setVisibility(View.VISIBLE);
            binding.included.imgSearch.setVisibility(View.GONE);
            binding.included.imgBack.setOnClickListener(this);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeActivity();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initViews() {
        dialog = new CustomProgressDialog(Constants.PROGRESS_IMAGE, CartListActivity.this).createProgressBar();

        binding.txtPromoCode.setOnClickListener(this);
        binding.txtApply.setOnClickListener(this);
        dbAdapter = new CartDbAdapter(CartListActivity.this);
        binding.included.txtTitle.setText(getIntent().getStringExtra(VALUE));
        binding.included.imgBack.setOnClickListener(this);
        binding.btnCheckout.setOnClickListener(this);
        initRecycler();
        fillArrayList(false, "", "","");

    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new CartListAdapter(CartListActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                switch (type) {
                    case Constants.DELETE:
                        removeLocaly(model.getArrayList().get(position).getId());
                        model.getArrayList().remove(position);
                        notyFyDat();
                        binding.recyclerView.getAdapter().notifyDataSetChanged();

                }


            }
        }));


    }

    @Override
    public void closeActivity() {
        AppConstant.hideKeyboard(this, binding.recyclerView);
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                closeActivity();
                break;


            case R.id.txtApply:
                if (binding.edtpromoCode.getText().toString().length() > 0)
                    callPromocodeApi();
                else
                    Toast.makeText(this, getString(R.string.promoMessage), Toast.LENGTH_SHORT).show();
                break;

            case R.id.txtPromoCode:
                startActivity(new Intent(this, OfferListActivity.class));
                overridePendingTransition(R.anim.animation, R.anim.animation2);
                break;
            case R.id.btnCheckout:
                callorderConformation();

                break;

        }
    }


    private void fillArrayList(boolean isTotal, String responceTotal, String subTotal,String tex) {
        model.getArrayList().clear();
        try {
            dbAdapter.open();
            model.getArrayList().addAll(dbAdapter.getCartItem());
            if (!isTotal) {
                total = dbAdapter.getTotalPrice();
                binding.txtTotal.setText(BaseAppClass.getPreferences().getCurrancy() + " " + dbAdapter.getTotalPrice() + "");
                binding.txtsubtotal.setText(BaseAppClass.getPreferences().getCurrancy() + " " + dbAdapter.getTotalPrice() + "");
            } else {
                total = dbAdapter.getFinalTotal();
                binding.txtTotal.setText(BaseAppClass.getPreferences().getCurrancy() + " " + responceTotal + "");
                binding.txtsubtotal.setText(BaseAppClass.getPreferences().getCurrancy() + " " + subTotal + "");
                binding.txtShipping.setText(BaseAppClass.getPreferences().getCurrancy() + " " + tex + "");

            }
            dbAdapter.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        notyFyDat();
        binding.recyclerView.getAdapter().notifyDataSetChanged();

    }
    private void notyFyDat() {
        if (model.getArrayList().size() > 0) {
            binding.llMain.setVisibility(View.VISIBLE);
            binding.noData.setVisibility(View.GONE);
        } else {
            binding.llMain.setVisibility(View.GONE);
            binding.noData.setVisibility(View.VISIBLE);
        }
    }
    private void removeLocaly(String id) {
        try {
            dbAdapter.open();
            dbAdapter.deleteItem(id);
            dbAdapter.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void callPromocodeApi() {
        dialog.setCancelable(false);
        dialog.show();
        getPromoSyncTask = new GetPromoSyncTask(CartListActivity.this, binding.edtpromoCode.getText().toString(), true, false, new OnApiCalled() {
            @Override
            public void onSuccess(String response) {
                try {
                    dialog.hide();
                    JSONObject jsonObject = new JSONObject(response.toString());
                    total =jsonObject.getString("final_total");
                    JSONObject taxObject =jsonObject.getJSONObject("tax_data");
                    fillArrayList(true, jsonObject.getString("subtotal"), jsonObject.getString("final_total")
                    , taxObject.getString("total_tax"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                model.setApiCallActive(false);
            }

            @Override
            public void onError(String strError) {
                dialog.hide();
                model.setApiCallActive(false);
            }
        });
    }


    private void callorderConformation() {
        dialog.setCancelable(false);
        dialog.show();
        orderConformationTask = new GetOrderConformationTask(CartListActivity.this, binding.edtpromoCode.getText().toString(), true, false, new OnApiCalled() {
            @Override
            public void onSuccess(String response) {
                dialog.hide();

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    JSONObject orderObj=jsonObject.getJSONObject("order");

                    Intent buyIntent = new Intent(CartListActivity.this, PaymentOptionActivity.class);
                    buyIntent.putExtra("total", jsonObject.getString("final_total"));
                    buyIntent.putExtra("orderId", orderObj.getString("id"));
                    startActivity(buyIntent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(String strError) {
                dialog.hide();
            }
        });
    }
}
