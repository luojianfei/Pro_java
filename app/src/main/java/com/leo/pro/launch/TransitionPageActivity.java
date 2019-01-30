package com.leo.pro.launch;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.leo.pro.app.utils.ActivityUtils;
import com.leo.pro.app.utils.AppUtils;
import com.leo.pro.app.utils.SPUtils;

/**
 * 创建人 LEO
 * 创建时间 2019/1/29 17:49
 */

public class TransitionPageActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        boolean isFirstLaunch = (boolean) SPUtils.get(this, AppUtils.getAppName(this),"isFirstLaunch",true);
        if(isFirstLaunch){//第一次启动app
            SPUtils.put(this,AppUtils.getAppName(this),"isFirstLaunch",false);
            ActivityUtils.startActivityIntent(this,GuideActivity.class);
        }else{//非第一次启动app
            ActivityUtils.startActivityIntent(this,WelcomeActivity.class);
        }
        finish();
    }
}
