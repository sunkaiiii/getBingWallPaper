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


    private class BingWallPaper extends AsyncTask<Void,Void,Bitmap>{

        private Uri uri;
        private static final String IMAGE_JSON_URL="https://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=en-us";

        public BingWallPaper(){
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            uri=getImageUri();
            if(uri==null)
                return null;
            InputStream in=downloadImage(uri);
            if(null==in)
                return null;
            return decodeImage(in);
        }

        @Override
        protected void onPostExecute(Bitmap bit) {
            if(bit!=null){
              setImage(bit);
            }
        }

        private String getURLString(){
            Uri jsonUri=Uri.parse(IMAGE_JSON_URL);
            String json=null;
            try {
                URLConnection conn = new URL(jsonUri.toString()).openConnection();
                conn.connect();
                InputStream in=conn.getInputStream();
                InputStreamReader ir=new InputStreamReader(in,"utf-8");
                BufferedReader br=new BufferedReader(ir);
                String line;
                json="";
                while ((line=br.readLine())!=null){
                    json+=line;
                }
            }
            catch (IOException e){
                e.printStackTrace();
                Log.w(TAG,"获取图片信息失败");
            }
            return json;
        }

        private Uri getImageUri(){
            String urlString=getURLString();
            Uri uri=null;
            if(urlString==null)
                return null;

            try {
                JSONObject json = new JSONObject(urlString);
                uri= Uri.parse("https://www.bing.com"+((JSONObject)((JSONArray)json.get("images")).get(0)).get("url").toString());
            }catch (JSONException e){
                Log.w(TAG,"获取json信息失败");
            }
            return uri;
        }

        private InputStream downloadImage(Uri uri){
            InputStream in=null;
            try {
                URLConnection conn = new URL(uri.toString()).openConnection();
                conn.connect();
                in = conn.getInputStream();
            }
            catch (IOException e){
                e.printStackTrace();
                Log.w(TAG,"获取图片失败");
            }
            return in;
        }

        private Bitmap decodeImage(InputStream in) {
            BufferedInputStream buffer=null;
            Bitmap bit=null;
            buffer=new BufferedInputStream(in);
            bit= BitmapFactory.decodeStream(buffer);
            return bit;
        }
    }
}
