package com.example.user.getbingwallpaper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "getBingWallPaper";

    private ListView list;
    private imageListAdpter adpter;
    ProgressBar progressBar;

    private void init() {
        list = (ListView) findViewById(R.id.list);
        progressBar=(ProgressBar)findViewById(R.id.loading);
        adpter = new imageListAdpter(this, getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getWidth());
        list.setAdapter(adpter);
        list.setEmptyView(progressBar);
        adpter.getBingData();

//        list.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int i) {
//                switch (i){
//                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
//                        System.out.println(list.getLastVisiblePosition());
//                        if(list.getLastVisiblePosition()==list.getAdapter().getCount()-1){
//                            ((imageListAdpter)list.getAdapter()).getBingData(list.getAdapter().getCount());
//                        }
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
//
//            }
//        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

}
