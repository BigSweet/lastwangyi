package com.yanzhe.swt.wangyi.AdapterAndListener;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 这是主布局中的viewpager的适配器
 * Created by Administrator on 2016/9/1.
 */
public class FMadapter extends FragmentStatePagerAdapter {
    private List<Fragment> mtList;
    private List<String> mTitleList;

    public FMadapter(FragmentManager fm, List<Fragment> fragmentList ,List<String> list) {
        super(fm);
        mtList = fragmentList;
        mTitleList=list;
    }

    @Override
    public Fragment getItem(int position) {
        return mtList.get(position);
    }

    @Override
    public int getCount() {
        return mtList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
