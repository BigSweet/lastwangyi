package com.yanzhe.swt.wangyi.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/8/30.
 */
public class ImageLoad {


    private LruCache<String, Bitmap> mlrucache;

    public ImageLoad() {
        int Maxmemory = (int) Runtime.getRuntime().maxMemory();
        int cachesize = Maxmemory / 4;
        mlrucache = new LruCache<String, Bitmap>(cachesize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };


    }

    public void addBitmaptocache(String url, Bitmap bitmap) {
        if (getBitmapfromcache(url) == null) {
            mlrucache.put(url, bitmap);

        }
    }

    public Bitmap getBitmapfromcache(String url) {
        return mlrucache.get(url);
    }


    public Bitmap getgitmapfromuri(String url) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL murl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) murl.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    public void showimagebyasynctask(ImageView imageView, String url) {

        Bitmap bitmap = getBitmapfromcache(url);
        if(bitmap==null){
            new myasynctask(imageView,url).execute(url);
        }else {
            imageView.setImageBitmap(bitmap);
        }


    }

    class myasynctask extends AsyncTask<String, Void, Bitmap> {
        private ImageView mimageView;
        private String murl;

        public myasynctask(ImageView imageView, String url) {
            this.mimageView = imageView;
            this.murl = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url=params[0];
            Bitmap bitmap =getgitmapfromuri(url);
            if(bitmap!=null){
                addBitmaptocache(url,bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (mimageView.getTag().equals(murl)) {
                mimageView.setImageBitmap(bitmap);

            }
        }
    }
}
