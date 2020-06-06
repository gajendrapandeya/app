package com.copy.lms.activity;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.copy.lms.R;
import com.copy.lms.basecomponent.BaseActivity;
import com.copy.lms.databinding.ActivityOnlinetestPlayBinding;
import com.copy.lms.databinding.RowOnlinePagerBinding;
import com.copy.lms.db.AnswerDbAdapter;
import com.copy.lms.model.OnLineTestModel;
import com.copy.lms.net.GetResultTask;
import com.copy.lms.net.OnApiCalled;
import com.copy.lms.util.Constants;
import com.copy.lms.util.CustomProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class OnlinePlayActivity extends BaseActivity implements View.OnClickListener {

    ActivityOnlinetestPlayBinding binding;
    //    OnlineTestListModel model;
    private int currentPosition = 0;
    private int currentItem;
    private int itemCount;
    private String testId;
    AnswerDbAdapter dbAdapter;
    public JSONArray array = new JSONArray();
    public JSONObject object;
    public JSONObject jsonObject = new JSONObject();
    CustomProgressDialog dialog;
    private GetResultTask resultTask;

    boolean isTest;
    boolean isComplate;

    @Override
    public void setModelAndBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onlinetest_play);
//        model = new OnlineTestListModel();
//        binding.setOnlineTestListModel(model);


    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }

    @Override
    public void setToolBar() {
        binding.included.imgBack.setVisibility(View.VISIBLE);
        binding.included.txtTitle.setText(getIntent().getStringExtra(getString(R.string.name)));

    }

    @Override
    public void initViews() {
        dbAdapter = new AnswerDbAdapter(OnlinePlayActivity.this);
        dialog = new CustomProgressDialog(Constants.PROGRESS_IMAGE, OnlinePlayActivity.this).createProgressBar();

        binding.included.imgBack.setOnClickListener(this);

        isTest = getIntent().getBooleanExtra(TestActivity.ISTEST, false);
        isComplate = getIntent().getBooleanExtra(TestActivity.ISCOMPLATED, false);
        testId = getIntent().getStringExtra(TestActivity.TESTID);
        itemCount = TestActivity.model.getResponceResponseTestQuestions().size();
        currentItem = currentPosition + 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setPager();

            }
        }, 100);


    }

    private void setCount() {
        currentItem = currentItem + 1;


    }


    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                closeActivity();
                break;


        }
    }


    private void setPager() {
        binding.pager.setAdapter(new productDetailPager(TestActivity.model.getArrayList()));
        binding.pager.setClipToPadding(false);
        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                setCount();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.pager.setCurrentItem(0);

    }

    public class productDetailPager extends PagerAdapter {
        ArrayList<OnLineTestModel> detailData;

        public productDetailPager(ArrayList<OnLineTestModel> detailData) {
            this.detailData = detailData;


        }


        @Override
        public int getCount() {
            return TestActivity.model.getResponceResponseTestQuestions().size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public float getPageWidth(int position) {
            return 1f;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final RowOnlinePagerBinding playOnlinePagerBinding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()),
                    R.layout.row_online_pager, container, false);

            playOnlinePagerBinding.setOnLineTestModel(TestActivity.model.getArrayList().get(position));
            playOnlinePagerBinding.txtQuestion.setText(TestActivity.model.getResponceResponseTestQuestions().get(position).getQuestion());
            playOnlinePagerBinding.txtOne.setText(TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().get(0).getOption_text());
            playOnlinePagerBinding.txtTwo.setText(TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().get(1).getOption_text());
            playOnlinePagerBinding.txtThree.setText(TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().get(2).getOption_text());
            playOnlinePagerBinding.txtFour.setText(TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().get(3).getOption_text());

//            final String answer = model.getArrayList().get(position).getAnswer();
            if (isTest) {
                playOnlinePagerBinding.radioGropup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        int selectedId = playOnlinePagerBinding.radioGropup.getCheckedRadioButtonId();
                        RadioButton button;
                        int count = group.getChildCount();
                        for (int i = 0; i < count; i++) {
                            button = (RadioButton) group.getChildAt(i);
                            if (button.getId() == checkedId) {
                                if (checkedId == selectedId) {
                                    TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().get(i).setSelected(true);
                                } else {
                                    TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().get(i).setSelected(false);

                                }
                            }


                        }
                    }

                });
            } else {
//                playOnlinePagerBinding.txtScore.setText("Your Score is  " +TestActivity.model.getResponceResponseTest_resultScores().get(0).getTest_result());
                playOnlinePagerBinding.radioGropup.setEnabled(false);
                playOnlinePagerBinding.radioGropup.setClickable(false);
                playOnlinePagerBinding.txtOne.setClickable(false);
                playOnlinePagerBinding.txtThree.setClickable(false);
                playOnlinePagerBinding.txtTwo.setClickable(false);
                playOnlinePagerBinding.txtFour.setClickable(false);

                int selectedId = playOnlinePagerBinding.radioGropup.getCheckedRadioButtonId();
                RadioButton button;
                for (int i = 0; i < TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().size(); i++) {
                    if (i == 0) {
                        if (TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().get(0).isSelected()) {
                            playOnlinePagerBinding.txtOne.setChecked(true);
                            if (TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().get(0).getCorrect() == 0) {
                                playOnlinePagerBinding.txtOne.setTextColor(getResources().getColor(R.color.green));
                            } else {
                                playOnlinePagerBinding.txtOne.setTextColor(getResources().getColor(R.color.red));
                            }
                        } else {
                            if (TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().get(0).getCorrect() == 0) {
                                playOnlinePagerBinding.txtOne.setTextColor(getResources().getColor(R.color.green));
                            } else {
                                playOnlinePagerBinding.txtOne.setTextColor(getResources().getColor(R.color.white));
                            }
                        }
                    } else if (i == 1) {
                        if (TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().get(1).isSelected()) {
                            playOnlinePagerBinding.txtTwo.setChecked(true);
                            if (TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().get(1).getCorrect() == 0) {
                                playOnlinePagerBinding.txtTwo.setTextColor(getResources().getColor(R.color.green));
                            } else {
                                playOnlinePagerBinding.txtTwo.setTextColor(getResources().getColor(R.color.red));
                            }
                        } else {
                            if (TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().get(1).getCorrect() == 0) {
                                playOnlinePagerBinding.txtTwo.setTextColor(getResources().getColor(R.color.green));
                            } else {
                                playOnlinePagerBinding.txtTwo.setTextColor(getResources().getColor(R.color.white));
                            }
                        }
                    } else if (i == 2) {
                        if (TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().get(2).isSelected()) {
                            playOnlinePagerBinding.txtThree.setChecked(true);
                            if (TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().get(2).getCorrect() == 0) {
                                playOnlinePagerBinding.txtThree.setTextColor(getResources().getColor(R.color.green));
                            } else {
                                playOnlinePagerBinding.txtThree.setTextColor(getResources().getColor(R.color.red));
                            }
                        } else {
                            if (TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().get(2).getCorrect() == 0) {
                                playOnlinePagerBinding.txtThree.setTextColor(getResources().getColor(R.color.green));
                            } else {
                                playOnlinePagerBinding.txtThree.setTextColor(getResources().getColor(R.color.white));
                            }
                        }
                    } else if (i == 3) {
                        if (TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().get(3).isSelected()) {
                            playOnlinePagerBinding.txtFour.setChecked(true);

                            if (TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().get(3).getCorrect() == 0) {
                                playOnlinePagerBinding.txtFour.setTextColor(getResources().getColor(R.color.green));
                            } else {
                                playOnlinePagerBinding.txtFour.setTextColor(getResources().getColor(R.color.red));
                            }
                        } else {
                            if (TestActivity.model.getResponceResponseTestQuestions().get(position).getOptions().get(3).getCorrect() == 0) {
                                playOnlinePagerBinding.txtFour.setTextColor(getResources().getColor(R.color.green));
                            } else {
                                playOnlinePagerBinding.txtFour.setTextColor(getResources().getColor(R.color.white));
                            }
                        }
                    }
                }
            }


            playOnlinePagerBinding.btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (isTest) {
                        if (playOnlinePagerBinding.radioGropup.getCheckedRadioButtonId() == -1) {
                            Toast.makeText(OnlinePlayActivity.this, "Please select your answer", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int j = 0; j < TestActivity.model.getResponceResponseTestQuestions().get(currentPosition).getOptions().size(); j++) {
                                if (TestActivity.model.getResponceResponseTestQuestions().get(currentPosition).getOptions().get(j).isSelected()) {
                                    try {
                                        jsonObject.put("test_id", testId);
                                        jsonObject.put("question_data", getAnswerData(testId,
                                                TestActivity.model.getResponceResponseTestQuestions().get(currentPosition).getOptions().get(j).getQuestion_id(),
                                                TestActivity.model.getResponceResponseTestQuestions().get(currentPosition).getOptions().get(j).getId()
                                        ));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Log.e("jsonObject", jsonObject.toString());

                                }


                            }


                            if (currentItem == itemCount) {
                                callSubmintQuizConformation();

                            } else {
                                currentPosition++;
                                binding.pager.setCurrentItem(currentPosition, true);


                            }
                        }
                    } else {
                        if (currentItem == itemCount) {
                            playOnlinePagerBinding.btnNext.setText("Close");
                            finish();

                        } else {
                            currentPosition++;
                            binding.pager.setCurrentItem(currentPosition, true);


                        }
                    }
                }

            });

            container.addView(playOnlinePagerBinding.getRoot());
            return playOnlinePagerBinding.getRoot();


        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    public JSONArray getAnswerData(String testId, String questionId, String answerId) {
        object = new JSONObject();
        try {
            object.put("question_id", questionId);
            object.put("ans_id", answerId);
            array.put(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return array;

    }

    private void callSubmintQuizConformation() {
        dialog.setCancelable(false);
        dialog.show();
        resultTask = new GetResultTask(OnlinePlayActivity.this, jsonObject, true, false, new OnApiCalled() {
            @Override
            public void onSuccess(String response) {
                Log.e("response", response);
                String result_id = "";

                dialog.hide();

                Intent intent = new Intent(OnlinePlayActivity.this, OnlinePlayActivity.class);
                intent.putExtra(getString(R.string.name), TestActivity.model.getArrayList().get(0).getTitle());
                intent.putExtra(getString(R.string.id), TestActivity.model.getArrayList().get(0).getId());
                intent.putExtra(TestActivity.TESTID, testId);
                intent.putExtra(TestActivity.ISTEST, false);
                intent.putExtra(TestActivity.ISCOMPLATED, true);
                overridePendingTransition(R.anim.animation, R.anim.animation2);
                startActivity(intent);
                finish();

            }

            @Override
            public void onError(String strError) {
                dialog.hide();
            }
        });
    }

}
