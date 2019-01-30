package com.leo.pro.fun2.view;

import android.os.Bundle;
import android.view.View;

import com.leo.pro.R;
import com.leo.pro.app.base.BaseFragment;
import com.leo.pro.databinding.FragmentFun2Binding;
import com.leo.pro.fun2.contract.Fun2Contract;
import com.leo.pro.fun2.presenter.Fun2Presenter;

/**
 * 创建人 LEO
 * 创建时间 2019/1/30 15:04
 */

public class Fun2Fragment extends BaseFragment<Fun2Presenter,FragmentFun2Binding> implements Fun2Contract.View {
    @Override
    protected int onSetContentView() {
        return R.layout.fragment_fun2 ;
    }

    @Override
    public void onInitView() {
        mViewBinding.layoutTitle.ivBack.setVisibility(View.GONE);
    }

    @Override
    public void onInitData(Bundle arguments) {
        mViewBinding.layoutTitle.setTitle("Fun2");
    }

    @Override
    public void onInitListener() {

    }
}
