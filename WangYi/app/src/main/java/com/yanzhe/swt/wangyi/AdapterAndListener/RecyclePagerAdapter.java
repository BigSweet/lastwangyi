package com.yanzhe.swt.wangyi.AdapterAndListener;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * recycleview中的viewpager的适配器
 * Created by Administrator on 2016/8/30.
 */
public class RecyclePagerAdapter extends PagerAdapter {
    private Context context;
    private int[] imagesid;
    private List<ImageView> mlist;
    private ViewPager viewPager;


    public RecyclePagerAdapter(Context context, int[] imagesid,ViewPager viewPager) {
        this.context = context;
        this.imagesid = imagesid;
        mlist = new ArrayList<ImageView>();
        this.viewPager=viewPager;

    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      //  container.removeView((View) object);
       container.removeView(mlist.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imagesid[position % imagesid.length]);
        //imageView.setImageResource(imagesid[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(imageView);
        mlist.add(imageView);
        return imageView;
    }





}
