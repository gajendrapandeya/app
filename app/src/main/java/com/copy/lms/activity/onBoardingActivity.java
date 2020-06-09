package com.copy.lms.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.copy.lms.R;
import com.copy.lms.adapter.SliderAdapter;
import com.paypal.android.sdk.payments.LoginActivity;

public class onBoardingActivity extends AppCompatActivity {

    //Widgets
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private Button letsGetStartedBtn;
    private Button btnSkip;

    //var's
    private SliderAdapter sliderAdapter;
    private Animation animation;
    private int currentPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);

        viewPager = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.dots);
        letsGetStartedBtn = findViewById(R.id.get_started_btn);
        btnSkip = findViewById(R.id.skip_btn);


        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipFunction(v);
            }
        });

        letsGetStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next(v);
            }
        });

    }

    private void addDots(int position) {

        dots = new TextView[3];
        dotsLayout.removeAllViews();

        for(int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(24);
            dotsLayout.addView(dots[i]);

        }

        if(dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.colorSelected));
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots(position);
            currentPosition = position;

            if(position == 0) {

                letsGetStartedBtn.setVisibility(View.INVISIBLE);

            } else if(position == 1) {

                letsGetStartedBtn.setVisibility(View.INVISIBLE);
            } else {

                animation = AnimationUtils.loadAnimation(onBoardingActivity.this, R.anim.left_anim);
                letsGetStartedBtn.setAnimation(animation);
                letsGetStartedBtn.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void skipFunction(View view) {

        startActivity(new Intent(onBoardingActivity.this, SignInActivity.class));
        finish();
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    public void next(View view) {

        viewPager.setCurrentItem(currentPosition+1);
        startActivity(new Intent(onBoardingActivity.this, SignInActivity.class));
        finish();
        overridePendingTransition(R.anim.animation, R.anim.animation2);
    }
}
