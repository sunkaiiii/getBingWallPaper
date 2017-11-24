package com.example.user.getbingwallpaper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

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

public class MainActivity extends AppCompatActivity {

    static final String TAG="getBingWallPaper";

    private Button btnGet;
    private ImageView imgView;

    private void init(){
        btnGet=(Button)findViewById(R.id.btn_get);
        imgView=(ImageView)findViewById(R.id.img_get);

        btnGet.setOnClickListener((view -> {new BingWallPaper().execute();}));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void setImage(Bitmap bit){
        imgView.setImageBitmap(bit);
        imgView.refreshDrawableState();
    }


    public class BingWallPaper extends AsyncTask<Void,Void,Bitmap>{

        private Uri uri;


        public BingWallPaper(){
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            uri=GetBingWallPaperHelper.getImageUri();
            if(uri==null)
                return null;
            InputStream in=GetBingWallPaperHelper.downloadImage(uri);
            if(null==in)
                return null;
            return GetBingWallPaperHelper.decodeImage(in);
        }

        @Override
        protected void onPostExecute(Bitmap bit) {
            if(bit!=null){
              setImage(bit);
            }
        }


    }
}
