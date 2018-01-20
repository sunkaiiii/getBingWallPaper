package com.example.a70472.getwallpaper.ConnectService;

import com.example.a70472.getwallpaper.Data.ImageData;
import com.example.a70472.getwallpaper.Utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 70472 on 2018/1/20.
 */

public class GetServiceImage {

    public static List<ImageData> GetImageFromServer(int when, int count, String url) {
        Request request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                Gson gson = new Gson();
                return gson.fromJson(response.body().string(), new TypeToken<List<ImageData>>() {
                }.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ToastUtil.MakeToast("出现问题");
        return new ArrayList<>();
    }
}
