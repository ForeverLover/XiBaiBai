package com.jph.xibaibai.adapter.base;

//**

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Fragment的VeiwPager使用的适配器
 * Created by jph on 2015/8/25.
 */
public class PageFragAdapter extends FragmentPagerAdapter {
    private List<Fragment> mListFrag;

    public PageFragAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mListFrag = list;
    }

    @Override
    public int getCount() {
        return mListFrag.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mListFrag.get(position);
    }

}