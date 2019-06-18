package com.leo.pro.launch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.leo.pro.R;
import com.leo.pro.app.base.Base2Activity;
import com.leo.pro.databinding.ActivityWelcomeBinding;
import com.leo.pro.login.view.LoginActivity;
import com.leo.pro.app.utils.ActivityUtils;
import com.leo.pro.app.utils.RxCountDown;

/**
 * 创建人 LEO
 * 创建时间 2019/1/29 17:57
 */

public class WelcomeActivity extends Base2Activity<ActivityWelcomeBinding> {
    private static final String TAG = "WelcomeActivity" ;
    private RxCountDown mCountDown;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setMFullScreen(true);
        super.onCreate(savedInstanceState);
        startCountDown(3);//倒计时三秒跳入下一界面
    }

    @Override
    public int onSetContentView() {
        return R.layout.activity_welcome;
    }

    @Override
    public void onInitView() {

    }

    @Override
    public void onInitData() {

    }

    @Override
    public void onInitListener() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCountDown.cancel();
    }

    /**
     * 开始倒计时
     * @param number
     */
    private void startCountDown(int number){
        mCountDown = new RxCountDown(number);
        mCountDown.startCountDown(new RxCountDown.CountDownListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onNext(int number) {

            }

            @Override
            public void onCompleted() {
                nextPage();
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    /**
     * 下一个页面
     */
    private void nextPage(){
        ActivityUtils.startActivityIntent(getMContext(), LoginActivity.class);
        finish();
    }
}
