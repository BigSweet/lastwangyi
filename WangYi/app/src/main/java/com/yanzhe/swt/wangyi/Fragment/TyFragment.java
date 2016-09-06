package com.yanzhe.swt.wangyi.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanzhe.swt.wangyi.R;

/**
 * 体育界面的fragment
 * Created by Administrator on 2016/8/30.
 */
public class TyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ty,container,false);
        return view;
    }
}
