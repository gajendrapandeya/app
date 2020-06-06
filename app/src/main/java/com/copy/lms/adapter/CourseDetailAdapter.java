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
import com.copy.lms.databinding.RowCoursedetailItemBinding;
import com.copy.lms.model.CourseDetailModel;
import com.copy.lms.util.Constants;

import java.util.ArrayList;

public class CourseDetailAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<CourseDetailModel> arrayList;
    OnRecyclerItemClick onItemClick;

    int px4, px2;
    CardView.LayoutParams layoutParams = new CardView.LayoutParams(
            CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);

    public CourseDetailAdapter(Context context, ArrayList<CourseDetailModel> arrayList, OnRecyclerItemClick onItemClick) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClick = onItemClick;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_coursedetail_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {

        final MyViewHolder h = ((MyViewHolder) vh);
        h.binding.setCourseDetailModel(arrayList.get(position));
        h.binding.title.setText(arrayList.get(position).getTitle());
        h.binding.sequnceNumber.setText(position + 1 + "");
        if (arrayList.get(position).getType().equalsIgnoreCase("test")) {
            h.binding.test.setVisibility(View.VISIBLE);
//            if (arrayList.get(position).getCompleted()) {
//                h.binding.test.setText("Retest");
//            }
        }

        h.binding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowCoursedetailItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.llMain.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.llMain:
                    onItemClick.onClick(getLayoutPosition(), Constants.ROW_CLICK);
                    break;
                case R.id.test:
                    if (arrayList.get(getAdapterPosition()).getCompleted())
                        onItemClick.onClick(getLayoutPosition(), Constants.CLICK_RETEST);
                    else
                        onItemClick.onClick(getLayoutPosition(), Constants.CLICK_TEST);

                    break;
            }
        }
    }
}
