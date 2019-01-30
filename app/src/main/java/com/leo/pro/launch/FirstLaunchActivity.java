package com.leo.pro.launch;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.leo.pro.R;
import com.leo.pro.base.Base2FragmentActivity;
import com.leo.pro.base.BaseFragmentActivity;
import com.leo.pro.databinding.ActivityFirstBinding;
import com.leo.pro.databinding.LayoutGuideBinding;
import com.leo.pro.launch.adapter.GuideViewPageAdapter;
import com.leo.pro.login.view.LoginActivity;
import com.leo.pro.utils.ActivityUtils;

import java.util.ArrayList;

/**
 * 创建人 LEO
 * 创建时间 2019/1/29 17:57
 */

public class FirstLaunchActivity extends Base2FragmentActivity<ActivityFirstBinding> {
    @Override
    public int onSetContentView() {
        return R.layout.activity_first;
    }

    @Override
    public void onInitView() {

    }

    @Override
    public void onInitData() {
        ArrayList<View> mViews = new ArrayList<>() ;
        for(int i = 0; i < 3 ; i++){
            View guideView = mInflater.inflate(R.layout.layout_guide,null) ;
            LayoutGuideBinding binding = DataBindingUtil.bind(guideView) ;
            binding.setContent("第"+i+"页");
            mViews.add(guideView) ;
        }
        GuideViewPageAdapter adapter = new GuideViewPageAdapter(mViews) ;
        mViewBinding.vp.setAdapter(adapter);
    }

    @Override
    public void onInitListener() {
        mViewBinding.setClickListener(this) ;
        mViewBinding.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_jump:
                nextPage();
                break;
        }
    }
    /**
     * 下一个页面
     */
    private void nextPage(){
        ActivityUtils.startActivityIntent(mContext, LoginActivity.class);
        finish();
    }
}
