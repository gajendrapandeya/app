package com.copy.lms.fragment;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.copy.lms.R;
import com.copy.lms.basecomponent.BaseFragment;
import com.copy.lms.databinding.FragmentAddressBinding;
import com.copy.lms.model.AddressModel;


public class AddressFragment extends BaseFragment {
    public static final String TAG = "Home";
    private AddressModel model;
    private FragmentAddressBinding binding;



    public AddressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_address, container, false);
        model = new AddressModel();
        binding.setAddressModel(model);
        
        initView();


        return binding.getRoot();

    }

    private void initView() {

    }





}

