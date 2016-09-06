package com.yanzhe.swt.wangyi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/3.
 */
public class LauchActivity extends Activity  implements View.OnClickListener{
    private ImageView imageView;
    private TextView textView;
    int start=3;


    /**
     * 实现启动界面右上角的倒计时
     */
    Handler LaunchHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 2:
                    textView.setText("跳过    "+ start--);
                    this.sendEmptyMessageDelayed(2, 1000);
            }
        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.lauch);

        imageView = (ImageView) findViewById(R.id.lauch_im);
        textView = (TextView) findViewById(R.id.lauch_tx);

        imageView.setImageResource(R.mipmap.lauch);
        textView.setOnClickListener(this);
        AlphaAnimation animation = new AlphaAnimation(1.0f, 1.0f);
        animation.setDuration(3000);
        imageView.setAnimation(animation);
        LaunchHandler.sendEmptyMessage(2);


        /**
         * 给imageview加上动画监听，在动画结束之后执行跳转代码
         */
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {


            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(LauchActivity.this, MainActivity.class);
                startActivity(intent);
                LaunchHandler.removeMessages(1);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }


    /**
     * textview被点击的时候跳转到mainactivity
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(LauchActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

