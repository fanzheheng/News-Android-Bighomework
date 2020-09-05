package com.example.news_android.NewsList;

import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.example.news_android.NewsList.ClassChooser.ClassGridAdapter;
import com.example.news_android.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class NewsClassManagerFragment extends Fragment {
    private List<NewsListFragment> mFragmensts = new ArrayList<>();
    private ViewPager mViewPager;
    private TabLayout mTablayout;
    String[] classNamesArray = new String[]{"AA"};
    List<String> classNames = new ArrayList<>();
    private PopupWindow classChooseWindow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.news_fragment, container, false);
        mViewPager = view.findViewById(R.id.viewPager);
        mTablayout = view.findViewById(R.id.class_tab_layout);
        mTablayout.setupWithViewPager(mViewPager);

        for(String className : classNamesArray) {
            mFragmensts.add(NewsListFragment.newInstance(className));
            classNames.add(className);
        }

        //now we add expert and epidemic lists
        String expertClassName="Expert List";
        String epidemicClassName="Epidemic Data";
        mFragmensts.add(new ExpertFragment(expertClassName));
        mFragmensts.add(new EpidemicDataFragment(epidemicClassName));

        mViewPager.setAdapter(new NewsClassFragmentPagerAdapter(getChildFragmentManager(), mFragmensts));

        //Choose class
        Button classChooseButton = view.findViewById(R.id.choose_class_button);
        initClassChooseWindow(inflater);
        classChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = getActivity().findViewById(R.id.search_bar_edit);
                InputMethodManager imm =(InputMethodManager)getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                setAlpha(0.618f);
                classChooseWindow.showAsDropDown(mTablayout, 0, -mTablayout.getHeight(), Gravity.NO_GRAVITY);
            }
        });
        return view;
    }
    private void initClassChooseWindow(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.classchooser_layout, null);
        //first
        RecyclerView addedClassNameView = view.findViewById(R.id.added_classes_list);
        ClassGridAdapter addedAdapter = new ClassGridAdapter(mFragmensts);
        addedClassNameView.setAdapter(addedAdapter);
        addedClassNameView.setLayoutManager(new GridLayoutManager(getContext(), 4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        //second
        RecyclerView availableClassNameView = view.findViewById(R.id.available_classes_list);
        ClassGridAdapter availableAdapter = new ClassGridAdapter(new ArrayList<NewsListFragment>());
        availableClassNameView.setAdapter(availableAdapter);
        availableClassNameView.setLayoutManager(new GridLayoutManager(getContext(), 4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        //bond
        addedAdapter.bondAdapter(availableAdapter);
        availableAdapter.bondAdapter(addedAdapter);

        classChooseWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
        classChooseWindow.setAnimationStyle(R.style.popupAnimation);
        classChooseWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setAlpha(1.0f);
                mViewPager.getAdapter().notifyDataSetChanged();
                System.out.println("Start");
            }
        });
    }

    private void setAlpha(float f) {
        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
        layoutParams.alpha = f;

        getActivity().getWindow().setAttributes(layoutParams);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        System.out.println(f);
    }

}
