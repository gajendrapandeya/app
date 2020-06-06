package com.copy.lms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetMessageData;
import com.copy.lms.ResponceModel.NetMessageDataTeachers;
import com.copy.lms.ResponceModel.NetMessageDataThreads;
import com.copy.lms.adapter.MessageListAdapter;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.ActivityMessagelistBinding;
import com.copy.lms.model.MessagChatModel;
import com.copy.lms.model.MessageListModel;
import com.copy.lms.model.MessageModel;
import com.copy.lms.model.UserModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MassageListActivity extends BaseActivity implements View.OnClickListener {


    public static final String VALUE = "value";
    private MessageListModel model;
    private ActivityMessagelistBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }@Override
    protected void onResume() {
        BaseAppClass.changeLang(this, BaseAppClass.getPreferences().getUserLanguageCode());
        super.onResume();
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_messagelist);
        model = new MessageListModel();
        model.setArrayList(new ArrayList<MessageModel>());
        model.setChatModelArrayList(new ArrayList<MessagChatModel>());
        model.setUserModelArrayList(new ArrayList<UserModel>());
        binding.setMessageListModel(model);

    }

    @Override
    public void setToolBar() {
        if (binding.included.toolbar != null) {
            binding.included.txtTitle.setText(getString(R.string.message));

            binding.included.toolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(binding.included.toolbar);
            binding.included.imgBack.setVisibility(View.VISIBLE);
            binding.included.imgSearch.setVisibility(View.GONE);
            binding.included.imgBack.setOnClickListener(this);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeActivity();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initViews() {
        binding.included.txtTitle.setText(getIntent().getStringExtra(VALUE));
        binding.included.imgBack.setOnClickListener(this);
        binding.fab.setOnClickListener(this);
        initRecycler();
        messageListApi();

    }


    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new MessageListAdapter(MassageListActivity.this,
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                Intent intent = new Intent(MassageListActivity.this, MessageDetailActivity.class);
                intent.putExtra(getString(R.string.id), model.getArrayList().get(position).getPivot().getThread_id() + "");
                intent.putExtra(MessageDetailActivity.NEWMSG, false);
                intent.putExtra(MessageDetailActivity.TITLE, model.getArrayList().get(position).getMessages().get(0).getSender().getFull_name() + "");
                intent.putParcelableArrayListExtra(getString(R.string.detail), model.getArrayList().get(position).getMessages());
                startActivity(intent);
                overridePendingTransition(R.anim.animation, R.anim.animation2);


            }
        }));

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if ((recyclerView.getLayoutManager().getChildCount()
                        + ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition())
                        >= recyclerView.getLayoutManager().getItemCount()) {

                    if (/*model.getCount() > model.getArrayList().size() &&*/ !model.isApiCallActive()) {

                    }
                }
            }
        });

    }

    public void messageListApi() {
        binding.progressBar.setVisibility(View.VISIBLE);

        model.getArrayList().clear();
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    getMessageList("",
                            callback);
        } else {
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            binding.progressBar.setVisibility(View.GONE);

            NetMessageData messageData = (NetMessageData) object;
            if (messageData.getStatus().equalsIgnoreCase("success")) {
                for (int i = 0; i < messageData.getThreads().size(); i++) {
                    model.setThread_id(messageData.getThreads().get(i).getId());
                    fillArrayList(messageData.getThreads());
                    fillArrayListTeacher(messageData.getTeachers());
                    notyFyData();
                }


            } else {
//                Toast.makeText(LanguageActivity.this, "No data Found", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            model.setApiCallActive(false);
            binding.progressBar.setVisibility(View.GONE);


        }
    };

    /*set language list*/

    private void fillArrayList(List<NetMessageDataThreads> items) {
        binding.progressBar.setVisibility(View.GONE);
        model.getArrayList().clear();

        MessageModel itemModel;

        if (items !=null) {


            for (int i = 0; i < items.size(); i++) {
                itemModel = new MessageModel();
                itemModel.setId(items.get(i).getId());
                MessagChatModel messagChatModel;

                ArrayList<MessagChatModel> messagChatModelArrayList = new ArrayList<>();
                for (int j = 0; j < items.get(i).getMessages().size(); j++) {
                    messagChatModel = new MessagChatModel();
                    messagChatModel.setBody(items.get(i).getMessages().get(j).getBody());
                    messagChatModel.setThread_id(items.get(i).getMessages().get(j).getThread_id());
                    messagChatModel.setBody(items.get(i).getMessages().get(j).getBody());
                    messagChatModel.setSender_id(items.get(i).getMessages().get(j).getSender_id());
                    messagChatModel.setCreated_at(items.get(i).getMessages().get(j).getCreated_at());
                    messagChatModel.setSender(items.get(i).getMessages().get(j).getSender());
                    messagChatModelArrayList.add(messagChatModel);


                }
                itemModel.setMessages(messagChatModelArrayList);
                itemModel.setPivot(items.get(i).getPivot());
                model.getArrayList().add(itemModel);


            }
        }
        binding.recyclerView.getAdapter().notifyDataSetChanged();


    }
    private void fillArrayListTeacher(List<NetMessageDataTeachers> items) {
        model.getUserModelArrayList().clear();

        UserModel itemModel;



        for (int i = 0; i < items.size(); i++) {
            itemModel = new UserModel();
            itemModel.setId(items.get(i).getId());
            itemModel.setFirst_name(items.get(i).getFirst_name());
            itemModel.setFull_name(items.get(i).getFull_name());
            itemModel.setImage(items.get(i).getImage());
            itemModel.setLast_name(items.get(i).getLast_name());
            model.getUserModelArrayList().add(itemModel);


        }
        binding.recyclerView.getAdapter().notifyDataSetChanged();


    }

    private void notyFyData() {
        if (model.getArrayList().size() > 0) {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.noData.setVisibility(View.GONE);
        } else {
            binding.recyclerView.setVisibility(View.GONE);
            binding.noData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                closeActivity();
                break;

                case R.id.fab:
                    Intent intent = new Intent(MassageListActivity.this, UserListActivity.class);
                    intent.putParcelableArrayListExtra(getString(R.string.detail), model.getUserModelArrayList());
                    startActivity(intent);
                    overridePendingTransition(R.anim.animation, R.anim.animation2);


                break;

        }
    }
}
