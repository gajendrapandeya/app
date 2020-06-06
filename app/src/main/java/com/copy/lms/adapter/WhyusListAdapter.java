package com.copy.lms.adapter;

import android.content.Context;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.copy.lms.R;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.RowWhyusItemBinding;
import com.copy.lms.model.WhyusModel;
import com.copy.lms.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class WhyusListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<WhyusModel> arrayList;
    private OnRecyclerItemClick onItemClick;

    public WhyusListAdapter(Context context, ArrayList<WhyusModel> arrayList, OnRecyclerItemClick onItemClick) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClick = onItemClick;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_whyus_item, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {
        if (vh instanceof MyViewHolder) {
            ((MyViewHolder) vh).binding.setWhyusModel(arrayList.get(position));

            Picasso.with(context)
                    .load(arrayList.get(position).getIcon())
                    .into(((MyViewHolder) vh).binding.icon);
            ((MyViewHolder) vh).binding.executePendingBindings();

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        RowWhyusItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.llMain.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.llMain:
                    onItemClick.onClick(getAdapterPosition(), Constants.ROW_CLICK);


            }
        }

        @Override
        public boolean onLongClick(View view) {
            return true;
        }

    }


}
