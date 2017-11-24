package com.example.user.getbingwallpaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by user on 2017/11/24.
 */

public class imageListAdpter extends BaseAdapter {
    private Context mContext;
    private List<Bitmap> mList;
    private List<String> mStringList;
    private LayoutInflater inflater;
    int width;
    int height;

    public imageListAdpter(Context context, int width, int height) {
        init(context, width, height, 5, 0);
    }

    public imageListAdpter(Context context, int width, int height, int count) {
        init(context, width, height, count, 0);
    }

    public imageListAdpter(Context context, int width, int height, int count, int when) {
        init(context, width, height, count, when);
    }

    private void init(Context context, int width, int height, int count, int when) {
        mContext = context;
        mList = new ArrayList<>(count);
        mStringList=new ArrayList<>();
        inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        this.width = width;
        this.height = (int) (((double) width / 1920.0) * 1080); //根据传入进来的手机分辨率，调整图片宽高
        new HandleImage(count, when).execute();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;

        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = createViewHolder(i, inflater, viewGroup);
        }
        viewHolder.img.setImageBitmap(mList.get(i));
        viewHolder.textView.setText(mStringList.get(i));
        return viewHolder.convertView;
    }

    public void refreshList(List<Bitmap> lists) {
        mList = lists;
        notifyDataSetChanged();
    }

    protected ViewHolder createViewHolder(int position, LayoutInflater inflater, ViewGroup parent) {
        final View convertView = inflater.inflate(R.layout.image_item, parent, false);
        return new ViewHolder(convertView);
    }


    private class ViewHolder {
        View convertView;
        ImageView img;
        TextView textView;

        public ViewHolder(View convertView) {
            this.convertView = convertView;
            img = (ImageView) convertView.findViewById(R.id.image_item_image);
            textView = (TextView) convertView.findViewById(R.id.image_item_image_info);
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) img.getLayoutParams();

            //根据手机分辨率，调整imageview的大小
//            System.out.println(width+" "+height);
            params.width = width;
            params.height = height;
            img.setLayoutParams(params);
            convertView.setTag(this);
        }
    }

    private class HandleImage extends AsyncTask<Void, Void, Bitmap> {
        private List<ImageInfo> uriLists;
        int count, when;

        HandleImage() {
            System.out.println("获取图片列表");
            doing(5, 0);
        }

        HandleImage(int count) {
            doing(count, 0);
        }

        HandleImage(int count, int when) {
            doing(count, when);
        }

        private void doing(int count, int when) {
            this.count = count;
            this.when = when;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            uriLists = GetBingWallPaperHelper.getMultiImageUri(count, when);
            for (int i = 0; i < uriLists.size(); i++) {
                InputStream in = GetBingWallPaperHelper.downloadImage(uriLists.get(i).url);
                mList.add(GetBingWallPaperHelper.decodeImage(in));
                mStringList.add(uriLists.get(i).copyRight);
                publishProgress();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            notifyDataSetChanged();
        }
    }
}
