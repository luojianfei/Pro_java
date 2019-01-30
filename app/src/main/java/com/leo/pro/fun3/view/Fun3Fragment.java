package com.leo.pro.fun3.view;

import android.os.Bundle;
import android.view.View;

import com.leo.pro.R;
import com.leo.pro.app.base.BaseFragment;
import com.leo.pro.databinding.FragmentFun3Binding;
import com.leo.pro.fun3.contract.Fun3Contract;
import com.leo.pro.fun3.presenter.Fun3Presenter;

/**
 * 创建人 LEO
 * 创建时间 2019/1/30 15:05
 */

public class Fun3Fragment extends BaseFragment<Fun3Presenter,FragmentFun3Binding> implements Fun3Contract.View {
    @Override
    protected int onSetContentView() {
        return R.layout.fragment_fun3 ;
    }

    @Override
    public void onInitView() {
        mViewBinding.layoutTitle.ivBack.setVisibility(View.GONE);
    }

    @Override
    public void onInitData(Bundle arguments) {
        mViewBinding.layoutTitle.setTitle("Fun3");
    }

    @Override
    public void onInitListener() {

    }
}
