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
        mViewBinding.layoutTitle.ivBack.setVisibility(View.GONE);
    }

    @Override
    public void onInitData(Bundle arguments) {
        mViewBinding.layoutTitle.setTitle("Home");
        imgUrls = new ArrayList<>();
        imgUrls.add("http://e.hiphotos.baidu.com/image/pic/item/eaf81a4c510fd9f993009d8c282dd42a2934a4c4.jpg") ;
        imgUrls.add("http://e.hiphotos.baidu.com/image/pic/item/eaf81a4c510fd9f993009d8c282dd42a2934a4c4.jpg") ;
        imgUrls.add("http://e.hiphotos.baidu.com/image/pic/item/eaf81a4c510fd9f993009d8c282dd42a2934a4c4.jpg") ;
        imgUrls.add("http://e.hiphotos.baidu.com/image/pic/item/eaf81a4c510fd9f993009d8c282dd42a2934a4c4.jpg") ;
        ArrayList<String> titles = new ArrayList<>() ;
        titles.add("第一张");
        titles.add("第二张");
        titles.add("第三张");
        titles.add("第四张");
        mViewBinding.xbanner.setData(imgUrls,titles);
        mViewBinding.xbanner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, View view, int position) {
                Glide.with(getActivity()).load(imgUrls.get(position)).into((ImageView) view);
            }
        });
    }

    @Override
    public void onInitListener() {

    }
}
