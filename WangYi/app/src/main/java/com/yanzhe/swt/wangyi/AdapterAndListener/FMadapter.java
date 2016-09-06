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

    public FMadapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mtList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mtList.get(position);
    }

    @Override
    public int getCount() {
        return mtList.size();
    }
}
