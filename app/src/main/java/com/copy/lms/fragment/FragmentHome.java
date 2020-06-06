package com.copy.lms.fragment;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.appcompat.widget.SearchView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.copy.lms.Config;
import com.copy.lms.R;
import com.copy.lms.activity.AboutUsActivity;
import com.copy.lms.activity.BundleListActivity;
import com.copy.lms.activity.CheckoutActivity;
import com.copy.lms.activity.ContactUsActivity;
import com.copy.lms.activity.CourseListActivity;
import com.copy.lms.activity.FaqListActivity;
import com.copy.lms.activity.InvoiceActivity;
import com.copy.lms.activity.MassageListActivity;
import com.copy.lms.activity.SearchListActivity;
import com.copy.lms.activity.TeacherListActivity;
import com.copy.lms.activity.TestimonialListActivity;
import com.copy.lms.activity.WhyusListActivity;
import com.copy.lms.adapter.HomeAdapter;
import com.copy.lms.basecomponent.BaseFragment;
import com.copy.lms.callBack.OnRecyclerItemClick;
import com.copy.lms.databinding.FragmentHomeBinding;
import com.copy.lms.model.HomeListModel;
import com.copy.lms.model.HomeModel;
import com.copy.lms.util.AppConstant;
import com.copy.lms.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FragmentHome extends BaseFragment implements View.OnClickListener {
    public static final String TAG = "Shop";
    public HomeListModel model;
    private FragmentHomeBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        model = new HomeListModel();
        model.setArrayList(new ArrayList<HomeModel>());
        binding.setHomeListModel(model);
        initView();
        return binding.getRoot();

    }

    /*
     * Init Method
     * */
    private void initView() {
        initRecycler();
        fillArraylist();

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                openSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });
    }


    private void openSearch(String search) {
        AppConstant.hideKeyboard(getContext(), binding.recyclerView);
        startActivity(new Intent(getContext(), SearchListActivity.class)
                .putExtra(SearchListActivity.SEARCH, search)
                .putExtra(SearchListActivity.TYPE, "1"));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);

    }

    ;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {


        }
    }

    /*
     *  initialize Reacycler view
     */
    private void initRecycler() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.recyclerView.setAdapter(new HomeAdapter(getActivity(),
                model.getArrayList(), new OnRecyclerItemClick() {
            @Override
            public void onClick(int position, int type) {
                switch (type) {
                    case Constants.ROW_CLICK:
                        switch (model.getArrayList().get(position).getId()) {

                            case Constants.COURSES:
                                openCourseTypeList("none");
                                break;
                            case Constants.BUNDLES:
                                openBundleList();
                                break;
                            case Constants.TESTIMONIAL:
                                openTestimonialList();
                                break;
                            case Constants.TEACHER:
                                openTeacherList();
                                break;
                            case Constants.FAQ_QUESTION:
                                openFaqList();
                                break;
                            case Constants.WHY_US:
                                openWhyusList();
                                break;
                            case Constants.ABOUT:
                                openAboutUs();
                                break;
                            case Constants.CONTACT_US:
                                openContactUs();
                                break;
                        }

                        break;
                }
            }
        }));


    }


    private void openWhyusList() {
        startActivity(new Intent(getContext(), WhyusListActivity.class));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openContactUs() {
        startActivity(new Intent(getContext(), ContactUsActivity.class));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);
    }


    private void openTestimonialList() {
        startActivity(new Intent(getContext(), TestimonialListActivity.class));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openAboutUs() {
        startActivity(new Intent(getContext(), AboutUsActivity.class));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openCourseTypeList(String value) {
        startActivity(new Intent(getContext(), CourseListActivity.class)
                .putExtra(CourseListActivity.VALUE, value));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openBundleList() {
        startActivity(new Intent(getContext(), BundleListActivity.class)
                .putExtra(CourseListActivity.VALUE, "none"));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);


    }

    private void openCheckout() {
        startActivity(new Intent(getContext(), CheckoutActivity.class));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openTeacherList() {
        startActivity(new Intent(getContext(), TeacherListActivity.class));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openFaqList() {
        startActivity(new Intent(getContext(), FaqListActivity.class));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openMessageListList() {
        startActivity(new Intent(getContext(), MassageListActivity.class));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void openInvoiceListList() {
        startActivity(new Intent(getContext(), InvoiceActivity.class));
        getActivity().overridePendingTransition(R.anim.animation, R.anim.animation2);
    }

    private void fillArraylist() {
        model.getArrayList().clear();
        String[] fName = {
                "",
                "",
                getString(R.string.our),
                "",
                getString(R.string.contect),
                getString(R.string.why),
                "",
                getString(R.string.aboutTeacher),

        };

        String[] id = {
                Constants.COURSES,
                Constants.BUNDLES,
                Constants.TEACHER,
                Constants.FAQ_QUESTION,
                Constants.CONTACT_US,
                Constants.WHY_US,
                Constants.TESTIMONIAL,
                Constants.ABOUT,
        };

        String[] lname = {
                getString(R.string.courses),
                getString(R.string.bundles),
                getString(R.string.teacher),
                getString(R.string.faqs),
                getString(R.string.us),
                getString(R.string.us),
                getString(R.string.testimonial),
                getString(R.string.us),

        };
        int[] image = {R.drawable.course,
                R.drawable.book,
                R.drawable.professor,
                R.drawable.knowledge,
                R.drawable.contact,
                R.drawable.conversation,
                R.drawable.testimonial,
                R.drawable.hand,


        };


        for (int i = 0; i < fName.length; i++) {
            HomeModel itemModel = new HomeModel();
            itemModel.setfName(fName[i]);
            itemModel.setlName(lname[i]);
            itemModel.setImage(image[i]);
            itemModel.setId(id[i]);
            model.getArrayList().add(itemModel);

        }
        binding.recyclerView.getAdapter().notifyDataSetChanged();
    }


}
