package com.example.a70472.getwallpaper.Utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Created by 70472 on 2018/1/20.
 */

public class GlobalContext extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContextInstance(){
        return context;
    }
}
