package com.copy.lms.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.RowCartItemBinding;
import com.copy.lms.model.CartModel;
import com.copy.lms.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<CartModel> arrayList;
    OnRecyclerItemClick onItemClick;

    int px4, px2;
    CardView.LayoutParams layoutParams = new CardView.LayoutParams(
            CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);

    public CartListAdapter(Context context, ArrayList<CartModel> arrayList, OnRecyclerItemClick onItemClick) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClick = onItemClick;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {

        final MyViewHolder h = ((MyViewHolder) vh);
        Picasso.with(context)
                .load(arrayList.get(position).getImage())
                .into(h.binding.cartImg);
        h.binding.txtPrice.setText(BaseAppClass.getPreferences().getCurrancy() + " " + arrayList.get(position).getPrice());

        h.binding.setCartModel(arrayList.get(position));
        h.binding.executePendingBindings();


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowCartItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.llMain.setOnClickListener(this);
            binding.remove.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.llMain:
                    onItemClick.onClick(getLayoutPosition(), Constants.ROW_CLICK);
                    break;
                case R.id.remove:
                    onItemClick.onClick(getLayoutPosition(), Constants.DELETE);
                    break;

            }


        }
    }


}
