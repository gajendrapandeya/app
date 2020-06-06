package com.copy.lms.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.copy.lms.R;

public class CustomProgressDialog extends ProgressDialog {
    private Context mContext;
    private ImageView image1;
    private TextSwitcher textSwitcher;
    private boolean canChangeSlider = true;
    private static final long delay = 2500;
    private AnimationsContainer.FramesSequenceAnimation anim;
    String progressArrayText[] = {"Main HeadLine", "Your Message", "New In Technology", "New Articles", "Business News", "What IS New"};
    private int pagerPos = 0;
    private int imageOrText;

//    private static CustomProgressDialog mInstance;

    public CustomProgressDialog(int imageOrText, Context context) {
        super(context);
        this.mContext = context;
        this.imageOrText = imageOrText;

    }

//    public static CustomProgressDialog getInstance(Context context) {
//        if (mInstance == null)
//            mInstance = new CustomProgressDialog(context);
//        return mInstance;
//    }

    public CustomProgressDialog(Context context, int imageOrText, int theme) {
        super(context, theme);
        this.mContext = context;
        this.imageOrText = imageOrText;
    }

    public CustomProgressDialog createProgressBar() {
        CustomProgressDialog dialog = new CustomProgressDialog(mContext, imageOrText, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        return dialog;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_bar);

        image1 = (ImageView) findViewById(R.id.pbImg1);

        textSwitcher = (TextSwitcher) findViewById(R.id.txtSwicher);
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                // Create a new TextView and set properties
                TextView textView = new TextView(mContext);
                textView.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                textView.setTextSize(14);
                textView.setTextColor(mContext.getResources().getColor(R.color.actionbar));
                return textView;
            }
        });

        if (imageOrText == Constants.PROGRESS_IMAGE) {
            image1.setVisibility(View.VISIBLE);
            anim = AnimationsContainer.getInstance().createProgressDialogAnim(image1);
            anim.start();
        } else if (imageOrText == Constants.PROGRESS_TEXT) {
            textSwitcher.setVisibility(View.VISIBLE);
            if (progressArrayText.length > 0) {
                autoScrollPager();
            }
        }

    }

    private void autoScrollPager() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!canChangeSlider)
                        return;
                    if (pagerPos == progressArrayText.length)
                        pagerPos = 0;
                    setAnimation(textSwitcher, progressArrayText[pagerPos], false);
                    pagerPos++;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                autoScrollPager();
            }
        }, delay);
    }


    private void setAnimation(TextSwitcher txt, String value, boolean isRight) {
        if (isRight) {
            txt.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_left));
            txt.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_right));
        } else {
            txt.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right));
            txt.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left));
        }
        txt.setText(String.valueOf(value));
    }


    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        anim.stop();
        super.dismiss();
    }

}
