package com.example.a70472.getwallpaper.Activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.a70472.getwallpaper.Adapter.RecyclerViewAdapter;
import com.example.a70472.getwallpaper.ConnectService.GetServiceImage;
import com.example.a70472.getwallpaper.ConnectService.GetServiceImageBuilder;
import com.example.a70472.getwallpaper.Data.ImageData;
import com.example.a70472.getwallpaper.Interfaces.LoadDataListener;
import com.example.a70472.getwallpaper.R;
import com.example.a70472.getwallpaper.Utils.ToastUtil;
import com.example.a70472.getwallpaper.Utils.Values;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoadDataListener {

    private List<ImageData> imageData;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        startLoadImagesData();
    }

    private void initView(){
        refreshLayout=findViewById(R.id.refresh_layout);
        recyclerView=findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.setAdapter(null);
                adapter=null;
                startLoadImagesData();
            }
        });
    }

    private void startLoadImagesData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                onPreLoad();
                imageData= GetServiceImageBuilder.create(Values.connectUrl).post();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onLoaded();
                        adapter=new RecyclerViewAdapter(MainActivity.this,imageData);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onPreLoad() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void onLoaded() {
        refreshLayout.setRefreshing(false);
        ToastUtil.MakeToast("获取数据成功");
    }
}
