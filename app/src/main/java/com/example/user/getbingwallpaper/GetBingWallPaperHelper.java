package com.example.user.getbingwallpaper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

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

/**
 * Created by user on 2017/11/24.
 */

public class GetBingWallPaperHelper {
    private static final String IMAGE_JSON_URL="https://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=en-us";
    private static final String TAG="GetBingWallPaperHelper";
    private static String getURLString(){
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

    public static Uri getImageUri(){
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

    public static InputStream downloadImage(Uri uri){
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

    public static Bitmap decodeImage(InputStream in) {
        BufferedInputStream buffer=null;
        Bitmap bit=null;
        buffer=new BufferedInputStream(in);
        bit= BitmapFactory.decodeStream(buffer);
        return bit;
    }
}
