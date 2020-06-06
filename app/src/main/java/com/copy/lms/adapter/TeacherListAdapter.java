package com.copy.lms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.copy.lms.R;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.RowTeacherItemBinding;
import com.copy.lms.model.TeacherModel;
import com.copy.lms.util.AlertDialogAndIntents;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class TeacherListAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<TeacherModel> arrayList;
    OnRecyclerItemClick onItemClick;

    int px4, px2;
    CardView.LayoutParams layoutParams = new CardView.LayoutParams(
            CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);

    public TeacherListAdapter(Context context, ArrayList<TeacherModel> arrayList, OnRecyclerItemClick onItemClick) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClick = onItemClick;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_teacher_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {

        final MyViewHolder h = ((MyViewHolder) vh);
        h.binding.teacherName.setText(arrayList.get(position).getFull_name());
        h.binding.teacherposition.setText(arrayList.get(position).getWorkpos());
        Picasso.with(context)
                .load(arrayList.get(position).getImage())
                .into(h.binding.teacheImage);
        h.binding.setTeacherModel(arrayList.get(position));
        h.binding.executePendingBindings();


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowTeacherItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.llMain.setOnClickListener(this);
            binding.facebook.setOnClickListener(this);
            binding.tweeter.setOnClickListener(this);
            binding.linkedin.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
//                case R.id.llMain:
//                    onItemClick.onClick(getLayoutPosition(), Constants.ROW_CLICK);
//                    break;
                case R.id.facebook:
                    AlertDialogAndIntents.openUrl(context, arrayList.get(getAdapterPosition()).getFacebook_link());
                    break;
                case R.id.tweeter:
                    AlertDialogAndIntents.openUrl(context, arrayList.get(getAdapterPosition()).getTwitter_link());
                    break;
                case R.id.linkedin:
                    AlertDialogAndIntents.openUrl(context, arrayList.get(getAdapterPosition()).getLinkedin_link());
                    break;

            }


        }
    }


}
