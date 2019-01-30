package com.leo.pro.launch;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.leo.pro.R;
import com.leo.pro.app.base.Base2FragmentActivity;
import com.leo.pro.databinding.ActivityGuideBinding;
import com.leo.pro.databinding.LayoutGuideBinding;
import com.leo.pro.launch.adapter.GuideViewPageAdapter;
import com.leo.pro.login.view.LoginActivity;
import com.leo.pro.app.utils.ActivityUtils;

import java.util.ArrayList;

/**
 * 创建人 LEO
 * 创建时间 2019/1/29 17:57
 */

public class GuideActivity extends Base2FragmentActivity<ActivityGuideBinding> {
    @Override
    public int onSetContentView() {
        return R.layout.activity_guide;
    }

    @Override
    public void onInitView() {

    }

    @Override
    public void onInitData() {
        ArrayList<View> mViews = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            View guideView = mInflater.inflate(R.layout.layout_guide, null);
            LayoutGuideBinding binding = DataBindingUtil.bind(guideView);
            binding.setClickListener(this);
            binding.setContent("第" + i + "页");
            binding.setIsLast(i == 5 - 1);
            mViews.add(guideView);
        }
        GuideViewPageAdapter adapter = new GuideViewPageAdapter(mViews);
        mViewBinding.vp.setAdapter(adapter);
        mViewBinding.gpv.bindViewPager(mViewBinding.vp, mViews.size());
    }

    @Override
    public void onInitListener() {
        mViewBinding.setClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_jump:
            case R.id.btn_start:
                nextPage();
                break;
        }
    }

    /**
     * 下一个页面
     */
    private void nextPage() {
        ActivityUtils.startActivityIntent(mContext, LoginActivity.class);
        finish();
    }
}
