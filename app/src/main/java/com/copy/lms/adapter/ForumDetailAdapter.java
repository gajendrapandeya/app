package com.copy.lms.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetForumDataResultDiscussionsDataPost;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.RowForumdetailItemBinding;
import com.copy.lms.model.ForumModel;
import com.copy.lms.util.Constants;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ForumDetailAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<ForumModel> arrayList;
    private ArrayList<NetForumDataResultDiscussionsDataPost> postArrayList;
    OnRecyclerItemClick onItemClick;

    int px4, px2;
    CardView.LayoutParams layoutParams = new CardView.LayoutParams(
            CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);

    public ForumDetailAdapter(Context context, ArrayList<ForumModel> arrayList, ArrayList<NetForumDataResultDiscussionsDataPost> postArrayList, OnRecyclerItemClick onItemClick) {
        this.context = context;
        this.arrayList = arrayList;
        this.postArrayList = postArrayList;
        this.onItemClick = onItemClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_forumdetail_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {

        final MyViewHolder h = ((MyViewHolder) vh);
        h.binding.userName.setText(postArrayList.get(position).getUser().getFirst_name()+"");
        h.binding.descussion.setText(Html.fromHtml(postArrayList.get(position).getBody()+""));

        Picasso.with(context)
                .load(postArrayList.get(position).getUser().getImage())
                .into(h.binding.userImage);
        h.binding.setForumModel(arrayList.get(position));
        h.binding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowForumdetailItemBinding binding;

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
            }
        }
    }
}
