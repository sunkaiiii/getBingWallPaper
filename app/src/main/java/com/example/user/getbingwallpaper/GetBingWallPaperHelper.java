package com.example.user.getbingwallpaper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017/11/24.
 */

public class GetBingWallPaperHelper {
    private static final String IMAGE_JSON_URL = "https://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=en-us";
    private static final String TAG = "GetBingWallPaperHelper";


    private static String getMuiltiUrl(int count, int when) {
        return getURLString(makeUrlString(count, when));
    }

    private static String makeUrlString(int count, int when) {
        return "https://www.bing.com/HPImageArchive.aspx?format=js&idx=" + String.valueOf(when) + "&n=" + String.valueOf(count) + "&mkt=en-us";
    }

    private static String getURLString() {
        Uri jsonUri = Uri.parse(IMAGE_JSON_URL);
        return downloadURL(jsonUri);
    }

    private static String getURLString(String url) {
        Uri jsonUri = Uri.parse(url);
        return downloadURL(jsonUri);
    }

    private static String downloadURL(Uri jsonUri) {
        String json = null;
        try {
            URLConnection conn = new URL(jsonUri.toString()).openConnection();
            conn.connect();
            InputStream in = conn.getInputStream();
            InputStreamReader ir = new InputStreamReader(in, "utf-8");
            BufferedReader br = new BufferedReader(ir);
            String line;
            json = "";
            while ((line = br.readLine()) != null) {
                json += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.w(TAG, "获取图片信息失败");
        }
        return json;
    }

    public static Uri getImageUri() {
        String urlString = getURLString();
        Uri uri = null;
        if (urlString == null)
            return null;

        try {
            JSONObject json = new JSONObject(urlString);
            uri = Uri.parse("https://www.bing.com" + ((JSONObject) ((JSONArray) json.get("images")).get(0)).get("url").toString());
        } catch (JSONException e) {
            Log.w(TAG, "获取json信息失败");
        }
        return uri;
    }

    public static List<ImageInfo> getMultiImageUri() {
        return getMultiImageUri(5, 0);
    }

    public static List<ImageInfo> getMultiImageUri(int count) {
        return getMultiImageUri(count, 0);
    }

    public static List<ImageInfo> getMultiImageUri(int count, int when) {
        if (count > 7) {
            count = 7; //必应接口最高返回近7张图片
        }
        if(when>7){
            when=0;
        }
        String urlString = getMuiltiUrl(count, when);
        List<ImageInfo> imageInfos=new ArrayList<>();
        try {
            JSONObject json = new JSONObject(urlString);
            JSONArray jsonArray = (JSONArray) json.get("images");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject imgObj = (JSONObject) jsonArray.get(i);
                String copyRight=imgObj.get("copyright").toString();
                Uri uri=(Uri.parse("https://www.bing.com" + imgObj.get("url").toString()));
                imageInfos.add(new ImageInfo(copyRight,uri));
            }
        } catch (JSONException e) {
            Log.w(TAG, "获取json信息失败");
        }
        return imageInfos;
    }

    public static InputStream downloadImage(Uri uri) {
        InputStream in = null;
        try {
            URLConnection conn = new URL(uri.toString()).openConnection();
            conn.connect();
            in = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            Log.w(TAG, "获取图片失败");
        }
        return in;
    }

    public static Bitmap decodeImage(InputStream in) {
        BufferedInputStream buffer = null;
        Bitmap bit = null;
        buffer = new BufferedInputStream(in);
        bit = BitmapFactory.decodeStream(buffer);
        return bit;
    }
}
