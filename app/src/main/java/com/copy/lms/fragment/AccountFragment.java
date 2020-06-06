package com.copy.lms.fragment;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.Nullable;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetSuccess;
import com.copy.lms.basecomponent.BaseFragment;
import com.copy.lms.databinding.FragmentAccountDetailBinding;
import com.copy.lms.model.AccountDetailModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;
import com.copy.lms.util.CustomProgressDialog;
import com.copy.lms.util.Utility;
import com.copy.lms.util.Validation;


public class AccountFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = "Home";
    private AccountDetailModel model;
    private FragmentAccountDetailBinding binding;
    CustomProgressDialog dialog;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_detail, container, false);
        model = new AccountDetailModel();
        binding.setAccountDetailModel(model);

        initView();


        return binding.getRoot();

    }

    private void initView() {
        dialog = new CustomProgressDialog(Constants.PROGRESS_IMAGE, getContext()).createProgressBar();

        setUserData();
        binding.btnSave.setOnClickListener(this);
    }

    private void setUserData() {

        binding.txtFname.setText(BaseAppClass.getPreferences().getUserFirstName());
        binding.txtLname.setText(BaseAppClass.getPreferences().getUserName());
        if (BaseAppClass.getPreferences().getUserMobile().equalsIgnoreCase(""))
            binding.txtPhone.setText("");
        else
            binding.txtPhone.setText(BaseAppClass.getPreferences().getUserMobile());


        binding.txtEmail.setText(BaseAppClass.getPreferences().getUserEmail());
        binding.line1Address.setText(BaseAppClass.getPreferences().getUserLine1());
        binding.txtCity.setText(BaseAppClass.getPreferences().getUserCity());
        binding.txtState.setText(BaseAppClass.getPreferences().getUserState());
        binding.txtCountry.setText(BaseAppClass.getPreferences().getUserCountry());
        binding.txtPin.setText(BaseAppClass.getPreferences().getPincode());
    }


    private boolean validateInput() {
        if (!Validation.isStringEmpty(binding.txtFname.getText().toString())) {
            if (!Validation.isStringEmpty(binding.txtLname.getText().toString())) {
                if (Utility.validate(binding.txtEmail.getText().toString())) {
                    return true;
                } else
                    Toast.makeText(getContext(), getString(R.string.enteremail), Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getContext(), getString(R.string.enterLname), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getContext(), getString(R.string.enterFname), Toast.LENGTH_SHORT).show();


        return false;

    }


    //     Login Api Codeall
    public void signUpAPi() {
        dialog.setCancelable(false);
        dialog.show();
        if (AppConstant.isOnline(getContext())) {
            RetrofitClient.getInstance().getRestOkClient().
                    updateData(binding.txtFname.getText().toString(),
                            binding.txtLname.getText().toString(),
                            binding.txtEmail.getText().toString(),
                            "",
                            binding.txtCity.getText().toString(),
                            binding.txtPhone.getText().toString(),
                            "",
                            "",
                            binding.txtPin.getText().toString(),
                            binding.txtState.getText().toString(),
                            binding.txtCountry.getText().toString(),
                            "",
                            "",
                            signUpcallback);
        } else {
            dialog.hide();
            Toast.makeText(getContext(), getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback signUpcallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            dialog.hide();
            NetSuccess NetSuccess = (NetSuccess) object;
            if (NetSuccess != null) {
                Toast.makeText(getContext(), getString(R.string.updateData), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getContext(), getString(R.string.somthingwrong), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            dialog.hide();
            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                if (validateInput())
                    signUpAPi();
                break;
        }

    }
}

