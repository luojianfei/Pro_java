package com.leo.pro.app;

import android.app.Application;
import android.content.Context;

/**
 * 创建人 LEO
 * 创建时间 2019/1/29 15:14
 */

public class CustomApplication extends Application {

    public static Context mContext ;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext() ;
    }
}
