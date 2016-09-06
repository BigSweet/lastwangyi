package com.yanzhe.swt.wangyi.AdapterAndListener;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

/**
 * 触摸事件，停止轮播
 * Created by Administrator on 2016/9/3.
 */
public class MyTouchListener implements View.OnTouchListener {
    private Handler mhandler;

    public MyTouchListener(Handler handler) {
        mhandler=handler;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
                mhandler.removeMessages(1);
        }
        return false;
    }


}
