package com.yanzhe.swt.wangyi.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yanzhe.swt.wangyi.Util.DividerItemDecoration;
import com.yanzhe.swt.wangyi.Bean.NewsBean;
import com.yanzhe.swt.wangyi.R;
import com.yanzhe.swt.wangyi.AdapterAndListener.RVadapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 头条界面的fragment
 * Created by Administrator on 2016/8/30.
 */
public class TtFragment extends Fragment {
    private RecyclerView recyclerView;
    private Handler handler=new Handler();
    private SwipeRefreshLayout refreshLayout;
    private static String url = "http://www.imooc.com/api/teacher?type=4&num=30";//请求的url接口地址


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tt, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyle);

        /**
         * 给refreshLayout设置颜色，监听
         */
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast toast=Toast.makeText(getActivity(), "正在刷新", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //判断当前的fragment是否存活，如果不存活就不执行，防止刷新的时候切换页面导致空指针错误
                        if (getUserVisibleHint()){
                            Toast toast = Toast.makeText(getActivity(), "刷新完成", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            new newsAsynctask().execute(url);
                            refreshLayout.setRefreshing(false);
                        }



                    }
                }, 2000);
            }
        });

        new newsAsynctask().execute(url);//开启异步线程

        return view;
    }

    /**
     * 防止刷新的时候切换界面导致切换回来的时候还处于刷新状态
     */
    @Override
    public void onStop() {
        super.onStop();
        refreshLayout.setRefreshing(false);
    }

    /**
     * 通过url得到list<newsbean>数据
     */
    class newsAsynctask extends AsyncTask<String, Void, List<NewsBean>> {

        @Override
        protected List<NewsBean> doInBackground(String... params) {

            return getJsondata(params[0]);
        }

        @Override
        protected void onPostExecute(List<NewsBean> newsBeans) {
            super.onPostExecute(newsBeans);


            /**
             * 给recyclerView设置适配器和展示方式为LIST方式
             */
            RVadapter adapter = new RVadapter(getActivity(), newsBeans);
            recyclerView.setAdapter(adapter);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager);
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        }
    }


    /**
     * 通过url得到json格式的数据并且放到list中
     * @param url
     * @return
     */
    private List<NewsBean> getJsondata(String url) {
        List<NewsBean> list = new ArrayList<NewsBean>();

        try {
            String Jsonstring = readStream(new URL(url).openStream());
            android.util.Log.d("xys", Jsonstring);
            JSONObject jsonObject = new JSONObject(Jsonstring);
            NewsBean newsBean;
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                newsBean = new NewsBean();
                newsBean.newsiconurl = jsonObject.getString("picSmall");
                newsBean.newstitle = jsonObject.getString("name");
                newsBean.newscontent = jsonObject.getString("description");
                list.add(newsBean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;

    }

    /**
     * 通过IO读书接口的数据并且写入result中
     * @param is
     * @return
     */
    private String readStream(InputStream is) {
        InputStreamReader isr;
        String result = "";
        BufferedReader br;
        try {
            String line = "";
            isr = new InputStreamReader(is, "utf-8");
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result += line;

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
