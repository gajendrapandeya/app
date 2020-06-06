package com.copy.lms.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.copy.lms.R;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.RowSponserItemBinding;
import com.copy.lms.model.SponserModel;
import com.copy.lms.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SponserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<SponserModel> arrayList;
    private OnRecyclerItemClick onItemClick;

    public SponserListAdapter(Context context, ArrayList<SponserModel> arrayList, OnRecyclerItemClick onItemClick) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClick = onItemClick;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sponser_item, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {
        if (vh instanceof MyViewHolder) {

            Picasso.with(context)
                    .load(arrayList.get(position).getImage())
            .into(((MyViewHolder) vh).binding.imgSponser);
            ((MyViewHolder) vh).binding.setSponserModel(arrayList.get(position));
            ((MyViewHolder) vh).binding.executePendingBindings();

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        RowSponserItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.linMain.setOnClickListener(this);
            binding.linMain.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.linMain:
                    onItemClick.onClick(getAdapterPosition(), Constants.ROW_CLICK);
                    break;



            }
        }

        @Override
        public boolean onLongClick(View view) {
            return true;
        }

    }




}
