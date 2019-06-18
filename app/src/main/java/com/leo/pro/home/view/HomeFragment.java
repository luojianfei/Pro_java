package com.leo.pro.home.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.leo.pro.R;
import com.leo.pro.app.base.BaseFragment;
import com.leo.pro.databinding.FragmentHomeBinding;
import com.leo.pro.home.contract.HomeContract;
import com.leo.pro.home.presenter.HomePresenter;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;

/**
 * 创建人 LEO
 * 创建时间 2019/1/30 14:57
 */

public class HomeFragment extends BaseFragment<HomePresenter,FragmentHomeBinding> implements HomeContract.View {

    private ArrayList<String> imgUrls;

    @Override
    protected int onSetContentView() {
        return R.layout.fragment_home ;
    }

    @Override
    public void onInitView() {
    }

    @Override
    public void onInitData(Bundle arguments) {

    }

    @Override
    public void onInitListener() {

    }
}
