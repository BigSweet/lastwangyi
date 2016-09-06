package com.yanzhe.swt.wangyi.AdapterAndListener;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanzhe.swt.wangyi.Bean.NewsBean;
import com.yanzhe.swt.wangyi.R;
import com.yanzhe.swt.wangyi.Util.ImageLoad;

import java.util.List;

/**
 * recycleview的适配器
 * Created by Administrator on 2016/9/1.
 */
public class RVadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEAD_TYPE = 0;
    private static final int ITEM_TYPE = 1;
    private static final int BOTTOM_TYPE = 2;
    public int imagesid[] = {R.mipmap.guide_image1, R.mipmap.guide_image2, R.mipmap.guide_image3};
    private Context context;
    private Handler handler = new Handler();
    private List<NewsBean> mlist;
    private LayoutInflater inflater;
    private ImageLoad imageLoad;
    private LinearLayout headlayout;


    public RVadapter(Context context, List<NewsBean> mlist) {
        this.context = context;
        this.mlist = mlist;
        inflater = LayoutInflater.from(context);
        imageLoad = new ImageLoad();
    }

    //根据不同的类型创建相对应的viewholder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == HEAD_TYPE) {
            view = inflater.inflate(R.layout.recycle_head, parent, false);
            HeadViewholder headviewholder = new HeadViewholder(view);
            headlayout = (LinearLayout) view.findViewById(R.id.head_layout);
            LinearLayout.LayoutParams ind_params = new LinearLayout.LayoutParams(
                    30, 30);

            for (int i = 0; i < imagesid.length; ++i) {
                ImageView iv = new ImageView(context);
                if (i == 0)
                    iv.setBackgroundResource(R.mipmap.dark_dot);
                else
                    iv.setBackgroundResource(R.mipmap.white_dot);
                iv.setLayoutParams(ind_params);
                headlayout.addView(iv);
            }


            return headviewholder;
        }
        if (viewType == ITEM_TYPE) {
            view = inflater.inflate(R.layout.recycle_item, parent, false);
            ItemViewhodler itemViewhodler = new ItemViewhodler(view);
            return itemViewhodler;
        }
        if (viewType == BOTTOM_TYPE) {
            view = inflater.inflate(R.layout.recycle_bottm, parent, false);
            BottomViewholder bottom = new BottomViewholder(view);
            return bottom;
        }

        return null;

    }


    //根据不同的类型给相对应的viewholder进行赋值
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == HEAD_TYPE) {
            final HeadViewholder headViewholder = (HeadViewholder) holder;
//            mimage= new ArrayList<ImageView>();
            headViewholder.viewpager.setAdapter(new RecyclePagerAdapter(context, imagesid, headViewholder.viewpager));


            Handler mHandler = new Handler() {
                public void handleMessage(android.os.Message msg) {
                    switch (msg.what) {
                        case 1:
                            int currentItem = headViewholder.viewpager.getCurrentItem();

                            int toItem = currentItem + 1;//*== totalcount ? 0 : currentItem + 1;*//*


                            headViewholder.viewpager.setCurrentItem(toItem, true);

                            //每两秒钟发送一个message，用于切换viewPager中的图片
                            this.sendEmptyMessageDelayed(1, 4000);
                    }
                }
            };
            mHandler.sendEmptyMessageAtTime(1, 2000);
            MyTouchListener listener = new MyTouchListener(mHandler);
            headViewholder.viewpager.setOnTouchListener(listener);


            /**
             * 设置小圆点跟随着页面变动
             */
            headViewholder.viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    int mpositon = position % imagesid.length;

                    int len = headlayout.getChildCount();
                    for (int i = 0; i < len; ++i)
                        headlayout.getChildAt(i).setBackgroundResource(R.mipmap.white_dot);
                    headlayout.getChildAt(mpositon).setBackgroundResource(R.mipmap.dark_dot);

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        } else if (getItemViewType(position) == ITEM_TYPE) {
            ItemViewhodler itemViewhodler = (ItemViewhodler) holder;
            itemViewhodler.imageView.setImageResource(R.mipmap.ic_launcher);
            String url = mlist.get(position).newsiconurl;
            itemViewhodler.imageView.setTag(url);
            imageLoad.showimagebyasynctask(itemViewhodler.imageView, url);
            itemViewhodler.title.setText(mlist.get(position).newstitle);
            itemViewhodler.content.setText(mlist.get(position).newscontent);
        } else if (getItemViewType(position) == BOTTOM_TYPE) {
            final BottomViewholder bottomViewholder = (BottomViewholder) holder;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bottomViewholder.bottom_load.setVisibility(View.GONE);
                    bottomViewholder.imageView.setImageResource(R.mipmap.ic_launcher);
                    bottomViewholder.title.setText("新增的");
                    bottomViewholder.content.setText("新增的");
                    bottomViewholder.bottom_item.setVisibility(View.VISIBLE);
                }
            }, 2000);


        }


    }


    //获得总页数
    @Override
    public int getItemCount() {
        Log.i("TAG", mlist.size() + "");
        return mlist.size() + 1;
    }


    //根据位置返回不同的类型
    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEAD_TYPE;
        else if (position == mlist.size())
            return BOTTOM_TYPE;
        else
            return ITEM_TYPE;

    }


}

//创建相对应的viewholder
class ItemViewhodler extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView title;
    public TextView content;

    public ItemViewhodler(View itemView) {
        super(itemView);


        imageView = (ImageView) itemView.findViewById(R.id.iv_item);
        title = (TextView) itemView.findViewById(R.id.title_item);
        content = (TextView) itemView.findViewById(R.id.content_item);


    }
}

class HeadViewholder extends RecyclerView.ViewHolder {
    public ViewPager viewpager;

    public HeadViewholder(View itemView) {
        super(itemView);
        viewpager = (ViewPager) itemView.findViewById(R.id.ttviewpager);


    }


}

class BottomViewholder extends RecyclerView.ViewHolder {
    public View bottom_load, bottom_item;
    public ImageView imageView;
    public TextView title;
    public TextView content;

    public BottomViewholder(View itemView) {
        super(itemView);
        bottom_load = itemView.findViewById(R.id.bottom_layout);

        bottom_item = itemView.findViewById(R.id.bottom_relativ);
        bottom_item.setVisibility(View.GONE);
        bottom_load.setVisibility(View.VISIBLE);
        imageView = (ImageView) itemView.findViewById(R.id.iv_bottom);
        title = (TextView) itemView.findViewById(R.id.title_bottom);
        content = (TextView) itemView.findViewById(R.id.content_bottom);


    }
}
