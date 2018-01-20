package com.example.a70472.getwallpaper.Utils;

import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by 70472 on 2018/1/20.
 */

public class ToastUtil {
    public static void MakeToast(String text){
        if(!TextUtils.isEmpty(text)){
            Toast.makeText(GlobalContext.getContextInstance(), text, Toast.LENGTH_SHORT).show();
        }
    }
}
