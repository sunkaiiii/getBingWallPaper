package com.example.user.getbingwallpaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by user on 2017/11/24.
 */

public class imageListAdpter extends BaseAdapter {
    Context context;
    int width;
    int height;
    int count,when;
    List<ImageInfo> uri=null;
    ListView listView;
    //图片缓存
    LruCache<Uri,Bitmap> lruCache;

    public imageListAdpter(Context context, int width, int height) {
        init(context, width, height, 7, 0);
    }

    public imageListAdpter(Context context, int width, int height, int count) {
        init(context, width, height, count, 0);
    }

    public imageListAdpter(Context context, int width, int height, int count, int when) {
        init(context, width, height, count, when);
    }

    private void init(Context context, int width, int height, int count, int when) {
        this.context=context;
        this.width = width;
        this.height = (int) (((double) width / 1920.0) * 1080); //根据传入进来的手机分辨率，调整图片宽高
        this.count=count;
        this.when=when;
        int maxMemory=(int)Runtime.getRuntime().maxMemory();
        int cacheSize=maxMemory/4;
        lruCache=new LruCache<Uri,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(Uri key, Bitmap value) {
                return value.getByteCount();
            }
        };

        //启动线程，获取uri
        new Thread(()->{
            uri=GetBingWallPaperHelper.getMultiImageUri(count,when);
            new Handler((context.getMainLooper()),message -> {
                notifyDataSetChanged();
                return true;
            }).sendEmptyMessage(0);
        }).start();

    }

    @Override
    public int getCount() {
        return uri==null?0:uri.size();
    }

    @Override
    public Object getItem(int i) {
        return uri.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if(listView==null){
            listView=(ListView)viewGroup;
        }
        View view=LayoutInflater.from(context).inflate(R.layout.image_item,null);
        TextView textView=(TextView)view.findViewById(R.id.image_item_image_info);
        ImageView imageView=(ImageView)view.findViewById(R.id.image_item_image);
        ViewGroup.LayoutParams params=(ViewGroup.LayoutParams)imageView.getLayoutParams();
        params.width=width;
        params.height=height;
        imageView.setLayoutParams(params);
        textView.setText(((ImageInfo)getItem(i)).copyRight);
        Uri uri=((ImageInfo)getItem(i)).url;
        imageView.setTag(uri);
        Bitmap bitmap=getBitmapCache(uri);
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
        }
        else{
            BitmapWorkerTask bitmapWorkerTask=new BitmapWorkerTask(uri,listView);
            bitmapWorkerTask.execute();
        }
        return view;
    }

    private Bitmap getBitmapCache(Uri uri){
        return lruCache.get(uri);
    }

    private void addMemoryCache(Uri uri,Bitmap bitmap){
        lruCache.put(uri,bitmap);
    }

    class BitmapWorkerTask extends AsyncTask<Void,Void,Bitmap>{
        ListView listView;
        Uri uri;
        public BitmapWorkerTask(Uri uri,ListView listView){
            this.uri=uri;
            this.listView=listView;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bitmap=BitmapFactory.decodeStream(GetBingWallPaperHelper.downloadImage(uri));
            if(bitmap!=null) {
                addMemoryCache(uri, bitmap);
                return bitmap;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView imageView=(ImageView)listView.findViewWithTag(uri);
            if(imageView!=null&&bitmap!=null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }

}
