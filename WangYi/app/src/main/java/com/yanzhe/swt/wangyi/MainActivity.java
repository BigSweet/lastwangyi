package com.yanzhe.swt.wangyi;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.yanzhe.swt.wangyi.AdapterAndListener.FMadapter;
import com.yanzhe.swt.wangyi.Fragment.JxFragment;
import com.yanzhe.swt.wangyi.Fragment.TtFragment;
import com.yanzhe.swt.wangyi.Fragment.TyFragment;
import com.yanzhe.swt.wangyi.Fragment.WyhFragment;
import com.yanzhe.swt.wangyi.Fragment.YlFragment;
import com.yanzhe.swt.wangyi.Util.ViewPagerIndicate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private List<Fragment> fragmentList;
    private ViewPager main_vp;
    private ImageView find, add, more;
    private ViewPagerIndicate mIndicate;
    private TabLayout mTabLayout;
    //  private int[] mTextColors = {0xFFA0A0A0, 0xFF000000};//字体的颜色
    // private int mUnderlineColor = 0xFF168EFF; //下划线的颜色
    //   private String[] mTitles = new String[]{"头条", "精选", "娱乐", "体育", "网易号"};//标签的文字
    private List<String> mTitleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initdata();
        // initViewPagerIndicate(main_vp);

        add.setOnClickListener(this);
        more.setOnClickListener(this);
        find.setOnClickListener(this);


        mTitleList.add("头条");
        mTitleList.add("精选");
        mTitleList.add("娱乐");
        mTitleList.add("体育");
        mTitleList.add("网易号");
        FMadapter fMadapter = new FMadapter(getSupportFragmentManager(), fragmentList,mTitleList);
        main_vp.setAdapter(fMadapter);
        mTabLayout.setupWithViewPager(main_vp);//将TabLayout和ViewPager关联起来。

    }

    /**
     * 初始化数据
     */
    private void initdata() {
        TtFragment ttFragment = new TtFragment();
        JxFragment jxFragment = new JxFragment();
        YlFragment ylFragment = new YlFragment();
        TyFragment tyFragment = new TyFragment();
        WyhFragment wyhFragment = new WyhFragment();
        main_vp = (ViewPager) findViewById(R.id.main_vp);
        add = (ImageView) findViewById(R.id.add);
        find = (ImageView) findViewById(R.id.find);
        more = (ImageView) findViewById(R.id.more);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(ttFragment);
        fragmentList.add(jxFragment);
        fragmentList.add(ylFragment);
        fragmentList.add(tyFragment);
        fragmentList.add(wyhFragment);
    }


   /* *//**
     * 初始化ViewPagerIndicate，并且和ViewPager建立关联
     *
     * @param main_vp viewpager
     *//*
    private void initViewPagerIndicate(ViewPager main_vp) {
        mIndicate = (ViewPagerIndicate) findViewById(R.id.indicate);//自定义ViewPagerIndicate继承HorizontalScrollView，重写
        //设置标签样式、文本和颜色
        mIndicate.resetText(R.layout.indicate, mTitles, mTextColors);
        //设置下划线粗细和颜色
        mIndicate.resetUnderline(4, mUnderlineColor);
        //设置ViewPager
        mIndicate.resetViewPager(main_vp);
        //设置初始化完成
        mIndicate.setOk();
    }*/


    /**
     * 主界面右上角3个imageview点击响应事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find:
                Toast toast = Toast.makeText(MainActivity.this, "搜索功能", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                break;

            case R.id.more:
                Toast toast1 = Toast.makeText(MainActivity.this, "更多功能", Toast.LENGTH_SHORT);
                toast1.setGravity(Gravity.CENTER, 0, 0);
                toast1.show();
                break;
            case R.id.add:
                Toast toast2 = Toast.makeText(MainActivity.this, "添加功能", Toast.LENGTH_SHORT);
                toast2.setGravity(Gravity.CENTER, 0, 0);
                toast2.show();
                break;
        }
    }

    /**
     * 返回物理键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showexitdilog();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 设置安卓物理键点击弹出对话框
     */
    private void showexitdilog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("你确定要退出吗");
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
