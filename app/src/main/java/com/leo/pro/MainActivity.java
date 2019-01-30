package com.leo.pro;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.leo.pro.base.Base2Activity;
import com.leo.pro.databinding.ActivityMainBinding;

public class MainActivity extends Base2Activity<ActivityMainBinding> {

    @Override
    public int onSetContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void onInitView() {

    }

    @Override
    public void onInitData() {
        mViewBinding.layoutTitle.setTitle("主页");
    }

    @Override
    public void onInitListener() {
        mViewBinding.layoutTitle.setViewClickListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mDoubleKeyExit = true ;
        super.onCreate(savedInstanceState);
    }
}
