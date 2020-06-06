package com.copy.lms.fragment;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.copy.lms.R;
import com.copy.lms.adapter.InvoiceListAdapter;
import com.copy.lms.basecomponent.BaseFragment;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.FragmentInvoiceBinding;
import com.copy.lms.model.InvoiceListModel;
import com.copy.lms.model.InvoiceModel;

import java.util.ArrayList;


public class InvoiceFragment extends BaseFragment {
    public static final String TAG = "Home";
    private InvoiceListModel model;
    private FragmentInvoiceBinding binding;



    public InvoiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_invoice, container, false);
        model = new InvoiceListModel();
        model.setArrayList(new ArrayList<InvoiceModel>());
        binding.setInvoiceListModel(model);
        
        initView();


        return binding.getRoot();

    }

    private void initView() {
        initrecyclerView();
        fillArrayList();

    }


    /*
     *  initialize Reacycler view
     */
    private void initrecyclerView() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        binding.recyclerView.setAdapter(new InvoiceListAdapter(getContext(),
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(final int position, int type) {

            }
        }));
    }


    private void fillArrayList() {


        String[] name = {"Current Affairs", "Videos", "Books",
                "Flash Cards", "Oneliner GK", "True / False",
                "Whos who", "GK Quiz", "Online Test"


        };

        InvoiceModel homeModel;


        for (int i = 0; i < name.length; i++) {
            homeModel = new InvoiceModel();
            homeModel.setfName(name[i]);
            model.getArrayList().add(homeModel);


        }
        binding.recyclerView.getAdapter().notifyDataSetChanged();

    }
}

