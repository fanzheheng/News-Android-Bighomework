package com.example.news_android.NewsList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NewsClassFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<NewsListFragment> mFragmentList;
    public NewsClassFragmentPagerAdapter(FragmentManager fm, List<NewsListFragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentList.get(position).getClassName();
    }


    @Override
    public int getItemPosition(@NonNull @NotNull Object object) {
        return POSITION_NONE;
    }
}
