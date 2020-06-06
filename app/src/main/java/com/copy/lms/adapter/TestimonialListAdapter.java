package com.copy.lms.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.copy.lms.R;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.RowTestimonialItemBinding;
import com.copy.lms.model.TestimonialModel;

import java.util.ArrayList;


public class TestimonialListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<TestimonialModel> arrayList;
    private OnRecyclerItemClick onItemClick;

    public TestimonialListAdapter(Context context, ArrayList<TestimonialModel> arrayList, OnRecyclerItemClick onItemClick) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClick = onItemClick;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_testimonial_item, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {
        if (vh instanceof MyViewHolder) {

            ((MyViewHolder) vh).binding.setTestimonialModel(arrayList.get(position));
            ((MyViewHolder) vh).binding.executePendingBindings();

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        RowTestimonialItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.llMain.setOnClickListener(this);
            binding.llMain.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.llMain:



            }
        }

        @Override
        public boolean onLongClick(View view) {
            return true;
        }

    }




}
