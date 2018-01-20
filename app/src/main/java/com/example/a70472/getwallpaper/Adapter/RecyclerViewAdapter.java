package com.example.a70472.getwallpaper.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a70472.getwallpaper.Data.ImageData;
import com.example.a70472.getwallpaper.R;

import java.util.List;

/*
 * Created by 70472 on 2018/1/20.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Holder> {
    class Holder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView title;
        public Holder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.item_image);
            title=itemView.findViewById(R.id.item_title);
        }
    }

    private Context context;
    private List<ImageData> datas;

    public RecyclerViewAdapter(Context context, List<ImageData> datas){
        this.context=context;
        this.datas=datas;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ImageData data=datas.get(position);
        holder.title.setText(data.getCopyright());
        Glide.with(context).load(data.getUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
