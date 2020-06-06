package com.copy.lms.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.copy.lms.R;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.RowHomeItemBinding;
import com.copy.lms.model.HomeModel;
import com.copy.lms.util.Constants;

import java.util.ArrayList;


public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<HomeModel> arrayList;
    private OnRecyclerItemClick onItemClick;

    public HomeAdapter(Context context, ArrayList<HomeModel> arrayList, OnRecyclerItemClick onItemClick) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClick = onItemClick;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_item, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {
        if (vh instanceof MyViewHolder) {
            ((MyViewHolder) vh).binding.fName.setText(arrayList.get(position).getfName());
            ((MyViewHolder) vh).binding.lName.setText(arrayList.get(position).getlName());
            ((MyViewHolder) vh).binding.homeimage.setImageResource(arrayList.get(position).getImage());
            ((MyViewHolder) vh).binding.setHomeModel(arrayList.get(position));

            ((MyViewHolder) vh).binding.executePendingBindings();

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        RowHomeItemBinding binding;

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



            }
        }

        @Override
        public boolean onLongClick(View view) {
            return true;
        }

    }




}
