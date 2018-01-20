package com.example.a70472.getwallpaper.Data;

/**
 * Created by 70472 on 2018/1/20.
 */

final public class ImageData {
    private final String copyright;
    private final String url;
    private final String date;
    public ImageData(final String title, final String url, String date){
        this.copyright=title;
        this.url=url;
        this.date=date;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

}
