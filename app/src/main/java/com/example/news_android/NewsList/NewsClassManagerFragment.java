package com.example.news_android.NewsList;

import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.PopupWindow;

import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.DefaultItemAnimator;
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
    private RecyclerView addedClassNameView, availableClassNameView;
    private TextView addedClassHint, availableClassHint;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.news_fragment, container, false);
        mViewPager = view.findViewById(R.id.viewPager);
        mTablayout = view.findViewById(R.id.class_tab_layout);
        mTablayout.setupWithViewPager(mViewPager);

        //init fragments
        //TODO
        for(String className : classNamesArray) {
            mFragmensts.add(NewsListFragment.newInstance(className));
            classNames.add(className);
        }

        //now we add expert and epidemic lists
        String expertClassName="Expert List";
        String epidemicClassName="Epidemic Data";
        String entityClassName="Entity List";
        mFragmensts.add(new ExpertFragment(expertClassName));
        mFragmensts.add(new EpidemicDataFragment(epidemicClassName));
        mFragmensts.add(new EntityFragment(entityClassName,"病毒"));
        classNames.add(entityClassName);
        classNames.add(expertClassName);
        classNames.add(epidemicClassName);

        mViewPager.setAdapter(new NewsClassFragmentPagerAdapter(getChildFragmentManager(), mFragmensts));

        //Choose class
        Button classChooseButton = view.findViewById(R.id.choose_class_button);
        initClassChooseWindow(inflater);
        classChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout constraintLayout = getActivity().findViewById(R.id.search_bar_layout);
                InputMethodManager imm =(InputMethodManager)getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(constraintLayout.getWindowToken(), 0);
                setAlpha(0.618f);
                classChooseWindow.showAsDropDown(mTablayout, 0, -mTablayout.getHeight(), Gravity.NO_GRAVITY);
            }
        });
        return view;
    }

    private void initClassChooseWindow(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.classchooser_layout, null);
        addedClassNameView = view.findViewById(R.id.added_classes_list);
        availableClassNameView = view.findViewById(R.id.available_classes_list);
        addedClassHint = view.findViewById(R.id.added_classes_hint);
        availableClassHint = view.findViewById(R.id.available_classes_hint);

        //first
        addedClassNameView.setItemAnimator(new DefaultItemAnimator());
        ClassGridAdapter addedAdapter = new ClassGridAdapter(mFragmensts, new ClassGridAdapter.OnClassChangeListener() {
            @Override
            public void onClassChange(int classNum) {
                if(classNum == 0) {
                    addedClassNameView.setVisibility(View.GONE);
                    addedClassHint.setVisibility(View.VISIBLE);
                } else {
                    addedClassNameView.setVisibility(View.VISIBLE);
                    addedClassHint.setVisibility(View.GONE);
                }
            }
        });
        addedClassNameView.setAdapter(addedAdapter);
        addedClassNameView.setLayoutManager(new GridLayoutManager(getContext(), 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        //second
        availableClassNameView.setItemAnimator(new DefaultItemAnimator());
        ClassGridAdapter availableAdapter = new ClassGridAdapter(new ArrayList<NewsListFragment>(), new ClassGridAdapter.OnClassChangeListener(){
            @Override
            public void onClassChange(int classNum) {
                if(classNum == 0) {
                    availableClassNameView.setVisibility(View.GONE);
                    availableClassHint.setVisibility(View.VISIBLE);
                } else {
                    availableClassNameView.setVisibility(View.VISIBLE);
                    availableClassHint.setVisibility(View.GONE);
                }
            }
        });
        availableClassNameView.setAdapter(availableAdapter);
        availableClassNameView.setLayoutManager(new GridLayoutManager(getContext(), 3){
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
