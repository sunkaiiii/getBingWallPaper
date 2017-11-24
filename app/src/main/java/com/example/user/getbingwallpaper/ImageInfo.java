package com.example.user.getbingwallpaper;

import android.net.Uri;

/**
 * Created by user on 2017/11/24.
 */

public class ImageInfo {
    public String copyRight;
    public Uri url;
    public ImageInfo(String copyRight,Uri url){
        this.copyRight=copyRight;
        this.url=url;
    }
}
