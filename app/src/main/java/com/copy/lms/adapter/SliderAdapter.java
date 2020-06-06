package com.copy.lms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.copy.lms.R;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    private int images[] = {

            R.drawable.teacher,
            R.drawable.personalized,
            R.drawable.detailed

    };

    private int[] headers = {

            R.string.first_slide_title,
            R.string.second_slide_title,
            R.string.third_slide_title

    };

    private int[] subHeaders = {

            R.string.first_slide_sub_title,
            R.string.second_slide_sub_title,
            R.string.third_slide_sub_title

    };

    private int[] descriptions = {

            R.string.first_slide_desc,
            R.string.second_slide_desc,
            R.string.third_slide_desc
    };
    @Override
    public int getCount() {
        return headers.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView imageView = view.findViewById(R.id.slider_image);
        TextView header = view.findViewById(R.id.slider_heading);
        TextView subHeader = view.findViewById(R.id.slider_sub_heading);
        TextView description = view.findViewById(R.id.slider_description);

        imageView.setImageResource(images[position]);
        header.setText(headers[position]);
        subHeader.setText(subHeaders[position]);
        description.setText(descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
