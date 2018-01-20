package com.example.a70472.getwallpaper.ConnectService;

import com.example.a70472.getwallpaper.Data.ImageData;

import java.util.List;

/**
 * Created by 70472 on 2018/1/20.
 */

public final class GetServiceImageBuilder {
    private int count;
    private int when;
    private String url;

    private GetServiceImageBuilder(String url){
        this.count=30;
        this.when=0;
        this.url=url;
    }

    public static GetServiceImageBuilder create(String url){
        return new GetServiceImageBuilder(url);
    }

    public GetServiceImageBuilder setCount(int count){
        this.count=count;
        return this;
    }

    public GetServiceImageBuilder setWhen(int when){
        this.when=when;
        return this;
    }


    public List<ImageData> post(){
        return GetServiceImage.GetImageFromServer(when,count,url);
    }
}
