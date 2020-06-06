package com.copy.lms.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.copy.lms.R;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.RowMypurchseItemBinding;
import com.copy.lms.model.MyPurchaseModel;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyPurchaseListAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<MyPurchaseModel> arrayList;
    OnRecyclerItemClick onItemClick;

    int px4, px2;
    CardView.LayoutParams layoutParams = new CardView.LayoutParams(
            CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);

    public MyPurchaseListAdapter(Context context, ArrayList<MyPurchaseModel> arrayList, OnRecyclerItemClick onItemClick) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClick = onItemClick;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_mypurchse_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {

        final MyViewHolder h = ((MyViewHolder) vh);
        h.binding.setMyPurchaseModel(arrayList.get(position));
        Picasso.with(context)
                .load(arrayList.get(position).getImage())
                .into(h.binding.courseBg);
        h.binding.title.setText(arrayList.get(position).getTitle());
        h.binding.date.setText(AppConstant.parseDateToddMMyyyy(arrayList.get(position).getCreated_at()));
        h.binding.executePendingBindings();


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowMypurchseItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.llGame.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.llGame:
                    onItemClick.onClick(getLayoutPosition(), Constants.ROW_CLICK);
                    break;

            }


        }
    }


}
