
package com.copy.lms.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetSuccess;
import com.copy.lms.adapter.MessageDetailAdapter;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.ActivityMessageDetailBinding;
import com.copy.lms.model.MessagChatModel;
import com.copy.lms.model.MessageListModel;
import com.copy.lms.model.MessageModel;
import com.copy.lms.net.GetComposeNewMsgTask;
import com.copy.lms.net.OnApiCalled;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;
import com.copy.lms.util.CustomProgressDialog;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MessageDetailActivity extends BaseActivity implements View.OnClickListener {


    public static final String TITLE = "title";
    public static final String NEWMSG = "new";
    private MessageListModel model;
    private ActivityMessageDetailBinding binding;

    String threadId;
    CustomProgressDialog dialog;
    private GetComposeNewMsgTask composeNewMsgTask;
    String recipients;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        BaseAppClass.changeLang(this, BaseAppClass.getPreferences().getUserLanguageCode());
        super.onResume();
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_message_detail);
        model = new MessageListModel();
        model.setArrayList(new ArrayList<MessageModel>());
        binding.setMessageListModel(model);

    }

    @Override
    public void setToolBar() {
        binding.imgBack.setOnClickListener(this);

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
        dialog = new CustomProgressDialog(Constants.PROGRESS_IMAGE, MessageDetailActivity.this).createProgressBar();

        threadId = getIntent().getStringExtra(getString(R.string.id));
        binding.txtRoomName.setText(getIntent().getStringExtra(TITLE));
        binding.txtSend.setOnClickListener(this);
        ArrayList<MessagChatModel> models = getIntent().getParcelableArrayListExtra(getString(R.string.detail));
        if (models != null) {
            model.setChatModelArrayList(models);
        } else {
            model.setChatModelArrayList(new ArrayList<MessagChatModel>());
        }

        initRecycler();


    }


    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new MessageDetailAdapter(MessageDetailActivity.this,
                model.getChatModelArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {


            }
        }));

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if ((recyclerView.getLayoutManager().getChildCount()
                        + ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition())
                        >= recyclerView.getLayoutManager().getItemCount()) {

                }
            }
        });

    }


    @Override
    public void closeActivity() {
        AppConstant.hideKeyboard(this, binding.recyclerView);
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                closeActivity();
                break;
            case R.id.txtSend:
                if (binding.etChatMessage.getText().length() > 0)
                    if (getIntent().getBooleanExtra(NEWMSG, false))
                        callComposenewMsg();
                    else
                        replayMessage();

                break;

        }
    }

    //    replay Message api call
    public void replayMessage() {
        RetrofitClient.getInstance().getRestOkClient().
                replayMassage(threadId,
                        binding.etChatMessage.getText().toString(),
                        callback);
    }

    private final retrofit.Callback callback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetSuccess netAboutData = (NetSuccess) object;
            if (netAboutData != null) {
                MessagChatModel itemModel = new MessagChatModel();
                itemModel.setBody(binding.etChatMessage.getText().toString());
                model.getChatModelArrayList().add(itemModel);
                binding.recyclerView.getAdapter().notifyDataSetChanged();

                binding.etChatMessage.setText("");
                AppConstant.hideKeyboard(MessageDetailActivity.this, binding.etChatMessage);


            } else {
                Toast.makeText(MessageDetailActivity.this, getString(R.string.somthingwrong), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            Toast.makeText(MessageDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };


    private void callComposenewMsg() {
        dialog.setCancelable(false);
        dialog.show();
        composeNewMsgTask = new GetComposeNewMsgTask(MessageDetailActivity.this, threadId, binding.etChatMessage.getText().toString(), true, false, new OnApiCalled() {
            @Override
            public void onSuccess(String response) {
                dialog.hide();
                MessagChatModel itemModel = new MessagChatModel();
                itemModel.setBody(binding.etChatMessage.getText().toString());
                model.getChatModelArrayList().add(itemModel);
                binding.recyclerView.getAdapter().notifyDataSetChanged();

                binding.etChatMessage.setText("");
                AppConstant.hideKeyboard(MessageDetailActivity.this, binding.etChatMessage);

            }

            @Override
            public void onError(String strError) {
                dialog.hide();
            }
        });
    }


}

