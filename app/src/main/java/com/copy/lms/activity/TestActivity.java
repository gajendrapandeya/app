package com.copy.lms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.copy.lms.BaseAppClass;
import com.copy.lms.R;
import com.copy.lms.ResponceModel.NetTestResponce;
import com.copy.lms.ResponceModel.NetTestResponceResponseTest;
import com.copy.lms.ResponceModel.NetTestResponceResponseTestQuestions;
import com.copy.lms.ResponceModel.NetTestResponceResponseTestQuestionsOptions;
import com.copy.lms.ResponceModel.NetTestResponceResponseTest_result;
import com.copy.lms.ResponceModel.NetTestResponceResponseTest_resultAnswers;
import com.copy.lms.ResponceModel.NetTestResponceResponseTest_resultScore;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.databinding.ActivityTestBinding;
import com.copy.lms.db.AnswerDbAdapter;
import com.copy.lms.model.OnLineTestModel;
import com.copy.lms.model.OnlineTestListModel;
import com.copy.lms.net.RetrofitClient;
import com.copy.lms.util.AppConstant;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TestActivity extends BaseActivity implements View.OnClickListener {
    public static final String TITLE = "title";
    public static OnlineTestListModel model;
    private ActivityTestBinding binding;
    public static final String TESTID = "testId";
    public static final String COURSEID = "courseId";
    public static final String RESULTID = "resultId";
    public static final String ISTEST = "isTest";
    public static final String ISCOMPLATED = "isCOmplated";
    public static final String ISRETEST = "isretest";
    String testId, courseId;
    boolean isTestGiven;
    boolean isComplated,isRetest;
    AnswerDbAdapter dbAdapter;
    String resultId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        BaseAppClass.changeLang(this, BaseAppClass.getPreferences().getUserLanguageCode());
        super.onResume();
    }

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        model = new OnlineTestListModel();
        model.setArrayList(new ArrayList<OnLineTestModel>());
        model.setResponseTestResultAnswers(new ArrayList<NetTestResponceResponseTest_resultAnswers>());
        model.setResponceResponseTestQuestions(new ArrayList<NetTestResponceResponseTestQuestions>());
        model.setResponceResponseTestQuestionsOptions(new ArrayList<NetTestResponceResponseTestQuestionsOptions>());
        model.setResponceResponseTest_resultScores(new ArrayList<NetTestResponceResponseTest_resultScore>());
        binding.setOnlineTestListModel(model);

    }

    @Override
    public void setToolBar() {
        binding.included.txtTitle.setText(getIntent().getStringExtra(TITLE));
        binding.included.imgBack.setOnClickListener(this);
    }

    @Override
    public void initViews() {
        dbAdapter = new AnswerDbAdapter(TestActivity.this);
        binding.btnStart.setOnClickListener(this);
        binding.btnRetest.setOnClickListener(this);
        testId = getIntent().getStringExtra(TESTID);
        resultId = getIntent().getStringExtra(RESULTID);
        courseId = getIntent().getStringExtra(COURSEID);
        isRetest = getIntent().getBooleanExtra(ISRETEST,false);
        isComplated = getIntent().getBooleanExtra(ISCOMPLATED, false);
        if (isRetest)
            getReTestdataApi();
        else
            getTestdataApi();
    }

    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    //     Login Api Codeall
    public void getTestdataApi() {
        binding.progressBar.setVisibility(View.VISIBLE);
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    getTest(testId, courseId,
                            forgotcallback);
        } else {
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    //     Login Api Codeall
    public void getReTestdataApi() {
        binding.progressBar.setVisibility(View.VISIBLE);
        if (AppConstant.isOnline(this)) {
            RetrofitClient.getInstance().getRestOkClient().
                    getRetest(testId, courseId,
                            resultId,
                            retestCallback);
        } else {
            Toast.makeText(this, getString(R.string.search_no_internet_connection), Toast.LENGTH_SHORT).show();

        }
    }

    private final retrofit.Callback forgotcallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetTestResponce netTestResponce = (NetTestResponce) object;
            binding.progressBar.setVisibility(View.GONE);
            if (netTestResponce.getStatus().equalsIgnoreCase("success")) {
                binding.txtTitle.setText(netTestResponce.getResponse().getTest().getTitle());
                binding.txtDes.setText(Html.fromHtml(netTestResponce.getResponse().getTest().getDescription()));

                if (netTestResponce.getResponse().getIs_test_given()) {
                    isTestGiven = true;
                    binding.btnStart.setText("Show Result");
                    resultId = netTestResponce.getResponse().getTest_result().getResult_id();
                    fillResultArrayList(netTestResponce.getResponse().getTest_result());
                    fillArrayList(netTestResponce.getResponse().getTest(), true);

                } else {
                    isTestGiven = false;
                    binding.btnStart.setText("Start");
                    fillArrayList(netTestResponce.getResponse().getTest(), false);

                }


            } else {
                Toast.makeText(TestActivity.this, getString(R.string.somthingwrong), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            binding.progressBar.setVisibility(View.GONE);
            Toast.makeText(TestActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };
    private final retrofit.Callback retestCallback = new retrofit.Callback() {
        @Override
        public void success(Object object, Response response) {
            NetTestResponce netTestResponce = (NetTestResponce) object;
            binding.progressBar.setVisibility(View.GONE);
            binding.btnStart.setText("Start");

            if (netTestResponce.getStatus().equalsIgnoreCase("success")) {
                binding.txtTitle.setText(netTestResponce.getResponse().getTest().getTitle());
                binding.txtDes.setText(Html.fromHtml(netTestResponce.getResponse().getTest().getDescription()));

//                if (netTestResponce.getResponse().getIs_test_given()) {
//                    isTestGiven = true;
//                    resultId = netTestResponce.getResponse().getTest_result().getResult_id();
//                    fillResultArrayList(netTestResponce.getResponse().getTest_result());
//                    fillScore(netTestResponce.getResponse().getTest_result().getScore());
//                    fillArrayList(netTestResponce.getResponse().getTest(), true);
//
//
//                } else {
//                    isTestGiven = false;
                binding.btnStart.setText("Start");
                fillArrayList(netTestResponce.getResponse().getTest(), false);

//                }


            } else {
                Toast.makeText(TestActivity.this, getString(R.string.somthingwrong), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void failure(RetrofitError error) {
            binding.progressBar.setVisibility(View.GONE);
            Toast.makeText(TestActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };


    /*set language list*/

    private void fillArrayList(NetTestResponceResponseTest items, boolean isTest) {
        model.getArrayList().clear();
        binding.progressBar.setVisibility(View.GONE);
        OnLineTestModel itemModel;
        NetTestResponceResponseTestQuestions responseTestQuestions;

        for (int i = 0; i < items.getQuestions().size(); i++) {
            itemModel = new OnLineTestModel();
            responseTestQuestions = new NetTestResponceResponseTestQuestions();

            itemModel.setId(items.getQuestions().get(i).getId());
            itemModel.setTitle(items.getTitle());
            responseTestQuestions.setId(items.getQuestions().get(i).getId());
            responseTestQuestions.setQuestion(items.getQuestions().get(i).getQuestion());
            responseTestQuestions.setScore(items.getQuestions().get(i).getScore());
            NetTestResponceResponseTestQuestionsOptions responseTestQuestionsOptions;
            for (int k = 0; k < items.getQuestions().get(i).getOptions().size(); k++) {
                responseTestQuestionsOptions = new NetTestResponceResponseTestQuestionsOptions();
                responseTestQuestionsOptions.setOption_text(items.getQuestions().get(i).getOptions().get(k).getOption_text());
                responseTestQuestionsOptions.setCorrect(items.getQuestions().get(i).getOptions().get(k).getCorrect());
                responseTestQuestionsOptions.setExplanation(items.getQuestions().get(i).getOptions().get(k).getExplanation());
                responseTestQuestionsOptions.setId(items.getQuestions().get(i).getOptions().get(k).getId());
                responseTestQuestionsOptions.setQuestion_id(items.getQuestions().get(i).getOptions().get(k).getQuestion_id());
                if (isTest) {
                    if (model.getResponseTestResultAnswers().get(i).getOption_id().equalsIgnoreCase(items.getQuestions().get(i).getOptions().get(k).getId()))
                        responseTestQuestionsOptions.setSelected(true);
                    else {
                        responseTestQuestionsOptions.setSelected(false);
                    }
                } else {
                    responseTestQuestionsOptions.setSelected(false);

                }
                responseTestQuestions.getOptions().add(responseTestQuestionsOptions);
            }
            model.getResponceResponseTestQuestions().add(responseTestQuestions);
            model.getArrayList().add(itemModel);


        }

    }

    private void fillResultArrayList(NetTestResponceResponseTest_result items) {
        model.getArrayList().clear();
        binding.progressBar.setVisibility(View.GONE);
        NetTestResponceResponseTest_resultAnswers netTestResponceResponseTest_resultAnswers;

        for (int i = 0; i < items.getAnswers().size(); i++) {
            netTestResponceResponseTest_resultAnswers = new NetTestResponceResponseTest_resultAnswers();

            netTestResponceResponseTest_resultAnswers.setId(items.getAnswers().get(i).getId());
            netTestResponceResponseTest_resultAnswers.setCorrect(items.getAnswers().get(i).getCorrect());
            netTestResponceResponseTest_resultAnswers.setOption_id(items.getAnswers().get(i).getOption_id());
            netTestResponceResponseTest_resultAnswers.setQuestion_id(items.getAnswers().get(i).getQuestion_id());
            netTestResponceResponseTest_resultAnswers.setTests_result_id(items.getAnswers().get(i).getTests_result_id());
            NetTestResponceResponseTestQuestionsOptions responseTestQuestionsOptions;

            model.getResponseTestResultAnswers().add(netTestResponceResponseTest_resultAnswers);


        }

    }

    private void fillScore(NetTestResponceResponseTest_resultScore items) {
        binding.progressBar.setVisibility(View.GONE);
        NetTestResponceResponseTest_resultScore netTestResponceResponseTestResultScore = new NetTestResponceResponseTest_resultScore();

        netTestResponceResponseTestResultScore.setTest_result(items.getTest_result());
        model.getResponceResponseTest_resultScores().add(netTestResponceResponseTestResultScore);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                closeActivity();
                break;

            case R.id.btnRetest:
                Intent intentretest = new Intent(TestActivity.this, TestActivity.class);
                intentretest.putExtra(TestActivity.TITLE, model.getArrayList().get(0).getTitle() + "  ");
                intentretest.putExtra(TestActivity.COURSEID, courseId);
                intentretest.putExtra(TestActivity.RESULTID, resultId);
                intentretest.putExtra(TestActivity.TESTID, testId);
                intentretest.putExtra(ISRETEST, true);
                startActivity(intentretest);
                overridePendingTransition(R.anim.animation, R.anim.animation2);
                finish();
                break;
            case R.id.btnStart:
                if (!isTestGiven) {
                    Intent intent = new Intent(TestActivity.this, OnlinePlayActivity.class);
                    intent.putExtra(getString(R.string.name), model.getArrayList().get(0).getTitle());
                    intent.putExtra(getString(R.string.id), model.getArrayList().get(0).getId());
                    intent.putExtra(TESTID, testId);
                    intent.putExtra(ISTEST, true);
                    overridePendingTransition(R.anim.animation, R.anim.animation2);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(TestActivity.this, OnlinePlayActivity.class);
                    intent.putExtra(getString(R.string.name), model.getArrayList().get(0).getTitle());
                    intent.putExtra(getString(R.string.id), model.getArrayList().get(0).getId());
                    intent.putExtra(TestActivity.TESTID, testId);
                    intent.putExtra(TestActivity.ISTEST, false);
                    overridePendingTransition(R.anim.animation, R.anim.animation2);
                    startActivity(intent);
                    finish();
                }
                break;

        }
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }


}
